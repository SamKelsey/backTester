import dto.BrokerAccountSummary;
import dto.Order;
import dto.StockData;

/**
 * A base class to define trading algorithms.
 * <h3>To be implemented by user.</h3>
 */
public abstract class Algorithm {

    /**
     * The main method for an Algorithm that runs the algorithm against a given data entry.
     * @param data An object containing the required data for the algorithm.
     * @return An dto.Order object detailing action to take. Null, if nothing.
     * */
    public abstract Order run(StockData data, BrokerAccountSummary brokerAccountSummary);
}
