package dto;

import org.junit.jupiter.api.Test;
import utils.TestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {

    @Test
    void whenValidOrder_shouldReturnTotalOrderValue() {
        Order order = TestUtils.getValidOrder();
        assertEquals(order.getStockQty() * order.getStockPrice(), order.getOrderValue());
    }
}
