package exceptions;

public class CarAlreadyExistsException extends Exception {
    public CarAlreadyExistsException() {
        super();
    }

    public CarAlreadyExistsException(String message) {
        super(message);
    }
}
