package dto;

/**
 * An interface to represent the data that is returned from the DataSource class.
 */

public interface StockData {

    String getTicker();
    float getStockPrice();

}
