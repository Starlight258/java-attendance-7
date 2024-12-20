package attendance.util;

import attendance.exception.CustomIllegalArgumentException;
import attendance.exception.ErrorMessage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeParser {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    public static LocalDateTime toLocalDateTime(final String text) {
        try {
            DateTimeFormatter pattern = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
            return LocalDateTime.parse(text, pattern);
        } catch (DateTimeParseException exception) {
            throw new CustomIllegalArgumentException(ErrorMessage.INVALID_FILE_FORMAT);
        }
    }

}
