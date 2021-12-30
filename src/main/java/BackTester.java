/**
 * Main class to kick-off backtest simulations.
 */
public class BackTester {

    private final DataSource dataSource;
    private final Algorithm algorithm;

    public BackTester(Algorithm algorithm, DataSource dataSource) {
        this.dataSource = dataSource;
        this.algorithm = algorithm;
    }

    // Kicks off simulation.
    public void run() {

    }

}
