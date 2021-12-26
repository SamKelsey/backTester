/**
 * Main class to kick-off backtest simulations.
 */
public class BackTester {

    public void example() {
        Order order = new Order("AAPL", OrderType.BUY, 2, 4);
        System.out.println(order.getOrderValue());
        System.out.println(order.toString());
    }

}
