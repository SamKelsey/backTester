import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class DefaultDataSource implements DataSource {

    public Object getData() {
        try {
            CSVReader reader = new CSVReader(new FileReader("test_data/AAPL.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

}
