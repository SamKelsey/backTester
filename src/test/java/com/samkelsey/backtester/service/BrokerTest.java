package com.samkelsey.backtester.service;

import com.samkelsey.backtester.utils.TestUtils;
import io.github.samkelsey.backtester.dto.BrokerAccountSummary;
import io.github.samkelsey.backtester.dto.Order;
import io.github.samkelsey.backtester.dto.OrderType;
import io.github.samkelsey.backtester.exception.BrokerException;
import io.github.samkelsey.backtester.service.Broker;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BrokerTest {

    @Test
    void whenValidPurchase_shouldUpdateBrokerState() {
        int startingBalance = 1000;
        Broker broker = new Broker(startingBalance);
        Order order = TestUtils.getValidOrder();

        broker.placeOrder(order);

        assertEquals(startingBalance - 200, broker.getCash());
        assertTrue(broker.getPortfolio().containsKey("AAPL"));
        assertEquals(2, broker.getPortfolio().get("AAPL"));
    }

    @Test
    void whenInsufficientFunds_shouldThrowException() {
        int startingBalance = 100;
        Broker broker = new Broker(startingBalance);
        Order order = TestUtils.getValidOrder();

        Throwable err = assertThrows(BrokerException.class, () -> broker.placeOrder(order));

        assertEquals(
                String.format("Insufficient funds to purchase %d units of %s.",
                    order.getStockQty(),
                    order.getTicker()),
                err.getMessage()
        );
    }

    @Test
    void whenValidSale_shouldUpdateBrokerState() {
        int startingBalance = 1000;
        Broker broker = new Broker(startingBalance);
        Order buyOrder = TestUtils.getValidOrder();
        Order sellOrder = TestUtils.getValidOrder(OrderType.SELL);
        sellOrder.setStockQty(buyOrder.getStockQty() - 1);
        broker.placeOrder(buyOrder);

        broker.placeOrder(sellOrder);

        float expectedBalance = startingBalance - (
                (buyOrder.getStockQty() - sellOrder.getStockQty()) * sellOrder.getStockPrice()
        );
        assertEquals(expectedBalance, broker.getCash());
        assertEquals(1, broker.getPortfolio().get("AAPL"));
    }

    @Test
    void whenValidSale_shouldRemoveTicker() {
        int startingBalance = 1000;
        Broker broker = new Broker(startingBalance);
        Order buyOrder = TestUtils.getValidOrder();
        Order sellOrder = TestUtils.getValidOrder(OrderType.SELL);
        broker.placeOrder(buyOrder);

        broker.placeOrder(sellOrder);

        assertEquals(startingBalance, broker.getCash());
        assertFalse(broker.getPortfolio().containsKey("AAPL"));
    }

    @Test
    void whenInsufficientStock_shouldThrowException() {
        int startingBalance = 100;
        Broker broker = new Broker(startingBalance);
        Order order = TestUtils.getValidOrder(OrderType.SELL);

        Throwable err = assertThrows(BrokerException.class, () -> broker.placeOrder(order));

        assertEquals(
                String.format("Insufficient share units to sell %d units of %s",
                        order.getStockQty(),
                        order.getTicker()),
                err.getMessage()
        );
    }

    @Test
    void shouldReturnAccountSummary_whenCreateAccountSummary() {
        Broker broker = new Broker(1000);
        Order order = TestUtils.getValidOrder(OrderType.BUY);
        broker.placeOrder(order);

        BrokerAccountSummary summary = broker.createAccountSummary();

        assertEquals(800, summary.getCash());
        assertEquals(broker.getPortfolio(), summary.getPortfolio());
    }

    @Test
    void shouldCalculateTotalEquity_whenGetTotalEquity() {
        Map<String, Float> stockPrices = new HashMap<>();
        stockPrices.put("AAPL", 300f);

        Broker broker = new Broker(1000);
        Order order = TestUtils.getValidOrder(OrderType.BUY);
        broker.placeOrder(order);

        float totalEquity = broker.getTotalEquity(stockPrices);
        assertEquals(1400, totalEquity);
    }
}
