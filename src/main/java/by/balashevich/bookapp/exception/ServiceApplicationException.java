package by.balashevich.bookapp.exception;

public class ServiceApplicationException extends Exception{
    public ServiceApplicationException() {
        super();
    }

    public ServiceApplicationException(String message) {
        super(message);
    }

    public ServiceApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceApplicationException(Throwable cause) {
        super(cause);
    }
}
