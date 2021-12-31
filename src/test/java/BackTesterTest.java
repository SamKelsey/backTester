import org.junit.jupiter.api.Test;
import service.DataSource;

public class BackTesterTest {

    @Test
    void example() {
        DataSource dataSource = new DataSource();
        Algorithm algorithm = new ExampleAlgorithm();

        BackTester backTester = new BackTester(algorithm, dataSource);
        float profit = backTester.run();
        System.out.println(profit);
    }
}
