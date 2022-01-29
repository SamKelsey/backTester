package io.github.samkelsey.backtester.broker.model;

import io.github.samkelsey.backtester.broker.Broker;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A data class used by {@link Broker} to hold the state of
 * each stock it currently owns.
 */
@Data
@AllArgsConstructor
public class BrokerStockData {

    private String ticker;
    private float buyPrice;
    private float currentPrice;
    private int unitsOwned;

}
