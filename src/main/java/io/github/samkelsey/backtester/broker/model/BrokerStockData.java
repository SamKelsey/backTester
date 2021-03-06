package io.github.samkelsey.backtester.broker.model;

import io.github.samkelsey.backtester.broker.BrokerImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * A data class used by {@link BrokerImpl} to hold the state of
 * each stock it currently owns.
 */
@Getter
@Setter
@AllArgsConstructor
public class BrokerStockData {

    private String ticker;
    private float currentPrice;
    private int unitsOwned;

    private float totalPurchaseCost;
    private float totalSalesRevenue;

    // Percentage gained/lost over the lifetime of trading this ticker.
    public float getPercentageChange() {
        return (((unitsOwned * currentPrice) + totalSalesRevenue) / totalPurchaseCost) * 100 - 100;
    }
}
