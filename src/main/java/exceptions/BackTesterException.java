package exceptions;

public class BackTesterException extends Exception {

    public BackTesterException(String message) {
        super(message);
    }

    public BackTesterException(String message, Throwable err) {
        super(message, err);
    }

}
