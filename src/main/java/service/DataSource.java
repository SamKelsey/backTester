package service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import dto.StockData;
import dto.mapper.StockDataMapper;
import exceptions.DataSourceException;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DataSource {

    private CSVReader reader;
    private final StockDataMapper stockDataMapper;

    private final List<File> files;
    private int currentFileIndex = 0;
    private String[] currentFileHeaders;

    /**
     * Default constructor which will use default test data.
     * @throws IOException If something bad happens whilst initializing the object.
     */
    public DataSource() throws IOException {
        stockDataMapper = (dataRow) -> new StockData(Float.parseFloat(dataRow[4]), getCurrentFileName());
        files = getFiles("src/main/resources/test_data/");
        instantiateFile(files.get(0));
    }

    /**
     * Constructor for specifying custom test data.
     * @param testDataPath The directory that contains <strong>only</strong> the custom test data.
     * @param stockDataMapper An implementation of the StockDataMapper interface.
     * @throws IOException If something bad happens whilst initializing the object.
     */
    public DataSource(String testDataPath, StockDataMapper stockDataMapper) throws IOException {
        this.stockDataMapper = stockDataMapper;
        files = getFiles(testDataPath);
        instantiateFile(files.get(0));
    }

    /**
     * A method to check if there is another row of data to be read from the current file.
     * @return boolean
     * @throws IOException If something bad happens when reading the file.
     */
    public boolean hasNextData() throws IOException {
        return reader.peek() != null;
    }

    /**
     * A method responsible for returning the next row of data.
     * @return A StockData data object, representing the next row of csv data.
     * @throws IOException If something bad happens whilst reading the data.
     * @throws DataSourceException If there is an issue with the validity of the data.
     */
    public StockData getData() throws IOException, DataSourceException {
        try {
            String[] row = reader.readNext();
            if (row == null) {
                return null;
            }

            StockData stockData = stockDataMapper.toStockData(row);

            /* If a ticker isn't provided, it's assumed to be the file name. */
            if (stockData.getTicker() == null) {
                stockData.setTicker(getCurrentFileName());
            }

            return stockData;
        } catch (CsvValidationException err) {
            throw new DataSourceException(
                    String.format(
                            "An error occurred reading from test data named: %s",
                            getCurrentFileName()
                    ),
                    err
            );
        }
    }

    /**
     * Method to increment onto the next data file.
     * @throws DataSourceException If there is no next file.
     * @throws IOException If something bad happens whilst initialising the new file.
     */
    public void nextFile() throws DataSourceException, IOException {
        if (!hasNextFile()) {
            throw new DataSourceException("There is no next test data file.");
        }
        currentFileIndex += 1;
        instantiateFile(files.get(currentFileIndex));
    }

    /**
     * A method to tell if there is another set of data to run.
     * @return True, if there is a next file. False, if not.
     */
    public boolean hasNextFile() {
        return currentFileIndex < files.size() - 1;
    }

    /**
     * A method responsible for initialising a new file in the class.
     * This will set the reader on the new file and save the file's column headers.
     * @param file The file to be initialised.
     * @throws IOException If something bad happens whilst trying to read the new file.
     */
    private void instantiateFile(File file) throws IOException {
        reader = new CSVReader(new FileReader(file));
        currentFileHeaders = reader.readNextSilently();
    }

    /**
     * Lists all files in a given directory.
     * @param dir Directory to be listed.
     * @return A list of Files in the given directory.
     * @throws IOException If something bad happens whilst listing the directory.
     */
    private List<File> getFiles(String dir) throws IOException {
        List<File> files = new ArrayList<>();
        Files.list(new File(dir).toPath()).forEach(path -> files.add(path.toFile()));

        return files;
    }

    public String[] getCurrentFileHeaders() {
        return currentFileHeaders;
    }

    public String getCurrentFileName() {
        String withSuffix = files.get(currentFileIndex).getName();
        return withSuffix.substring(0, withSuffix.length() - 4);
    }

    public File getCurrentFile() {
        return files.get(currentFileIndex);
    }

    public int getCurrentFileIndex() {
        return currentFileIndex;
    }
}
