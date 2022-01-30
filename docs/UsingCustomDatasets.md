# How to use custom test data

BackTester allows for you to also run your
algorithm against your own test data if you
wish! See how your algorithm would perform
against your favourite stock! To do this, 
there are a few extra steps you need to follow.

1. Create a class that extends
   [Algorithm](/src/main/java/io/github/samkelsey/backtester/algorithm/Algorithm.java),
   then instantiate it.
2. Create a directory in your project that holds 
   your test data in csv files. Eg.
   `/resources/test_data/`
3. Create a class that extends [StockData](/src/main/java/io/github/samkelsey/backtester/datasource/model/StockData.java)
   and contains all the stock data fields you'd
   like your algorithm to use from your test
   data.
4. Construct an instance of [DataSource](/src/main/java/io/github/samkelsey/backtester/datasource/DataSource.java), 
   passing both an implementation of [StockDataMapper](/src/main/java/io/github/samkelsey/backtester/datasource/model/StockDataMapper.java)
   and the paths to your desired test data
   files as arguments *(in the example below, we use
   a lambda implementation of StockDataMapper.
   It only requires the data from the 6th index
   row in our test data. Yours might require
   multiple columns of data)*.
5. Instantiate a BackTester object and call the
   `run()` method on it, to get your results.
```java
public class CustomDataExample {
    
    public void example() {
       Algorithm algorithm = new ExampleAlgorithm();
       DataSource dataSource = new DataSource("path/to/test/data/", (row) ->
               new MyStockData(Float.parseFloat(row[6]))
       );

       BackTester backTester = new BackTester(algorithm, dataSource);
       Broker result = backTester.run();
    }
}
```
