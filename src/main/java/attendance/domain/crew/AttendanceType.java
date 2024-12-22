package attendance.domain.crew;

import static attendance.domain.campus.CampusOperationTime.운영시간;

import attendance.domain.campus.CampusEducationTime;
import attendance.exception.CustomIllegalArgumentException;
import attendance.exception.ErrorMessage;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public enum AttendanceType {
    출석, 지각, 결석, NONE;

    public static AttendanceType getAttendanceType(final LocalDateTime attendanceTime) {
        checkOperationTime(attendanceTime.toLocalTime());
        return calculateType(attendanceTime);
    }

    public boolean isAbsent() {
        return this == 결석 || this == NONE;
    }

    private static AttendanceType calculateType(final LocalDateTime attendanceTime) {
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

    private static void checkOperationTime(final LocalTime time) {
        if (time.equals(운영시간.getStartTime()) || time.equals(운영시간.getEndTime())
                || (time.isAfter(운영시간.getStartTime()) && time.isBefore(운영시간.getEndTime()))) {
            return;
        }
        throw new CustomIllegalArgumentException(ErrorMessage.INVALID_CAMPUS_OPERATION_TIME);
    }
}
