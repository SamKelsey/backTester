package io.github.samkelsey.backtester.datasource.model;

import io.github.samkelsey.backtester.exception.DataSourceException;

/**
 * An interface for mapping the rows of test data from a string array to a StockData object.
 */
@FunctionalInterface
public interface StockDataMapper {

    StockData toStockData(String[] dataRow) throws DataSourceException;

}
