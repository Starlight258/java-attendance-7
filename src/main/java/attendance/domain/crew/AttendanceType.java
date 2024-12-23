package attendance.domain.crew;

import attendance.domain.campus.CampusEducationTime;
import attendance.domain.campus.CampusOperationTime;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public enum AttendanceType {
    출석, 지각, 결석, NONE;

    private static final int ABSENT_THRESHOLD = 30;
    private static final int LATE_THRESHOLD = 5;

    public static AttendanceType getAttendanceType(final LocalDateTime attendanceTime) {
        CampusOperationTime.checkOperationTime(attendanceTime.toLocalTime());
        return calculateType(attendanceTime);
    }

    private static AttendanceType calculateType(final LocalDateTime attendanceTime) {
        CampusEducationTime campusEducationTime = CampusEducationTime.of(attendanceTime);
        LocalTime startTime = campusEducationTime.getStartTime();
        long minute = ChronoUnit.MINUTES.between(startTime, attendanceTime.toLocalTime());
        if (minute > ABSENT_THRESHOLD) {
            return AttendanceType.결석;
        }
        if (minute > LATE_THRESHOLD) {
            return AttendanceType.지각;
        }
        return AttendanceType.출석;
    }

    public boolean isAbsent() {
        return this == 결석 || this == NONE;
    }
}
