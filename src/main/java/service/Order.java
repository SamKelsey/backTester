package service;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data class to define an order to be placed.
 */

@Data
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
