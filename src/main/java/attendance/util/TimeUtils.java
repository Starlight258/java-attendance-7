package attendance.util;

import attendance.exception.CustomIllegalArgumentException;
import attendance.exception.ErrorMessage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeUtils {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    private static final String TIME_FORMAT = "HH:mm";

    public static LocalDateTime toLocalDateTime(final String text) {
        try {
            DateTimeFormatter pattern = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
            return LocalDateTime.parse(text, pattern);
        } catch (DateTimeParseException exception) {
            throw new CustomIllegalArgumentException(ErrorMessage.INVALID_FILE_FORMAT);
        }
    }

    public static LocalTime toLocalTime(final String text) {
        try {

            DateTimeFormatter pattern = DateTimeFormatter.ofPattern(TIME_FORMAT);
            return LocalTime.parse(text, pattern);
        } catch (DateTimeParseException exception) {
            throw new CustomIllegalArgumentException(ErrorMessage.INVALID_FORMAT);
        }
    }

    public static LocalDateTime makeTodayTime(final LocalDateTime today, final LocalTime time) {
        return LocalDateTime.of(today.getYear(), today.getMonthValue(), today.getDayOfMonth(),
                time.getHour(), time.getMinute());
    }

    public static LocalDateTime toLocalDate(final LocalDateTime today, final int day) {
        return LocalDate.of(today.getYear(), today.getMonthValue(), day).atStartOfDay();
    }
}
