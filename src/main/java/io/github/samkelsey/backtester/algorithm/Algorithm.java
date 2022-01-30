package io.github.samkelsey.backtester.algorithm;


import io.github.samkelsey.backtester.broker.BrokerReader;
import io.github.samkelsey.backtester.broker.model.Order;
import io.github.samkelsey.backtester.datasource.model.StockData;

/**
 * A base class to define trading algorithms.
 * <strong>To be implemented by user.</strong>
 */
public interface Algorithm {

    /**
     * The main method for an Algorithm that runs the algorithm against a given data entry.
     * @param data An object containing the required data for the algorithm.
     * @param brokerReader An object providing read-only access to the current state of the broker (bank account).
     * @return An Order object, detailing the resulting action recommended by the algorithm. Null, if nothing.
     */
    Order run(StockData data, BrokerReader brokerReader);
}
