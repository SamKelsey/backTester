package io.github.samkelsey.backtester.datasource;

import io.github.samkelsey.backtester.datasource.model.StockData;
import io.github.samkelsey.backtester.exception.DataSourceException;

import java.io.IOException;

public interface DataSource {

    boolean hasNextData() throws IOException;

    StockData getData() throws IOException, DataSourceException;

    boolean hasNextFile();

    boolean nextFile() throws IOException, DataSourceException;

}
