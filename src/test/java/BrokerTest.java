import Exceptions.BrokerException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BrokerTest {

    @Test
    void whenValidPurchase_shouldUpdateBrokerState() {
        int startingBalance = 1000;
        Broker broker = new Broker(startingBalance);
        Order order = TestUtils.getValidOrder();

        broker.placeOrder(order);

        assertEquals(startingBalance - 200, broker.getBalance());
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
                String.format("Insufficient funds for transaction of %d units of %s.",
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
        broker.placeOrder(buyOrder);

        broker.placeOrder(sellOrder);

        assertEquals(startingBalance, broker.getBalance());
        assertEquals(0, broker.getPortfolio().get("AAPL"));
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
}
