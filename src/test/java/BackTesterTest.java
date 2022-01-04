import dto.StockData;
import exceptions.BackTesterException;
import org.junit.jupiter.api.Test;
import service.DataSource;

import java.io.IOException;

public class BackTesterTest {

    @Test
    void example() throws IOException, BackTesterException {
        DataSource dataSource = new DataSource();
        Algorithm algorithm = new ExampleAlgorithm();

        BackTester backTester = new BackTester(algorithm, dataSource);
        float profit = backTester.run();
        System.out.println(profit);
    }

    @Test
    void exampleWithCustomDataSet() throws IOException, BackTesterException {
        DataSource dataSource = new DataSource("src/test/resources/", (row) ->
                new StockData(Float.parseFloat(row[6]))
        );

        Algorithm algorithm = new ExampleAlgorithm();

        BackTester backTester = new BackTester(algorithm, dataSource);
        float profit = backTester.run();
        System.out.println(profit);
    }
}
