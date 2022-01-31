[![Maven Central](https://img.shields.io/maven-central/v/io.github.samkelsey/backtester.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.github.samkelsey%22%20AND%20a:%22backtester%22)
# BackTester

A library for backtesting trading algorithms. 
BackTester can be used to test your trading
algorithms against years of historic data with
only a few lines of code. Quickly see if your
algorithm has potential or if you need to
iterate!

## Maven dependency

```xml
<dependency>
   <groupId>io.github.samkelsey</groupId>
   <artifactId>backtester</artifactId>
   <version>${backtester.version}</version>
</dependency>
```

## Quickstart

To use with the default testing data, you need
only 4 lines of code. Do the following steps:

1. Create an algorithm class that implements 
   [Algorithm](/src/main/java/io/github/samkelsey/backtester/algorithm/Algorithm.java).
   See [ExampleAlgorithm](/src/main/java/io/github/samkelsey/backtester/algorithm/ExampleAlgorithm.java)
   for a basic example.
2. Instantiate an instance of [DataSourceImpl](/src/main/java/io/github/samkelsey/backtester/datasource/DataSourceImpl.java).
3. Instantiate an instance of [BackTester](/src/main/java/io/github/samkelsey/backtester/BackTester.java)
   using your Algorithm and DataSource from steps 1 & 2.
4. Call `backTester.run()` to kick-off the simulation.
5. View the results from the [BrokerReader](/src/main/java/io/github/samkelsey/backtester/broker/BrokerReader.java)
   returned by the simulation!

**Example**

```java
public class BackTesterExample {

   public static void main(String[] args) throws BackTesterException, IOException {
      Algorithm algorithm = new ExampleAlgorithm();
      DataSource dataSource = new DataSourceImpl();
     
      BackTester backTester = new BackTester(algorithm, dataSource);
     
      BrokerReader broker = backTester.run();
      broker.getPortfolio();
   }
}
```

The above example will return a [BrokerReader](/src/main/java/io/github/samkelsey/backtester/broker/BrokerReader.java),
as a result. With this, you can view the effectiveness of your
trading algorithm by looking at the final state
of your broker account! :smiley:

## Advanced uses

BackTester also allows for you to try out your
own algorithms against **your own datasets!** For
more information on how to do this, visit our
documentation.



