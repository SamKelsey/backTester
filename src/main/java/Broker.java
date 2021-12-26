import Exceptions.BrokerException;

import java.util.HashMap;
import java.util.Map;

/**
 * A class responsible for keeping track of monetary gains/losses during backtracking simulation
 * and mocking the buying/selling of assets.
 */
public class Broker {

    private float balance;
    private final Map<String, Integer> portfolio = new HashMap<>();

    public Broker() {
        this.balance = 1_000_000;
    }

    public Broker(int balance) {
        this.balance = balance;
    }

    /**
     * A method to mock placing an order with a real broker.
     * @param order - The order to be placed.
     */
    public void placeOrder(Order order) {
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

            if (balance < order.getOrderValue()) {
                throw new BrokerException(String.format("Insufficient funds to purchase %d units of %s.",
                        order.getStockQty(),
                        order.getTicker())
                );
            }

            balance -= order.getOrderValue();
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

        balance += order.getOrderValue();
        portfolio.put(
                order.getTicker(),
                portfolio.get(order.getTicker()) - order.getStockQty()
        );
    }

    public float getBalance() {
        return balance;
    }

    public Map<String, Integer> getPortfolio() {
        return portfolio;
    }
}
