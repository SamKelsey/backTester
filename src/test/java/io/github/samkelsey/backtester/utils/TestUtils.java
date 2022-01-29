package io.github.samkelsey.backtester.utils;

import io.github.samkelsey.backtester.broker.model.Order;
import io.github.samkelsey.backtester.broker.model.OrderType;
import io.github.samkelsey.backtester.datasource.DataSource;
import io.github.samkelsey.backtester.datasource.StockData;
import io.github.samkelsey.backtester.exception.DataSourceException;

import java.io.IOException;

public class TestUtils {

    public static Order getValidOrder(OrderType type) {
        return switch (type) {
            case BUY -> new Order("AAPL", OrderType.BUY, 2, 100);
            case SELL -> new Order("AAPL", OrderType.SELL, 2, 100);
        };
    }

    public static DataSource createDataSource() throws DataSourceException, IOException {
        return new DataSource((row) ->
                new StockData(Float.parseFloat(row[6])),
                "/test_data.csv",
                        "/test_data2.csv"
                );
    }
}
