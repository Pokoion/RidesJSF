package exceptions;

public class RideMustBeLaterThanTodayException extends Exception {
    public RideMustBeLaterThanTodayException() {
        super();
    }

    public RideMustBeLaterThanTodayException(String message) {
        super(message);
    }
}
