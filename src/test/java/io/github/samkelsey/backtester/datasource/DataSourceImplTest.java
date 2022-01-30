package io.github.samkelsey.backtester.datasource;

import io.github.samkelsey.backtester.datasource.model.StockData;
import io.github.samkelsey.backtester.exception.DataSourceException;
import io.github.samkelsey.backtester.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataSourceImplTest {

    @Test
    void shouldThrowError_whenNoFileInitialised() throws DataSourceException, IOException {
        DataSourceImpl dataSource = new DataSourceImpl();

        assertThrows(DataSourceException.class, dataSource::getCurrentFileName);
        assertThrows(DataSourceException.class, dataSource::getData);
    }

    @Test
    void shouldReturnIfNextData_whenHasNextData() throws IOException, DataSourceException {
        DataSourceImpl dataSource = TestUtils.createInitialisedDataSource();

        boolean expectTrue = dataSource.hasNextData();
        dataSource.getData();
        dataSource.getData();
        boolean expectFalse = dataSource.hasNextData();

        assertTrue(expectTrue);
        assertFalse(expectFalse);
    }

    @Test
    void shouldReturnData_whenGetData() throws IOException, DataSourceException {
        DataSourceImpl dataSource = TestUtils.createInitialisedDataSource();
        StockData data = dataSource.getData();
        assertNotNull(data);
    }

    @Test
    void shouldReturnNull_whenNoData() throws IOException, DataSourceException {
        DataSourceImpl dataSource = TestUtils.createInitialisedDataSource();
        dataSource.getData();
        dataSource.getData();
        assertNull(dataSource.getData());
    }

    @Test
    void shouldUseFileName_whenNoTickerProvided() throws IOException, DataSourceException {
        DataSourceImpl dataSource = TestUtils.createInitialisedDataSource();
        StockData data = dataSource.getData();
        assertEquals(dataSource.getCurrentFileName(), data.getTicker());
    }

    @Test
    void shouldReturnBool_whenNextFile() throws IOException, DataSourceException {
        DataSourceImpl dataSource = TestUtils.createInitialisedDataSource();

        boolean expectTrue = dataSource.nextFile();
        boolean expectFalse = dataSource.nextFile();

        assertTrue(expectTrue);
        assertFalse(expectFalse);
    }

    @Test
    void shouldReturnFileName_whenGetFileName() throws IOException, DataSourceException {
        DataSourceImpl dataSource = TestUtils.createInitialisedDataSource();

        String actualName = dataSource.getCurrentFileName();
        String expectedName = "test_data";

        assertEquals(expectedName, actualName);
    }
}