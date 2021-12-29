package Exceptions;

public class DataSourceException extends Exception {

    public DataSourceException(String message) {
        super(message);
    }

    public DataSourceException(String message, Throwable err) {
        super(message, err);
    }
}
