package io.github.samkelsey.backtester.exception;

public class DataSourceRuntimeException extends RuntimeException {

    public DataSourceRuntimeException(String message) {
        super(message);
    }

    public DataSourceRuntimeException(String message, Throwable err) {
        super(message, err);
    }

}
