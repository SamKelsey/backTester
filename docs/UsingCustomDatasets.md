# How to use custom test data.

1. Follow all previous steps as with using the 
   default test data.
2. Construct an instance of DataSource, passing
   the path to your data and an implementation
   of StockDataMapper as arguments to the 
   constructor.
3. Create a class that extends StockData and
   contains all the info required by your 
   algorithm.
4. Create a mapper for mapping your csv data to
   your StockData extension. If the ticker name
   of your data is the file name, leave ticker 
   as null.

```java
Algorithm algorithm = new ExampleAlgorithm();
DataSource dataSource = new DataSource("path/to/test/data/", (row) ->
    new MyStockData(Float.parseFloat(row[6]))
);

BackTester backTester = new BackTester(algorithm, dataSource);

float profit = backTester.run();
System.out.println(profit);
```