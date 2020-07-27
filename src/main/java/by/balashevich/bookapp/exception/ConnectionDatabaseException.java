package by.balashevich.bookapp.exception;

public class ConnectionDatabaseException extends Exception {
    public ConnectionDatabaseException() {
        super();
    }

    public ConnectionDatabaseException(String message) {
        super(message);
    }

    public ConnectionDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionDatabaseException(Throwable cause) {
        super(cause);
    }
}
