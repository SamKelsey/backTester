package com.samkelsey.backtester;

import com.samkelsey.backtester.utils.TestUtils;
import io.github.samkelsey.backtester.Algorithm;
import io.github.samkelsey.backtester.BackTester;
import io.github.samkelsey.backtester.ExampleAlgorithm;
import io.github.samkelsey.backtester.dto.BackTestResult;
import io.github.samkelsey.backtester.exception.BackTesterException;
import io.github.samkelsey.backtester.service.DataSource;
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
