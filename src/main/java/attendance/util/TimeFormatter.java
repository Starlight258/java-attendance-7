package attendance.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeFormatter {

    private static final String TIME_FORMAT = "HH:mm";
    private static final String DATE_TIME_FORMAT = "MM월 dd일 E요일 HH:mm";

    public String makeTimeMessage(final LocalDateTime time) {
        return time.format(
                DateTimeFormatter.ofPattern(TIME_FORMAT)
        );
    }

    // 12월 13일 금요일 09:59 (출석)
    public String makeDateTimeMessage(final LocalDateTime time) {
        return time.format(
                DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }
}
