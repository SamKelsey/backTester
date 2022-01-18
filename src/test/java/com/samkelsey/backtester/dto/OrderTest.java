package com.samkelsey.backtester.dto;

import com.samkelsey.backtester.utils.TestUtils;
import io.github.samkelsey.backtester.dto.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {

    @Test
    void whenValidOrder_shouldReturnTotalOrderValue() {
        Order order = TestUtils.getValidOrder();
        assertEquals(order.getStockQty() * order.getStockPrice(), order.getOrderValue());
    }
}
