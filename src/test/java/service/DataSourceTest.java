package service;

import dto.StockData;
import exceptions.DataSourceException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataSourceTest {

    @Test
    void shouldInitialiseFile_whenConstructed() throws IOException {
        DataSource dataSource = new DataSource();
        assertNotNull(dataSource.getCurrentFile());
    }

    @Test
    void shouldInitialiseCustomFile_whenConstructed() throws IOException {
        DataSource dataSource = createDataSource();
        assertNotNull(dataSource.getCurrentFile());
    }

    @Test
    void shouldReturnIfNextData_whenHasNextData() throws IOException, DataSourceException {
        DataSource dataSource = createDataSource();

        boolean expectTrue = dataSource.hasNextData();
        dataSource.getData();
        dataSource.getData();
        boolean expectFalse = dataSource.hasNextData();

        assertTrue(expectTrue);
        assertFalse(expectFalse);
    }

    @Test
    void shouldReturnData_whenGetData() throws IOException, DataSourceException {
        DataSource dataSource = createDataSource();
        StockData data = dataSource.getData();
        assertNotNull(data);
    }

    @Test
    void shouldReturnNull_whenNoData() throws IOException, DataSourceException {
        DataSource dataSource = createDataSource();
        dataSource.getData();
        dataSource.getData();
        assertNull(dataSource.getData());
    }

    @Test
    void shouldUseFileName_whenNoTickerProvided() throws IOException, DataSourceException {
        DataSource dataSource = createDataSource();
        StockData data = dataSource.getData();
        assertEquals(dataSource.getCurrentFileName(), data.getTicker());
    }

    @Test
    void shouldReturnBool_whenNextFile() throws IOException {
        DataSource dataSource = createDataSource();

        boolean expectTrue = dataSource.nextFile();
        boolean expectFalse = dataSource.nextFile();

        assertTrue(expectTrue);
        assertFalse(expectFalse);
    }

    @Test
    void shouldReturnFileName_whenGetFileName() throws IOException {
        DataSource dataSource = createDataSource();

        String actualName = dataSource.getCurrentFileName();
        String expectedName = "test_data";

        assertEquals(expectedName, actualName);
    }

    private DataSource createDataSource() throws IOException {
        return new DataSource("src/test/resources/", (row) ->
                new StockData(Float.parseFloat(row[6]))
        );
    }
}