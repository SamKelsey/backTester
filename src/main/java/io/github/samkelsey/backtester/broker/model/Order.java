package io.github.samkelsey.backtester.broker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Data class to define an order to be placed.
 */

@Getter
@Setter
@AllArgsConstructor
public class Order {

    private String ticker;
    private OrderType orderType;
    private int stockQty;
    private float stockPrice;

    public float getOrderValue() {
        return stockQty * stockPrice;
    }
}
