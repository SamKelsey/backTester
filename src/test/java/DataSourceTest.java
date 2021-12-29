import Exceptions.DataSourceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DataSourceTest {

    @Test
    void shouldInstantiateFile_whenConstructed() throws DataSourceException {
        DataSource dataSource = new DataSource();
        assertNotNull(dataSource.getCurrentFileHeaders());
    }

    @Test
    void shouldInstantiateCustomFile_whenConstructed() throws DataSourceException {
        DataSource dataSource = new DataSource("src/test/resources/");
        assertNotNull(dataSource.getCurrentFileHeaders());
    }

    @Test
    void shouldThrowException_whenEmptyInvalidDirectory() throws DataSourceException {
        assertThrows(DataSourceException.class, () -> {
            new DataSource(null);
        });
    }

}
