package attendance.dto;

import attendance.domain.attendance.AttendanceType;
import attendance.util.TimeFormatter;
import java.time.LocalDateTime;

// "12월 %02d일 %s요일 %02d:%02d (%s)"
public record InformDto(String time, String attendanceType) {

    private static final String EMPTY = " --:--";

    public static InformDto of(final LocalDateTime time) {
        return new InformDto(TimeFormatter.makeDateTimeMessage(time), AttendanceType.getAttendanceType(time).name());
    }

    public static InformDto of(final LocalDateTime time, final AttendanceType type) {
        if (time.getHour() == 0 && time.getMinute() == 0) {
            return new InformDto(TimeFormatter.makeDateMessage(time) + EMPTY, AttendanceType.결석.name());
        }
        return new InformDto(TimeFormatter.makeDateTimeMessage(time), type.name());
    }
}
