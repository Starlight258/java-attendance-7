package attendance.exception;

public class CustomIllegalStateException extends IllegalStateException {

    public CustomIllegalStateException(final String message) {
        super(ErrorPrefix.format(message));
    }

    public CustomIllegalStateException(final ErrorMessage errorMessage) {
        super(ErrorPrefix.format(errorMessage.getMessage()));
    }
}
