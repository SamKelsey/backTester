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
     * Default constructor, using default test data.
     * @throws DataSourceException If something bad happens whilst initializing object.
     */
    public DataSource() {
        stockDataMapper = (dataRow) -> new StockData(Float.parseFloat(dataRow[4]), getCurrentFileName());
        try {
            files = getFiles("src/main/resources/test_data/");
        } catch (IOException e) {
            throw new DataSourceException("Failed to fetch default test data.", e);
        }
        instantiateFile(files.get(0));
    }

    /**
     * Constructor for user to specify custom test data.
     * @param testDataPath The directory that contains only custom datasets.
     * @throws DataSourceException If something bad happens whilst initializing object.
     */
    public DataSource(String testDataPath, StockDataMapper stockDataMapper) throws DataSourceException {
        this.stockDataMapper = stockDataMapper;
        try {
            files = getFiles(testDataPath);
        } catch (IOException err) {
            throw new DataSourceException(
                    String.format(
                            "Failed to fetch custom test data at '%s'.",
                            testDataPath
                    ),
                    err
            );
        }
        instantiateFile(files.get(0));
    }

    /**
     * A method to check if there is another row of data to be read from the current file.
     * @return boolean
     * @throws DataSourceException If something bad happens when reading the file.
     */
    public boolean hasNextData() throws IOException {
        boolean res;
        res = reader.peek() != null;

        return res;
    }

    /**
     * A method responsible for returning the next row of csv data.
     * @return A StockData data object, representing the next row of csv data.
     * Returns null if there is no more data in the csv.
     */
    public StockData getData() {
        try {
            String[] row = reader.readNext();
            StockData stockData = stockDataMapper.toStockData(row);

            /* If a ticker isn't provided, it's assumed to be the file name. */
            if (stockData.getTicker() == null) {
                stockData.setTicker(getCurrentFileName());
            }

            return stockData;

        } catch (CsvValidationException | IOException err) {
            throw new DataSourceException(
                    String.format(
                            "An error occurred whilst reading from test data file %s",
                            getCurrentFile().getName()
                    ),
                    err
            );
        }
    }

    /**
     * Method to increment onto the next data file.
     */
    public void nextFile() throws DataSourceException {
        if (hasNextFile()) {
            currentFileIndex += 1;
            instantiateFile(files.get(currentFileIndex));
        }
    }

    /**
     * A method to tell if there is another set of data to run.
     * @return True if there is a next file. False if not.
     */
    public boolean hasNextFile() {
        return currentFileIndex < files.size() - 1;
    }

    /**
     * A method responsible for instantiating a new file in the class.
     * This will set the reader on the new file and save the file's column headers.
     * @param file The file to be instantiated.
     * @throws DataSourceException If something bad happens whilst trying to read the new file.
     */
    private void instantiateFile(File file) throws DataSourceException {
        try {
            reader = new CSVReader(new FileReader(file));
            currentFileHeaders = reader.readNext();
        } catch (IOException | CsvValidationException err) {
            throw new DataSourceException(
                    String.format(
                            "An error occurred whilst initializing test data file %s",
                            file.getName()
                    ),
                    err
            );
        }
    }

    /**
     * Lists all files in a given directory.
     * @param dir Directory to be listed.
     * @return A list of Files in the given directory.
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
