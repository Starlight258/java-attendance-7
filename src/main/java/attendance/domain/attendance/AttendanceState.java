package attendance.domain.attendance;

import attendance.util.TimeUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record AttendanceState(LocalDateTime attendanceTime, AttendanceType attendanceType) {

    public static AttendanceState makeDefault(final LocalDate now, final int day) {
        LocalDateTime time = TimeUtils.alterDay(now, day);
        return new AttendanceState(time, AttendanceType.getAttendanceType(time));
    }

    public static AttendanceState makeAttendance(final LocalDateTime time) {
        return new AttendanceState(time, AttendanceType.getAttendanceType(time));
    }

    public boolean isDefaultValue() {
        return LocalTime.MIN.equals(attendanceTime.toLocalTime());
    }
}


