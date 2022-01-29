package io.github.samkelsey.backtester.broker;

import io.github.samkelsey.backtester.broker.model.Order;
import io.github.samkelsey.backtester.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static io.github.samkelsey.backtester.broker.model.OrderType.BUY;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {

    @Test
    void whenValidOrder_shouldReturnTotalOrderValue() {
        Order order = TestUtils.getValidOrder(BUY);
        assertEquals(order.getStockQty() * order.getStockPrice(), order.getOrderValue());
    }
}
