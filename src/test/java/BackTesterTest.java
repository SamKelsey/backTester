import dto.BackTestResult;
import exceptions.BackTesterException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.DataSource;
import utils.TestUtils;

import java.io.IOException;

public class BackTesterTest {

    @Test
    void shouldReturnResult_whenDefaultConfig() throws IOException, BackTesterException {
        DataSource dataSource = new DataSource();
        Algorithm algorithm = new ExampleAlgorithm();

        BackTester backTester = new BackTester(algorithm, dataSource);
        BackTestResult result = backTester.run();
        Assertions.assertNotNull(result);
    }

    @Test
    void shouldReturnResult_whenCustomConfig() throws IOException, BackTesterException {
        DataSource dataSource = TestUtils.createDataSource();

        Algorithm algorithm = new ExampleAlgorithm();

        BackTester backTester = new BackTester(algorithm, dataSource);
        BackTestResult result = backTester.run();
        Assertions.assertNotNull(result);
    }
}
