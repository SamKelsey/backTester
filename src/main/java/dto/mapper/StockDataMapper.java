package dto.mapper;

import dto.StockData;

/**
 * An interface for mapping the rows of test data from a string array to a StockData object.
 */
@FunctionalInterface
public interface StockDataMapper {

    StockData toStockData(String[] dataRow);

}
