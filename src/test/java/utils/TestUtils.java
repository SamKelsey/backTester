package utils;

import dto.Order;
import dto.OrderType;

public class TestUtils {

    public static Order getValidOrder() {
        return getValidOrder(OrderType.BUY);
    }

    public static Order getValidOrder(OrderType type) {
        return switch (type) {
            case BUY -> new Order("AAPL", OrderType.BUY, 2, 100);
            case SELL -> new Order("AAPL", OrderType.SELL, 2, 100);
        };
    }
}
