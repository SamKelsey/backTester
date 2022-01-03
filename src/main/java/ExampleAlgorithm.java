import dto.BrokerAccountSummary;
import dto.Order;
import dto.OrderType;
import dto.StockData;

public class ExampleAlgorithm extends Algorithm {

    @Override
    public Order run(StockData data, BrokerAccountSummary brokerAccountSummary) {
        if (!brokerAccountSummary.getPortfolio().containsKey(data.getTicker())) {
            return buy(100, data);
        }
        return null;
    }

    private Order buy(int units, StockData data) {
        return new Order(data.getTicker(), OrderType.BUY, units, data.getStockPrice());
    }
}
