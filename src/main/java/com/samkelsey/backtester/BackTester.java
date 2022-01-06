package com.samkelsey.backtester;

import com.samkelsey.backtester.dto.BackTestResult;
import com.samkelsey.backtester.dto.BrokerAccountSummary;
import com.samkelsey.backtester.dto.Order;
import com.samkelsey.backtester.dto.StockData;
import com.samkelsey.backtester.exception.BackTesterException;
import com.samkelsey.backtester.service.Broker;
import com.samkelsey.backtester.service.DataSource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    public BackTestResult run() throws IOException, BackTesterException {
        float startingBalance = 1_000_000f;
        Broker broker = new Broker(startingBalance);

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

        return new BackTestResult(
                startingBalance,
                broker.getTotalEquity(stockPrices)
        );
    }
}
