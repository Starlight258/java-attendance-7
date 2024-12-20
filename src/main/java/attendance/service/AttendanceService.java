package attendance.service;

import attendance.domain.AttendanceType;
import attendance.domain.CampusTimeChecker;
import attendance.dto.InformDto;
import java.time.LocalDateTime;

public class AttendanceService {

    private final CampusTimeChecker campusTimeChecker;

    public AttendanceService(final CampusTimeChecker campusTimeChecker) {
        this.campusTimeChecker = campusTimeChecker;
    }

    public void checkDate(final LocalDateTime time) {
        campusTimeChecker.checkOperationDate(time);
    }

    public InformDto processAttendance(final LocalDateTime time) {
        AttendanceType attendanceType = campusTimeChecker.processTime(time);
        return InformDto.of(time, attendanceType);
    }
}
