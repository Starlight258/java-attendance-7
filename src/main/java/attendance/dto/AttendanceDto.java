package attendance.dto;

import attendance.domain.attendance.AttendanceType;
import attendance.util.TimeFormatter;
import java.time.LocalDateTime;

public record AttendanceDto(String time, String attendanceType) {

    private static final String EMPTY = " --:--";

    public static AttendanceDto of(final LocalDateTime time) {
        return new AttendanceDto(TimeFormatter.makeDateTimeMessage(time), AttendanceType.getAttendanceType(time).name());
    }

    public static AttendanceDto of(final LocalDateTime time, final AttendanceType type) {
        if (isDefaultTime(time)) {
            return new AttendanceDto(TimeFormatter.makeDateMessage(time) + EMPTY, AttendanceType.결석.name());
        }
        return new AttendanceDto(TimeFormatter.makeDateTimeMessage(time), type.name());
    }

    private static boolean isDefaultTime(final LocalDateTime time) {
        return time.getHour() == 0 && time.getMinute() == 0;
    }
}
