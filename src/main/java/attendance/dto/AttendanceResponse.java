package attendance.dto;

import attendance.domain.attendance.AttendanceType;
import java.time.LocalDateTime;

public record AttendanceResponse(LocalDateTime time, AttendanceType attendanceType) {

    public static AttendanceResponse of(final LocalDateTime time) {
        return new AttendanceResponse(time, AttendanceType.getAttendanceType(time));
    }
}
