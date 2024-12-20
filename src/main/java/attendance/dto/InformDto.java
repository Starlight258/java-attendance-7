package attendance.dto;

import attendance.domain.AttendanceType;
import attendance.util.TimeFormatter;
import java.time.LocalDateTime;

// "12월 %02d일 %s요일 %02d:%02d (%s)"
public record InformDto(String time, String attendanceType) {

    public static InformDto of(final LocalDateTime time, final AttendanceType attendanceType) {
        return new InformDto(TimeFormatter.makeDateTimeMessage(time), attendanceType.name());
    }
}
