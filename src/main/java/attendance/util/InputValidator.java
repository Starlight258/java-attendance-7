package attendance.util;

import attendance.exception.CustomIllegalArgumentException;
import attendance.exception.ErrorMessage;
import java.util.regex.Pattern;

public class InputValidator {

    private InputValidator() {
    }

    public static void validateNotNullOrBlank(final String input, final ErrorMessage message) {
        if (input == null || input.isBlank()) {
            throw new CustomIllegalArgumentException(message);
        }
    }

    public static void validatePattern(String input, Pattern pattern, final ErrorMessage message) {
        if (!pattern.matcher(input).matches()) {
            throw new CustomIllegalArgumentException(message);
        }
    }

}
