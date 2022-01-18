package com.samkelsey.backtester.service;

import com.samkelsey.backtester.utils.TestUtils;
import io.github.samkelsey.backtester.dto.StockData;
import io.github.samkelsey.backtester.exception.DataSourceException;
import io.github.samkelsey.backtester.service.DataSource;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataSourceTest {

    @Test
    void shouldInitialiseFile_whenConstructed() throws IOException, DataSourceException {
        DataSource dataSource = new DataSource();
        assertNotNull(dataSource.getCurrentFileName());
    }

    @Test
    void shouldInitialiseCustomFile_whenConstructed() throws IOException, DataSourceException {
        DataSource dataSource = TestUtils.createDataSource();
        assertNotNull(dataSource.getCurrentFileName());
    }

    @Test
    void shouldReturnIfNextData_whenHasNextData() throws IOException, DataSourceException {
        DataSource dataSource = TestUtils.createDataSource();

        boolean expectTrue = dataSource.hasNextData();
        dataSource.getData();
        dataSource.getData();
        boolean expectFalse = dataSource.hasNextData();

        assertTrue(expectTrue);
        assertFalse(expectFalse);
    }

    @Test
    void shouldReturnData_whenGetData() throws IOException, DataSourceException {
        DataSource dataSource = TestUtils.createDataSource();
        StockData data = dataSource.getData();
        assertNotNull(data);
    }

    @Test
    void shouldReturnNull_whenNoData() throws IOException, DataSourceException {
        DataSource dataSource = TestUtils.createDataSource();
        dataSource.getData();
        dataSource.getData();
        assertNull(dataSource.getData());
    }

    @Test
    void shouldUseFileName_whenNoTickerProvided() throws IOException, DataSourceException {
        DataSource dataSource = TestUtils.createDataSource();
        StockData data = dataSource.getData();
        assertEquals(dataSource.getCurrentFileName(), data.getTicker());
    }

    @Test
    void shouldReturnBool_whenNextFile() throws IOException, DataSourceException {
        DataSource dataSource = TestUtils.createDataSource();

        boolean expectTrue = dataSource.nextFile();
        boolean expectFalse = dataSource.nextFile();

        assertTrue(expectTrue);
        assertFalse(expectFalse);
    }

    @Test
    void shouldReturnFileName_whenGetFileName() throws IOException, DataSourceException {
        DataSource dataSource = TestUtils.createDataSource();

        String actualName = dataSource.getCurrentFileName();
        String expectedName = "test_data";

        assertEquals(expectedName, actualName);
    }
}