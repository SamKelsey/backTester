package service;

import dto.BrokerAccountSummary;
import dto.Order;
import dto.OrderType;
import exceptions.BrokerException;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * A class responsible for keeping track of monetary gains/losses during backtracking simulation
 * and mocking the buying/selling of assets.
 */
@Slf4j
public class Broker {

    private float cash;
    private float totalEquity;
    private final Map<String, Integer> portfolio = new HashMap<>();

    public Broker() {
        this.cash = 1_000_000;
    }

    public Broker(int cash) {
        this.cash = cash;
    }

    /**
     * A method to mock placing an order with a real broker.
     * @param order - The order to be placed.
     */
    public void placeOrder(Order order) {
        if (order == null) {
            return;
        }

        log.info("Placing order: " + order);
        switch (order.getOrderType()) {
            case BUY:
                buyStock(order);
                break;
            case SELL:
                sellStock(order);
                break;
            default:
                break;
        }
    }

    private void buyStock(Order order) {
        if (order.getOrderType() == OrderType.BUY) {

            if (cash < order.getOrderValue()) {
                throw new BrokerException(String.format("Insufficient funds to purchase %d units of %s.",
                        order.getStockQty(),
                        order.getTicker())
                );
            }

            cash -= order.getOrderValue();
            portfolio.put(
                    order.getTicker(),
                    portfolio.getOrDefault(order.getTicker(), 0) + order.getStockQty()
            );
        }
    }

    private void sellStock(Order order) {
        if (portfolio.get(order.getTicker()) == null || portfolio.get(order.getTicker()) < order.getStockQty()) {
            throw new BrokerException(String.format("Insufficient share units to sell %d units of %s",
                    order.getStockQty(),
                    order.getTicker())
            );
        }

        cash += order.getOrderValue();
        int ownedUnits = portfolio.get(order.getTicker()) - order.getStockQty();

        if (ownedUnits == 0) {
            portfolio.remove(order.getTicker());
        } else {
            portfolio.put(order.getTicker(), ownedUnits);
        }
    }

    /**
     * A method to take a snapshot of the current broker state and return a summary report of it.
     * @return BrokerAccountSummary detailing the state of the brokers account.
     */
    public BrokerAccountSummary createAccountSummary() {
        return new BrokerAccountSummary(
                portfolio,
                cash
        );
    }

    public float getCash() {
        return cash;
    }

    /**
     * A method to calculate the total equity of the broker account.
     * @param stockPrices A map of the prices for all stocks currently in the brokers portfolio.
     * @return The total equity of the account.
     */
    public float getTotalEquity(Map<String, Float> stockPrices) {
        float val = 0;
        for (String key : portfolio.keySet()) {
            if (!stockPrices.containsKey(key)) {
                throw new BrokerException("Missing stock price for " + key);
            }

            val += stockPrices.get(key) * portfolio.get(key);
        }

        return val + getCash();
    }

    public Map<String, Integer> getPortfolio() {
        return portfolio;
    }
}
