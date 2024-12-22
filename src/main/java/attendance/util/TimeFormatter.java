package attendance.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeFormatter {

    private static final String TIME_FORMAT = "HH:mm";
    private static final String DATE_TIME_FORMAT = "MM월 dd일 E요일 HH:mm";
    private static final String DATE_FORMAT = "MM월 dd일 E요일";

    public static String makeTimeMessage(final LocalDateTime time) {
        return time.format(
                DateTimeFormatter.ofPattern(TIME_FORMAT)
        );
    }

    // 12월 13일 금요일 09:59
    public static String makeDateTimeMessage(final LocalDateTime time) {
        return time.format(
                DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }

    // 12월 13일 금요일
    public static String makeDateMessage(final LocalDateTime time) {
        return time.format(
                DateTimeFormatter.ofPattern(DATE_FORMAT));
    }
}
