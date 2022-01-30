package io.github.samkelsey.backtester.broker;

import io.github.samkelsey.backtester.broker.model.BrokerStockData;

import java.util.Map;

/**
 * An object that has read-only methods to a broker api.
 * Similar to {@link Broker}, except {@link BrokerReader} cannot place orders
 * or alter the state of the broker account in any way.
 */
public interface BrokerReader {

    Map<String, BrokerStockData> getPortfolio();

    float getCash();

    float getTotalEquity();

}
