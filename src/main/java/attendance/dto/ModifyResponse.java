package attendance.dto;

import attendance.domain.attendance.AttendanceType;
import attendance.util.TimeFormatter;
import java.time.LocalDateTime;

public record ModifyResponse(String previousTime, String previousType, String currentTime, String currentType) {

    public static ModifyResponse of(LocalDateTime previousTime, LocalDateTime currentTime) {
        return new ModifyResponse(TimeFormatter.makeDateTimeMessage(previousTime),
                AttendanceType.getAttendanceType(previousTime).name(),
                TimeFormatter.makeTimeMessage(currentTime.toLocalTime()), AttendanceType.getAttendanceType(currentTime).name());
    }
}
