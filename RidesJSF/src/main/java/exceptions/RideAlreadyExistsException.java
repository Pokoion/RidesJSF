package exceptions;

public class RideAlreadyExistsException extends Exception {
    public RideAlreadyExistsException() {
        super();
    }

    public RideAlreadyExistsException(String message) {
        super(message);
    }
}
