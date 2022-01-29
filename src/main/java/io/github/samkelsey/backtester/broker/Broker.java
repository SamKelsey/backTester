package io.github.samkelsey.backtester.broker;

import io.github.samkelsey.backtester.broker.model.BrokerAccountSummary;
import io.github.samkelsey.backtester.broker.model.BrokerStockData;
import io.github.samkelsey.backtester.broker.model.Order;
import io.github.samkelsey.backtester.datasource.StockData;
import io.github.samkelsey.backtester.exception.BrokerException;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * A class responsible for keeping track of monetary gains/losses during backtracking simulation
 * and mocking the buying/selling of assets.
 */
@Slf4j
public class Broker {

    private final float startingBalance;
    private float cash;
    private final Map<String, BrokerStockData> portfolio = new HashMap<>();


    public Broker(float cash) {
        this.cash = cash;
        this.startingBalance = cash;
    }

    /**
     * A method to mock placing an order with a real broker.
     * @param order The order to be placed.
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

        if (cash < order.getOrderValue()) {
            throw new BrokerException(String.format("Insufficient funds to purchase %d units of %s.",
                    order.getStockQty(),
                    order.getTicker())
            );

        }

        cash -= order.getOrderValue();

        BrokerStockData stockData = portfolio.get(order.getTicker());

        if (stockData == null) {
            stockData = createNewBrokerStockData(order);
            portfolio.put(order.getTicker(), stockData);
        } else {
            stockData.setUnitsOwned(stockData.getUnitsOwned() + order.getStockQty());
            stockData.setTotalPurchaseCost(
                    stockData.getTotalPurchaseCost() + (order.getStockQty() * order.getStockPrice())
            );
        }
    }

    private void sellStock(Order order) {

        BrokerStockData data = portfolio.get(order.getTicker());

        if (data == null || data.getUnitsOwned() < order.getStockQty()) {
            throw new BrokerException(String.format("Insufficient owned units to sell %d units of %s",
                    order.getStockQty(),
                    order.getTicker())
            );
        }

        cash += order.getOrderValue();

        BrokerStockData stockData = portfolio.get(order.getTicker());
        int ownedUnits = data.getUnitsOwned() - order.getStockQty();

        data.setUnitsOwned(ownedUnits);
        data.setTotalSalesRevenue(
                stockData.getTotalSalesRevenue() + (order.getStockQty() * order.getStockPrice())
        );
    }

    /**
     * A method for updating the stock prices in the broker's portfolio.
     * @param stockData New stock prices to implement.
     * @return {@link Broker}
     */
    public Broker refreshBroker(StockData... stockData) {
           for (StockData newData : stockData) {
               BrokerStockData currData = portfolio.get(newData.getTicker());
               if (currData == null) {
                   continue;
               }

               currData.setCurrentPrice(newData.getStockPrice());
           }

           return this;
    }

    /**
     * A method to take a snapshot of the current broker state and return a summary report of it.
     * @return BrokerAccountSummary detailing the state of the brokers account.
     */
    public BrokerAccountSummary createAccountSummary() {
        return new BrokerAccountSummary(
                portfolio,
                cash,
                startingBalance
        );
    }

    private BrokerStockData createNewBrokerStockData(Order order) {
        return new BrokerStockData(
                order.getTicker(),
                order.getStockPrice(),
                order.getStockQty(),
                order.getStockQty()* order.getStockPrice(),
                0
        );
    }


    /**
     * A method to calculate the total equity of the broker account.
     * Ensure {@link #refreshBroker(StockData...)} has recently been called
     * prior to calling this method.
     * @return The total equity of the account.
     */
    public float getTotalEquity() {
        float result = 0;
        for (String ticker : portfolio.keySet()) {
            BrokerStockData data = portfolio.get(ticker);
            result += data.getCurrentPrice() * data.getUnitsOwned();
        }

        return result + cash;
    }

    public Map<String, Float> getPercentageChanges() {
        Map<String, Float> result = new HashMap<>();

        for (String ticker : portfolio.keySet()) {
            result.put(
                    ticker,
                    portfolio.get(ticker).getPercentageChange()
            );
        }

        return result;
    }

    public Map<String, BrokerStockData> getPortfolio() {
        return portfolio;
    }

    public float getCash() {
        return cash;
    }

    public float getStartingBalance() {
        return startingBalance;
    }
}
