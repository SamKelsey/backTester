package com.samkelsey.backtester.utils;

import com.samkelsey.backtester.dto.Order;
import com.samkelsey.backtester.dto.OrderType;
import com.samkelsey.backtester.dto.StockData;
import com.samkelsey.backtester.service.DataSource;

import java.io.IOException;

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

    public static DataSource createDataSource() throws IOException {
        return new DataSource("src/test/resources/", (row) ->
                new StockData(Float.parseFloat(row[6]))
        );
    }
}
