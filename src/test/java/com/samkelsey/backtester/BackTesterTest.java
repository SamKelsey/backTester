package com.samkelsey.backtester;

import com.samkelsey.backtester.dto.BackTestResult;
import com.samkelsey.backtester.exception.BackTesterException;
import com.samkelsey.backtester.service.DataSource;
import com.samkelsey.backtester.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
