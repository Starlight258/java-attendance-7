package attendance.exception;

public class CustomIllegalArgumentException extends IllegalArgumentException {

    public CustomIllegalArgumentException(final String message) {
        super(ErrorPrefix.format(message));
    }

    public CustomIllegalArgumentException(final ErrorMessage errorMessage) {
        super(ErrorPrefix.format(errorMessage.getMessage()));
    }

}
