import dto.BrokerAccountSummary;
import dto.Order;
import dto.StockData;
import service.Broker;
import service.DataSource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    public float run() throws IOException {

        Map<String, Float> stockPrices = new HashMap<>();

        while (dataSource.getCurrentFile() != null) {
            StockData data = null;
            while (dataSource.hasNextData()) {
                // Get row of data
                data = dataSource.getData();

                // Get broker summary
                BrokerAccountSummary summary = broker.createAccountSummary();

                // Get verdict from algorithm
                Order order = algorithm.run(data, summary);

                // Carry out action on broker, decided by algorithm.
                broker.placeOrder(order);
            }

            if (data != null) {
                stockPrices.put(data.getTicker(), data.getStockPrice());
            }
            dataSource.nextFile();
        }

        // Return final total equity.
        return broker.getTotalEquity(stockPrices);
    }
}
