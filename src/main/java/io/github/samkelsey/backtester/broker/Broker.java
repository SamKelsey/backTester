package io.github.samkelsey.backtester.broker;

import io.github.samkelsey.backtester.broker.model.BrokerStockData;
import io.github.samkelsey.backtester.broker.model.Order;

import java.util.Map;

/**
 * An object that is responsible for querying a broker api.
 */
public interface Broker {

    /**
     * A method that places an {@link io.github.samkelsey.backtester.broker.model.Order} with the broker.
     * @param order The order to be placed.
     */
    void placeOrder(Order order);

    Map<String, BrokerStockData> getPortfolio();

    float getCash();

    float getTotalEquity();
}
