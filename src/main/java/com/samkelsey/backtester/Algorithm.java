package com.samkelsey.backtester;


import com.samkelsey.backtester.dto.BrokerAccountSummary;
import com.samkelsey.backtester.dto.Order;
import com.samkelsey.backtester.dto.StockData;

/**
 * A base class to define trading algorithms.
 * <h3>To be implemented by user.</h3>
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
