# BackTester

A library for backtesting trading algorithms. 
BackTester can be used to test your trading
algorithms against years of historic data with
only a few lines of code. Quickly see if your
algorithm has potential or if you need to
iterate!

## Usage

To use with the default testing data, do the following.

1. Create an algorithm that extends 
   [Algorithm](/src/main/java/Algorithm.java).
   See [ExampleAlgorithm](/src/main/java/ExampleAlgorithm.java)
   for an example.
2. Instantiate an instance of [BackTester](/src/main/java/BackTester.java)
   with a [DataSource](/src/main/java/service/DataSource.java)
   and your Algorithm.
3. Call `backTester.run()` to kick-off the simulation.

**Example**
```java
DataSource dataSource = new DataSource();
Algorithm algorithm = new ExampleAlgorithm();

BackTester backTester = new BackTester(algorithm, dataSource);
backTester.run();
```

// TODO: Edit above code block to show returned output of simulation.
// Add documentation for specifying your own datasource.

