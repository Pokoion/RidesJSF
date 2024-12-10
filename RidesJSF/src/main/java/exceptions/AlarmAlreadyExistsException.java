package exceptions;

public class AlarmAlreadyExistsException extends Exception {
    public AlarmAlreadyExistsException() {
        super();
    }

    public AlarmAlreadyExistsException(String message) {
        super(message);
    }
}
