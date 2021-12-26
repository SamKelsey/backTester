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
        Order order = getValidOrder();

        broker.placeOrder(order);

        assertEquals(startingBalance - 200, broker.getBalance());
        assertTrue(broker.getPortfolio().containsKey("AAPL"));
        assertEquals(2, broker.getPortfolio().get("AAPL"));
    }

    @Test
    void whenInsufficientFunds_shouldThrowException() {
        int startingBalance = 100;
        Broker broker = new Broker(startingBalance);
        Order order = getValidOrder();

        Throwable err = assertThrows(BrokerException.class, () -> {
            broker.placeOrder(order);
        });

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
        Order buyOrder = getValidOrder();
        Order sellOrder = getValidOrder(OrderType.SELL);
        broker.placeOrder(buyOrder);

        broker.placeOrder(sellOrder);

        assertEquals(startingBalance, broker.getBalance());
        assertEquals(0, broker.getPortfolio().get("AAPL"));
    }

    @Test
    void whenInsufficientStock_shouldThrowException() {
        int startingBalance = 100;
        Broker broker = new Broker(startingBalance);
        Order order = getValidOrder(OrderType.SELL);

        Throwable err = assertThrows(BrokerException.class, () -> {
            broker.placeOrder(order);
        });

        assertEquals(
                String.format("Insufficient share units to sell %d units of %s",
                        order.getStockQty(),
                        order.getTicker()),
                err.getMessage()
        );
    }

    private Order getValidOrder() {
        return getValidOrder(OrderType.BUY);
    }

    private Order getValidOrder(OrderType type) {
        return switch (type) {
            case BUY -> new Order("AAPL", OrderType.BUY, 2, 100);
            case SELL -> new Order("AAPL", OrderType.SELL, 2, 100);
        };
    }
}
