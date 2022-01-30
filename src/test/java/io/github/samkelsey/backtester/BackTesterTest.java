package io.github.samkelsey.backtester;

import io.github.samkelsey.backtester.algorithm.Algorithm;
import io.github.samkelsey.backtester.algorithm.ExampleAlgorithm;
import io.github.samkelsey.backtester.broker.BrokerReader;
import io.github.samkelsey.backtester.datasource.DataSourceImpl;
import io.github.samkelsey.backtester.exception.BackTesterException;
import io.github.samkelsey.backtester.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BackTesterTest {

    @Test
    void shouldReturnResult_whenDefaultConfig() throws IOException, BackTesterException {
        DataSourceImpl dataSource = new DataSourceImpl();
        Algorithm algorithm = new ExampleAlgorithm();

        BackTester backTester = new BackTester(algorithm, dataSource);
        BrokerReader result = backTester.run();
        Assertions.assertNotNull(result);
    }

    @Test
    void shouldReturnResult_whenCustomConfig() throws IOException, BackTesterException {
        DataSourceImpl dataSource = TestUtils.createDataSource();
        Algorithm algorithm = new ExampleAlgorithm();
        BackTester backTester = new BackTester(algorithm, dataSource);

        BrokerReader result = backTester.run();

        Assertions.assertNotNull(result);
    }

    @Test
    void shouldRunAllTestDataFiles_whenRun() throws IOException, BackTesterException {
        DataSourceImpl dataSource = TestUtils.createDataSource();
        Algorithm algorithm = new ExampleAlgorithm();
        BackTester backTester = new BackTester(algorithm, dataSource);

        BrokerReader result = backTester.run();

        assertTrue(result.getPortfolio().containsKey("test_data"));
        assertTrue(result.getPortfolio().containsKey("test_data2"));
    }
}
