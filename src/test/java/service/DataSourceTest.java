package service;

import dto.StockData;
import exceptions.DataSourceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataSourceTest {

    @Test
    void shouldInstantiateFile_whenConstructed() throws DataSourceException {
        DataSource dataSource = new DataSource();
        assertNotNull(dataSource.getCurrentFileHeaders());
    }

    @Test
    void shouldInstantiateCustomFile_whenConstructed() throws DataSourceException {
        DataSource dataSource = createDataSource();
        assertNotNull(dataSource.getCurrentFileHeaders());
    }

    @Test
    void shouldReturnIfNextData_whenHasNextData() throws DataSourceException {
        DataSource dataSource = createDataSource();

        boolean expectTrue = dataSource.hasNextData();
        dataSource.getData();
        dataSource.getData();
        boolean expectFalse = dataSource.hasNextData();

        assertTrue(expectTrue);
        assertFalse(expectFalse);
    }

    @Test
    void shouldReturnData_whenGetData() throws DataSourceException {
        DataSource dataSource = createDataSource();
        StockData data = dataSource.getData();
        assertNotNull(data);
    }

    @Test
    void shouldReturnNull_whenNoData() throws DataSourceException {
        DataSource dataSource = createDataSource();
        dataSource.getData();
        dataSource.getData();
        assertNull(dataSource.getData());
    }

    @Test
    void shouldReturnTrue_whenNextFile() throws DataSourceException {
        DataSource dataSource = createDataSource();
        assertTrue(dataSource.hasNextFile());
    }

    @Test
    void shouldUseNextFile_whenNextFile() throws DataSourceException {
        DataSource dataSource = createDataSource();
        dataSource.nextFile();
        assertEquals(1, dataSource.getCurrentFile());
    }

    @Test
    void shouldReturnFileName_whenGetFileName() {
        DataSource dataSource = createDataSource();

        String actualName = dataSource.getCurrentFileName();
        String expectedName = "test_data";

        assertEquals(expectedName, actualName);
    }

    private DataSource createDataSource() throws DataSourceException {
        return new DataSource("src/test/resources/");
    }
}