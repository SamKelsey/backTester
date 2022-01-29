package io.github.samkelsey.backtester.broker;

import io.github.samkelsey.backtester.broker.model.BrokerAccountSummary;
import io.github.samkelsey.backtester.broker.model.Order;
import io.github.samkelsey.backtester.broker.model.OrderType;
import io.github.samkelsey.backtester.datasource.StockData;
import io.github.samkelsey.backtester.exception.BrokerException;
import io.github.samkelsey.backtester.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static io.github.samkelsey.backtester.broker.model.OrderType.BUY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BrokerTest {

    @Test
    void whenValidPurchase_shouldUpdateBrokerState() {
        int startingBalance = 1000;
        Broker broker = new Broker(startingBalance);
        Order order = TestUtils.getValidOrder(BUY);

        broker.placeOrder(order);

        assertEquals(startingBalance - 200, broker.getCash());
        assertTrue(broker.getPortfolio().containsKey("AAPL"));
        assertEquals(2, broker.getPortfolio().get("AAPL").getUnitsOwned());
    }

    @Test
    void whenInsufficientFunds_shouldThrowException() {
        int startingBalance = 100;
        Broker broker = new Broker(startingBalance);
        Order order = TestUtils.getValidOrder(BUY);

        assertThrows(BrokerException.class, () -> broker.placeOrder(order));
    }

    @Test
    void whenValidSale_shouldUpdateBrokerState() {
        int startingBalance = 1000;
        Broker broker = new Broker(startingBalance);
        Order buyOrder = TestUtils.getValidOrder(BUY);
        Order sellOrder = TestUtils.getValidOrder(OrderType.SELL);
        sellOrder.setStockQty(buyOrder.getStockQty() - 1);
        broker.placeOrder(buyOrder);

        broker.placeOrder(sellOrder);

        float expectedBalance = startingBalance - (
                (buyOrder.getStockQty() - sellOrder.getStockQty()) * sellOrder.getStockPrice()
        );
        assertEquals(expectedBalance, broker.getCash());
        assertEquals(1, broker.getPortfolio().get("AAPL").getUnitsOwned());
    }

    @Test
    void whenValidSale_shouldRemoveTicker() {
        int startingBalance = 1000;
        Broker broker = new Broker(startingBalance);
        Order buyOrder = TestUtils.getValidOrder(BUY);
        Order sellOrder = TestUtils.getValidOrder(OrderType.SELL);
        broker.placeOrder(buyOrder);

        broker.placeOrder(sellOrder);

        assertFalse(broker.getPortfolio().containsKey("AAPL"));
    }

    @Test
    void whenInsufficientStock_shouldThrowException() {
        int startingBalance = 100;
        Broker broker = new Broker(startingBalance);
        Order order = TestUtils.getValidOrder(OrderType.SELL);

        assertThrows(BrokerException.class, () -> broker.placeOrder(order));
    }

    @Test
    void shouldReturnCorrectAccountSummary_whenCreateAccountSummary() {
        Broker broker = new Broker(1000);
        Order order = TestUtils.getValidOrder(BUY);
        broker.placeOrder(order);

        BrokerAccountSummary summary = broker.createAccountSummary();

        assertEquals(800, summary.getCash());
        assertEquals(broker.getPortfolio(), summary.getPortfolio());
    }

    @Test
    void shouldUpdateCurrPrices_whenRefreshBroker() {
        Broker broker = createPopulatedBroker();
        StockData stockData = new StockData(1000,"AAPL");

        broker.refreshBroker(stockData);

        assertEquals(1000, broker.getPortfolio().get("AAPL").getCurrentPrice());
    }

    @Test
    void shouldCalculateTotalEquity_whenGetTotalEquity() {
        Broker broker = new Broker(1000);
        Order order = TestUtils.getValidOrder(BUY);
        broker.placeOrder(order);
        StockData newData = new StockData(1000, "AAPL");
        broker.refreshBroker(newData);

        float totalEquity = broker.getTotalEquity();

        assertEquals(2800, totalEquity);
    }

    private Broker createPopulatedBroker() {
        Broker broker = new Broker(1000);
        Order order = TestUtils.getValidOrder(BUY);
        broker.placeOrder(order);

        return broker;
    }
}
