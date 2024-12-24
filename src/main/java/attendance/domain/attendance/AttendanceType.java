package attendance.domain.attendance;

import attendance.domain.campus.CampusEducationTime;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public enum AttendanceType {

    출석, 지각, 결석;

    private static final int LATE_THRESHOLD = 5;
    private static final int ABSENT_THRESHOLD = 30;

    public static AttendanceType getAttendanceType(final LocalDateTime attendanceTime) {
        if (isDefaultValue(attendanceTime)) {
            return AttendanceType.결석;
        }
        return checkDifference(attendanceTime);
    }

    private static boolean isDefaultValue(final LocalDateTime attendanceTime) {
        return LocalTime.MIN.equals(attendanceTime.toLocalTime());
    }

    private static AttendanceType checkDifference(final LocalDateTime attendanceTime) {
        long minute = calculateDifference(attendanceTime);
        if (minute > ABSENT_THRESHOLD) {
            return AttendanceType.결석;
        }
        if (minute > LATE_THRESHOLD) {
            return AttendanceType.지각;
        }
        return AttendanceType.출석;
    }

    private static long calculateDifference(final LocalDateTime attendanceTime) {
        int startHour = CampusEducationTime.getStartHour(attendanceTime);
        LocalTime startTime = LocalTime.MIN.withHour(startHour);
        return ChronoUnit.MINUTES.between(startTime, attendanceTime.toLocalTime());
    }
}
