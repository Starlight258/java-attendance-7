package attendance.domain.attendance;

import attendance.util.TimeUtils;
import java.time.LocalDateTime;

public record AttendanceState(LocalDateTime attendanceTime, AttendanceType attendanceType) {

    public static AttendanceState makeDefault(final LocalDateTime now, final int day) {
        return new AttendanceState(TimeUtils.makeDay(now, day), AttendanceType.NONE);
    }

    public static AttendanceState makeAttendance(final LocalDateTime time) {
        return new AttendanceState(time, AttendanceType.getAttendanceType(time));
    }

    public boolean isNone() {
        return attendanceType == AttendanceType.NONE;
    }
}

