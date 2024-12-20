package attendance.dto;

import attendance.domain.AttendanceType;
import attendance.util.TimeFormatter;
import java.time.LocalDateTime;

// 12월 03일 화요일 10:07 (지각) -> 09:58 (출석) 수정 완료!
public record ModifyDto(String previousTime, String previousType, String currentTime, String currentType) {

    public static ModifyDto of(LocalDateTime previousTime, LocalDateTime currentTime) {
        return new ModifyDto(TimeFormatter.makeDateTimeMessage(previousTime),
                AttendanceType.getAttendanceType(previousTime).name(),
                TimeFormatter.makeTimeMessage(currentTime), AttendanceType.getAttendanceType(currentTime).name());
    }
}
