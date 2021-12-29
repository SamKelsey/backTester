/**
 * A base class to define trading algorithms.
 * <h3>To be implemented by user.</h3>
 */
public abstract class Algorithm {

    private final Broker broker;

    public Algorithm(Broker broker) {
        this.broker = broker;
    }

    /**
     * The main method for an Algorithm that runs the algorithm against a given data entry.
     * @param data An object containing the required data for the algorithm.
     */
    public abstract void run(Object data);
}
