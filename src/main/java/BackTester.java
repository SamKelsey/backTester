import dto.BrokerAccountSummary;
import dto.Order;
import dto.StockData;
import exceptions.BackTesterException;
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
    public float run() throws IOException, BackTesterException {

        Map<String, Float> stockPrices = new HashMap<>();

        while (dataSource.nextFile()) {
            StockData data = null;
            while (dataSource.hasNextData()) {
                data = dataSource.getData();
                BrokerAccountSummary summary = broker.createAccountSummary();
                Order order = algorithm.run(data, summary);
                broker.placeOrder(order);
            }

            if (data != null) {
                stockPrices.put(data.getTicker(), data.getStockPrice());
            }
        }

        return broker.getTotalEquity(stockPrices);
    }
}
