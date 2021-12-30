import service.Broker;
import service.BrokerAccountSummary;
import service.Order;

/**
 * Main class to kick-off backtest simulations.
 */
public class BackTester {

    private final DataSource dataSource;
    private final Algorithm algorithm;
    private final Broker broker;

    public BackTester(Algorithm algorithm, DataSource dataSource) {
        this.dataSource = dataSource;
        this.algorithm = algorithm;
        this.broker = new Broker();
    }

    /**
     * Main method to kick-off back-testing simulation.
     */
    public void run() {

        while (dataSource.hasNextFile()) {
            while (dataSource.hasNextData()) {
                // Get row of data
                String[] data = dataSource.getData();

                // Get broker summary
                BrokerAccountSummary summary = broker.createAccountSummary();

                // Get verdict from algorithm
                Order order = algorithm.run(data, summary);

                // Carry out action on broker, decided by algorithm.
                if (order == null) {
                    continue;
                }

                broker.placeOrder(order);
            }

            dataSource.nextFile();
        }
    }
}
