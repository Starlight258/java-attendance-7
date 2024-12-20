package attendance.domain;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public enum AttendanceType {
    출석, 지각, 결석;

    public static AttendanceType getAttendanceType(final LocalDateTime attendanceTime) {
        CampusEducationTime campusEducationTime = CampusEducationTime.of(attendanceTime);
        LocalTime startTime = campusEducationTime.getStartTime();
        long minute = ChronoUnit.MINUTES.between(startTime, attendanceTime.toLocalTime());
        // 30분 초과는 **결석
        if (minute > 30) {
            return AttendanceType.결석;
        }
        // 5분 초과시 지각
        if (minute > 5) {
            return AttendanceType.지각;
        }
        return AttendanceType.출석;
    }
}
