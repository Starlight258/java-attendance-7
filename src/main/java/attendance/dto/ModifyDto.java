package attendance.dto;

import attendance.domain.attendance.AttendanceType;
import attendance.util.TimeFormatter;
import java.time.LocalDateTime;

public record ModifyDto(String previousTime, String previousType, String currentTime, String currentType) {

    public static ModifyDto of(LocalDateTime previousTime, LocalDateTime currentTime) {
        return new ModifyDto(TimeFormatter.makeDateTimeMessage(previousTime),
                AttendanceType.getAttendanceType(previousTime).name(),
                TimeFormatter.makeTimeMessage(currentTime), AttendanceType.getAttendanceType(currentTime).name());
    }
}
