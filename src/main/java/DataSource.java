import com.opencsv.CSVReader;
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

    private final List<File> fileNames;
    private int currentFile = 0;
    private String[] currentFileHeaders;

    /**
     * Default constructor, using default test data.
     * @throws DataSourceException If something bad happens whilst initializing object.
     */
    public DataSource() throws DataSourceException {
        fileNames = getFiles("src/main/resources/test_data/");
        instantiateFile(fileNames.get(0));
    }

    /**
     * Constructor for user to specify custom test data.
     * @param testDataPath The directory that contains only custom datasets.
     * @throws DataSourceException If something bad happens whilst initializing object.
     */
    public DataSource(String testDataPath) throws DataSourceException {
        fileNames = getFiles(testDataPath);
        instantiateFile(fileNames.get(0));
    }

    /**
     * A method to check if there is another row of data to be read from the current file.
     * @return boolean
     * @throws DataSourceException If something bad happens when reading the file.
     */
    public boolean hasNextData() throws DataSourceException {
        boolean res;
        try {
            res = reader.peek() != null;
        } catch (IOException e) {
            throw new DataSourceException("Error reading line from file.", e);
        }

        return res;
    }

    /**
     * A method responsible for returning the next row of csv data.
     * @return A string array representing the next row of csv data.
     * Returns null if there is no more data in the csv.
     */
    public String[] getData() {
        String[] row = null;

        try {
            row = reader.readNext();
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return row;
    }

    /**
     * Method to increment onto the next data file.
     */
    public void nextFile() throws DataSourceException {
        if (hasNextFile()) {
            currentFile += 1;
            instantiateFile(fileNames.get(currentFile));
        }
    }

    /**
     * A method to tell if there is another set of data to run.
     * @return True if there is a next file. False if not.
     */
    public boolean hasNextFile() {
        return currentFile < fileNames.size() - 1;
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
        } catch (Exception e) {
            throw new DataSourceException("An error occurred whilst instantiating a data file.", e);
        }
    }

    /**
     * Lists all files in a given directory.
     * @param dir Directory to be listed.
     * @return A list of Files in the given directory.
     */
    public List<File> getFiles(String dir) throws DataSourceException {
        List<File> files = new ArrayList<>();

        try {
            Files.list(new File(dir).toPath()).forEach(path -> files.add(path.toFile()));
        } catch (IOException e) {
            throw new DataSourceException("Empty test data directory provided.", e);
        }

        return files;
    }

    public String[] getCurrentFileHeaders() {
        return currentFileHeaders;
    }

    public int getCurrentFile() {
        return currentFile;
    }
}
