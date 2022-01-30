package io.github.samkelsey.backtester.broker;

import io.github.samkelsey.backtester.broker.model.BrokerAccountSummary;
import io.github.samkelsey.backtester.broker.model.BrokerStockData;
import io.github.samkelsey.backtester.broker.model.Order;
import io.github.samkelsey.backtester.broker.model.OrderType;
import io.github.samkelsey.backtester.datasource.model.StockData;
import io.github.samkelsey.backtester.exception.BrokerException;
import io.github.samkelsey.backtester.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.github.samkelsey.backtester.broker.model.OrderType.BUY;
import static io.github.samkelsey.backtester.broker.model.OrderType.SELL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BrokerTest {

    @Test
    void whenValidPurchase_shouldUpdateBrokerState() {
        int startingBalance = 1000;
        BrokerImpl broker = new BrokerImpl(startingBalance);
        Order order = TestUtils.getValidOrder(BUY);

        broker.placeOrder(order);

        BrokerStockData stockData = broker.getPortfolio().get("AAPL");
        assertNotNull(stockData);
        assertEquals(2, stockData.getUnitsOwned());
        assertEquals(200, stockData.getTotalPurchaseCost());
        assertEquals(0, stockData.getTotalSalesRevenue());
        assertEquals(startingBalance - 200, broker.getCash());
    }

    @Test
    void whenInsufficientFunds_shouldThrowException() {
        int startingBalance = 100;
        BrokerImpl broker = new BrokerImpl(startingBalance);
        Order order = TestUtils.getValidOrder(BUY);

        assertThrows(BrokerException.class, () -> broker.placeOrder(order));
    }

    @Test
    void whenValidSale_shouldUpdateBrokerState() {
        int startingBalance = 1000;
        BrokerImpl broker = new BrokerImpl(startingBalance);
        Order buyOrder = TestUtils.getValidOrder(BUY);
        Order sellOrder = TestUtils.getValidOrder(OrderType.SELL);
        sellOrder.setStockQty(buyOrder.getStockQty() - 1);
        broker.placeOrder(buyOrder);

        broker.placeOrder(sellOrder);

        float expectedBalance = startingBalance - (
                (buyOrder.getStockQty() - sellOrder.getStockQty()) * sellOrder.getStockPrice()
        );
        BrokerStockData stockData = broker.getPortfolio().get("AAPL");
        assertEquals(expectedBalance, broker.getCash());
        assertEquals(1, stockData.getUnitsOwned());
        assertEquals(200, stockData.getTotalPurchaseCost());
        assertEquals(
                sellOrder.getStockPrice() * sellOrder.getStockQty(),
                stockData.getTotalSalesRevenue()
        );
    }

    @Test
    void whenInsufficientStock_shouldThrowException() {
        int startingBalance = 100;
        BrokerImpl broker = new BrokerImpl(startingBalance);
        Order order = TestUtils.getValidOrder(OrderType.SELL);

        assertThrows(BrokerException.class, () -> broker.placeOrder(order));
    }

    @Test
    void shouldReturnCorrectAccountSummary_whenCreateAccountSummary() {
        BrokerImpl broker = new BrokerImpl(1000);
        Order order = TestUtils.getValidOrder(BUY);
        broker.placeOrder(order);

        BrokerAccountSummary summary = broker.createAccountSummary();

        assertEquals(800, summary.getCash());
        assertEquals(broker.getPortfolio(), summary.getPortfolio());
    }

    @Test
    void shouldUpdateCurrPrices_whenRefreshBroker() {
        BrokerImpl broker = createPopulatedBroker();
        StockData stockData = new StockData(1000,"AAPL");

        broker.refreshBroker(stockData);

        assertEquals(1000, broker.getPortfolio().get("AAPL").getCurrentPrice());
    }

    @Test
    void shouldCalculateTotalEquity_whenGetTotalEquity() {
        BrokerImpl broker = new BrokerImpl(1000);
        Order order = TestUtils.getValidOrder(BUY);
        broker.placeOrder(order);
        StockData newData = new StockData(1000, "AAPL");
        broker.refreshBroker(newData);

        float totalEquity = broker.getTotalEquity();

        assertEquals(2800, totalEquity);
    }

    @Test
    void shouldReturnPercentageChanges_whenGetPercentageChanges() {
        // Buy
        BrokerImpl broker = createPopulatedBroker();
        StockData refreshData = new StockData(120, "AAPL");
        broker.refreshBroker(refreshData);

        // Buy
        Order order = new Order("AAPL", BUY, 1, 120);
        broker.placeOrder(order);
        refreshData.setStockPrice(115);
        broker.refreshBroker(refreshData);

        // Sell
        order = new Order("AAPL", SELL, 2, 115);
        broker.placeOrder(order);
        refreshData.setStockPrice(118);
        broker.refreshBroker(refreshData);

        Map<String, Float> percentageChanges = broker.getPercentageChanges();

        assertEquals(8.75f, percentageChanges.get("AAPL"));
    }

    private BrokerImpl createPopulatedBroker() {
        BrokerImpl broker = new BrokerImpl(1000);
        Order order = TestUtils.getValidOrder(BUY);
        broker.placeOrder(order);

        return broker;
    }
}
