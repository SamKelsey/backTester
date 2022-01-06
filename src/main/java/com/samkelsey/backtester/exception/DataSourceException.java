package com.samkelsey.backtester.exception;

public class DataSourceException extends BackTesterException {

    public DataSourceException(String message) {
        super(message);
    }

    public DataSourceException(String message, Throwable err) {
        super(message, err);
    }
}