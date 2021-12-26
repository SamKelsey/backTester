/**
 * A base class to be implemented by users to define their trading algorithm.
 */
public abstract class Algorithm {

    private final Broker broker;
    private final DataSource dataSource;

    public Algorithm() {
        this.broker = new Broker();
        this.dataSource = new DataSource();
    }

    public abstract void run();
}
