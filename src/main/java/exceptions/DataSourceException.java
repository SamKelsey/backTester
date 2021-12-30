package exceptions;

public class DataSourceException extends RuntimeException {

    public DataSourceException(String message) {
        super(message);
    }

    public DataSourceException(String message, Throwable err) {
        super(message, err);
    }
}
