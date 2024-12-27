package attendance.util;

import attendance.exception.CustomIllegalArgumentException;
import attendance.exception.ErrorMessage;
import java.util.Arrays;
import java.util.List;

public class StringParser {

    private static final int CONTAINS_EMPTY = -1;

    private StringParser() {
    }

    public static int parseToInteger(final String input, final ErrorMessage message) {
        try {
            return Integer.parseInt(input.strip());
        } catch (NumberFormatException exception) {
            throw new CustomIllegalArgumentException(message);
        }
    }

    public static List<String> parseByDelimiter(String input, String delimiter) {
        return Arrays.stream(input.split(delimiter, CONTAINS_EMPTY))
                .map(String::strip)
                .toList();
    }
}
