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
   <version>1.0.0</version>
</dependency>
```

## Quickstart

To use with the default testing data, you need
only 4 lines of code. Do the following steps:

1. Create an algorithm class that extends 
   [Algorithm](/src/main/java/io/github/samkelsey/backtester/algorithm/Algorithm.java).
   See [ExampleAlgorithm](/src/main/java/io/github/samkelsey/backtester/algorithm/ExampleAlgorithm.java)
   for a basic example.
2. Instantiate a default instance of [DataSource](/src/main/java/io/github/samkelsey/backtester/datasource/DataSource.java).
2. Instantiate an instance of [BackTester](/src/main/java/io/github/samkelsey/backtester/BackTester.java)
   using your Algorithm and DataSource from steps 1 & 2.
3. Call `backTester.run()` to kick-off the simulation.

**Example**

```java
public class BackTesterExample {

   public void example() {

      Algorithm algorithm = new ExampleAlgorithm();
      DataSource dataSource = new DataSourceImpl();
      BackTester backTester = new BackTester(algorithm, dataSource);
      Broker result = backTester.run();
      result.get
   }
   
}
```

The above example will return a results POJO,
with which you can view the effectiveness of your
trading algorithm! :smiley:

## Advanced uses

BackTester also allows for you to try out your
own algorithms against your own datasets! For
more information on how to do this, visit our
documentation.



