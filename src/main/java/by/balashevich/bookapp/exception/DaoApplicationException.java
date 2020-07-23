package by.balashevich.bookapp.exception;

public class DaoApplicationException extends Exception{
    public DaoApplicationException() {
        super();
    }

    public DaoApplicationException(String message) {
        super(message);
    }

    public DaoApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoApplicationException(Throwable cause) {
        super(cause);
    }
}
