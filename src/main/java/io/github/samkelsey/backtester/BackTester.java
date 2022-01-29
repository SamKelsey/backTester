package io.github.samkelsey.backtester;

import io.github.samkelsey.backtester.algorithm.Algorithm;
import io.github.samkelsey.backtester.broker.Broker;
import io.github.samkelsey.backtester.broker.model.BrokerAccountSummary;
import io.github.samkelsey.backtester.broker.model.Order;
import io.github.samkelsey.backtester.datasource.DataSource;
import io.github.samkelsey.backtester.datasource.StockData;
import io.github.samkelsey.backtester.exception.BackTesterException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/* TODO
 - Create graphs of stock data showing where your algorithm bought and sold.
 - Use more of the data from the default dataset to get more info on algorithm performance
    - Add more fields to BackTestResult pojo.
 - Allow Algorithm to work through multiple datasets at a time
 */

/**
 * Main class for running and analysing back-testing simulations.
 */
public class BackTester {

    private final DataSource dataSource;
    private final Algorithm algorithm;

    public BackTester(Algorithm algorithm, DataSource dataSource) {
        this.dataSource = dataSource;
        this.algorithm = algorithm;
    }

    /**
     * A method to kick-off a back-testing simulation.
     * @return The percentage gain/loss of the simulation.
     * @throws IOException If something bad happens whilst reading test data files.
     * @throws BackTesterException If there is a simulation error.
     */
    public Broker run() throws IOException, BackTesterException {
        float startingBalance = 1_000_000f;
        Broker broker = new Broker(startingBalance);

        Map<String, Float> stockPrices = new HashMap<>();

        while (dataSource.nextFile()) {
            StockData data = null;
            Map<String, Float> percentageChanges = new HashMap<>();

            while (dataSource.hasNextData()) {
                data = dataSource.getData();
                broker.refreshBroker(data);
                BrokerAccountSummary summary = broker.createAccountSummary();
                Order order = algorithm.run(data, summary);
                broker.placeOrder(order);
            }

            if (data != null) {
                BrokerAccountSummary tickerSummary = broker.createAccountSummary();
                stockPrices.put(data.getTicker(), data.getStockPrice());
            }
        }

        return broker;
    }
}
