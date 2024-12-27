package attendance.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeFormatter {

    private static final String TIME_FORMAT = "HH:mm";
    private static final String DATE_FORMAT = "MM월 dd일 E요일";
    private static final String BLANK = " ";
    private static final String DATE_TIME_FORMAT = DATE_FORMAT + BLANK + TIME_FORMAT;

    public static String makeTimeMessage(final LocalTime time) {
        return time.format(
                DateTimeFormatter.ofPattern(TIME_FORMAT)
        );
    }

    public static String makeDateMessage(final LocalDate time) {
        return time.format(
                DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    public static String makeDateTimeMessage(final LocalDateTime time) {
        return time.format(
                DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }
}
