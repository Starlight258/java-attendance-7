package attendance.domain.log;

import attendance.domain.crew.AttendanceType;
import attendance.util.TimeUtils;
import java.time.LocalDateTime;

public class AttendanceState {

    private final AttendanceType attendanceType;
    private final LocalDateTime attendanceTime;

    public AttendanceState(final AttendanceType attendanceType, final LocalDateTime attendanceTime) {
        this.attendanceType = attendanceType;
        this.attendanceTime = attendanceTime;
    }

    public static AttendanceState makeDefault(final LocalDateTime now, final int day) {
        return new AttendanceState(AttendanceType.NONE, TimeUtils.makeDay(now, day));
    }

    public static AttendanceState makeAttendance(final LocalDateTime time) {
        return new AttendanceState(AttendanceType.getAttendanceType(time), time);
    }

    public boolean isNone() {
        return attendanceType == AttendanceType.NONE;
    }

    public AttendanceType getAttendanceType() {
        return attendanceType;
    }

    public LocalDateTime getAttendanceTime() {
        return attendanceTime;
    }
}


