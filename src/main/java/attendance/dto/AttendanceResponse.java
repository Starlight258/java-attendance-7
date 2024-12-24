package attendance.dto;

import attendance.domain.attendance.AttendanceType;
import attendance.util.TimeFormatter;
import java.time.LocalDateTime;

public record AttendanceResponse(String time, String attendanceType) {

    private static final String EMPTY = " --:--";

    public static AttendanceResponse of(final LocalDateTime time) {
        return new AttendanceResponse(TimeFormatter.makeDateTimeMessage(time), AttendanceType.getAttendanceType(time).name());
    }

    public static AttendanceResponse of(final LocalDateTime time, final AttendanceType type) {
        if (isDefaultTime(time)) {
            return new AttendanceResponse(TimeFormatter.makeDateMessage(time) + EMPTY, AttendanceType.결석.name());
        }
        return new AttendanceResponse(TimeFormatter.makeDateTimeMessage(time), type.name());
    }

    private static boolean isDefaultTime(final LocalDateTime time) {
        return time.getHour() == 0 && time.getMinute() == 0;
    }
}
