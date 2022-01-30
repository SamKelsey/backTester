package io.github.samkelsey.backtester.datasource;

import io.github.samkelsey.backtester.datasource.model.StockData;
import io.github.samkelsey.backtester.exception.DataSourceException;

import java.io.IOException;

public interface DataSource {

    /**
     * A method to check if there is another row of data to be read from the current file.
     * @return boolean
     * @throws IOException If something bad happens when reading the file.
     */
    boolean hasNextData() throws IOException;

    /**
     * A method responsible for returning the next row of data.
     * @return {@link StockData} data object, representing the next row of csv data.
     *         Null, if there is no more data to be read.
     * @throws IOException If something bad happens whilst reading the data.
     * @throws DataSourceException If there is an issue with the validity of the data.
     */
    StockData getData() throws IOException, DataSourceException;

    /**
     * A method to tell if there is another test data file to run.
     * @return True, if there is a next file. False, if not.
     */
    boolean hasNextFile();

    /**
     * Method to increment onto the next data file.
     * @throws DataSourceException Occurs if there is a problem initialising the test data file.
     * @throws IOException If something bad happens whilst initialising the file.
     */
    void nextFile() throws IOException, DataSourceException;

}
