package io.github.samkelsey.backtester.datasource;

import io.github.samkelsey.backtester.datasource.model.StockData;
import io.github.samkelsey.backtester.datasource.model.StockDataMapper;
import io.github.samkelsey.backtester.exception.DataSourceException;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Slf4j
public class DataSourceImpl implements DataSource {

    private BufferedReader reader;
    private final StockDataMapper stockDataMapper;
    private String[] filePaths = new String[]{
            "/test_data/AAPL.csv",
            "/test_data/DAL.csv",
            "/test_data/KO.csv"
    };

    // Initialised to -1, representing no file initialised.
    private int currentFileIndex = -1;


    /**
     * Default constructor which will use default test data.
     */
    public DataSourceImpl() {
        stockDataMapper = (dataRow) -> new StockData(Float.parseFloat(dataRow[4]), getCurrentFileName());
    }

    /**
     * Constructor for specifying custom test data.
     * @param stockDataMapper An implementation of the {@link StockDataMapper} interface.
     * @param filePaths Paths to all test data to be used.
     */
    public DataSourceImpl(StockDataMapper stockDataMapper, String... filePaths) {
        this.stockDataMapper = stockDataMapper;
        this.filePaths = filePaths;
    }

    /**
     * A method to check if there is another row of data to be read from the current file.
     * @return boolean
     * @throws IOException If something bad happens when reading the file.
     */
    public boolean hasNextData() throws IOException {
        int readAheadLimit = 100_000;
        reader.mark(readAheadLimit);
        String line = reader.readLine();
        reader.reset();
        return line != null;
    }

    /**
     * A method responsible for returning the next row of data.
     * @return {@link StockData} data object, representing the next row of csv data.
     *         Null, if there is no more data to be read.
     * @throws IOException If something bad happens whilst reading the data.
     * @throws DataSourceException If there is an issue with the validity of the data.
     */
    public StockData getData() throws IOException, DataSourceException {
        if (!isFileInitialised()) {
            throw new DataSourceException("No file initialised.");
        }

        String row = reader.readLine();
        if (row == null) {
            return null;
        }

        StockData stockData = stockDataMapper.toStockData(csvStringToArray(row));

        /* If a ticker isn't provided, it's assumed to be the file name. */
        if (stockData.getTicker() == null) {
            stockData.setTicker(getCurrentFileName());
        }

        return stockData;
    }

    /**
     * A method to tell if there is another test data file to run.
     * @return True, if there is a next file. False, if not.
     */
    public boolean hasNextFile() {
        return currentFileIndex < filePaths.length - 1;
    }

    /**
     * Method to increment onto the next data file.
     * @return Whether a new file was successfully incremented onto.
     * @throws DataSourceException Occurs if there is a problem initialising the test data file.
     * @throws IOException If something bad happens whilst initialising the file.
     */
    public boolean nextFile() throws DataSourceException, IOException {
        if (!hasNextFile()) {
            return false;
        }
        currentFileIndex += 1;
        initializeFile(filePaths[currentFileIndex]);
        return true;
    }


    /**
     * A method responsible for initialising a new file in the class.
     * This will set the reader on the new file and save the file's column headers to the object.
     * @param  filepath The file to be initialised.
     * @throws DataSourceException If the filepath does not exist.
     * @throws IOException If something bad happens whilst reading from the file.
     */
    private void initializeFile(String filepath) throws DataSourceException, IOException {
        InputStream resourceStream = getClass().getResourceAsStream(filepath);
        if (resourceStream == null) {
            throw new DataSourceException(
                    String.format("No file found at the following path: %s", filepath)
            );
        }
        InputStreamReader isr = new InputStreamReader(resourceStream);
        reader = new BufferedReader(isr);
        reader.readLine();
    }

    /**
     * Responsible for transforming a csv string into an array of strings.
     * @param s The string to be transformed.
     * @return An array of string values, each representing a csv value from the passed string.
     */
    private String[] csvStringToArray(String s) {
        String[] splitStrings = s.split(",");
        for (int i = 0; i < splitStrings.length; i++) {
            splitStrings[i] = splitStrings[i].trim();
        }

        return splitStrings;
    }

    /**
     * A method to return the name the current data file, <strong>without</strong> it's suffix.
     * @return The name of the current file being used by the datasource object.
     * @throws DataSourceException If there is an error parsing the filename.
     */
    public String getCurrentFileName() throws DataSourceException {
        if (!isFileInitialised()) {
            throw new DataSourceException("No file initialised.");
        }
        char[] currPath = filePaths[currentFileIndex].toCharArray();
        int start = -1;
        int end = -1;
        for (int i = currPath.length - 1; i > -1; i--) {
            if (currPath[i] == '.') {
                end = i - 1;
            }
            if (currPath[i] == '/') {
                start = i + 1;
                break;
            }
        }

        if (start == -1 || end == -1) {
            throw new DataSourceException("Could not parse test data file name.");
        }

        return String.valueOf(currPath, start, end - start + 1);
    }

    private boolean isFileInitialised() {
        return currentFileIndex >= 0 && reader != null;
    }
}
