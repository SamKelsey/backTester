package io.github.samkelsey.backtester;

import io.github.samkelsey.backtester.algorithm.Algorithm;
import io.github.samkelsey.backtester.broker.Broker;
import io.github.samkelsey.backtester.broker.model.BrokerAccountSummary;
import io.github.samkelsey.backtester.broker.model.Order;
import io.github.samkelsey.backtester.datasource.DataSource;
import io.github.samkelsey.backtester.datasource.StockData;
import io.github.samkelsey.backtester.exception.BackTesterException;

import java.io.IOException;


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

        while (dataSource.nextFile()) {
            while (dataSource.hasNextData()) {
                StockData data = dataSource.getData();
                broker.refreshBroker(data);
                BrokerAccountSummary summary = broker.createAccountSummary();
                Order order = algorithm.run(data, summary);
                broker.placeOrder(order);
            }
        }

        return broker;
    }
}
