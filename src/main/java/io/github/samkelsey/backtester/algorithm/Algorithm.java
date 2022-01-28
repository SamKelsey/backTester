package io.github.samkelsey.backtester.algorithm;


import io.github.samkelsey.backtester.broker.BrokerAccountSummary;
import io.github.samkelsey.backtester.broker.Order;
import io.github.samkelsey.backtester.datasource.StockData;

/**
 * A base class to define trading algorithms.
 * <h2>To be implemented by user.</h2>
 */
public abstract class Algorithm {

    /**
     * The main method for an Algorithm that runs the algorithm against a given data entry.
     * @param data An object containing the required data for the algorithm.
     * @param brokerAccountSummary A data object, detailing the current state of the broker (bank account).
     * @return An Order object, detailing the resulting action recommended by the algorithm. Null, if nothing.
     */
    public abstract Order run(StockData data, BrokerAccountSummary brokerAccountSummary);
}
