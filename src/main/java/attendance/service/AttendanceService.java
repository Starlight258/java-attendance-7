package attendance.service;

import attendance.domain.AttendanceType;
import attendance.domain.CampusTimeChecker;
import attendance.domain.CrewLogs;
import attendance.dto.InformDto;
import java.time.LocalDateTime;

public class AttendanceService {

    private final CrewLogs crewLogs;
    private final CampusTimeChecker campusTimeChecker;

    public AttendanceService(final CrewLogs crewLogs, final CampusTimeChecker campusTimeChecker) {
        this.crewLogs = crewLogs;
        this.campusTimeChecker = campusTimeChecker;
    }

    public void checkDate(final LocalDateTime time) {
        campusTimeChecker.checkOperationDate(time);
    }

    public void checkNickname(final String nickname) {
        crewLogs.checkNickname(nickname);
    }

    public InformDto processAttendance(final String nickname, final LocalDateTime time) {
        AttendanceType attendanceType = campusTimeChecker.processTime(time);
        crewLogs.addLog(nickname, time);
        return InformDto.of(time, attendanceType);
    }
}
