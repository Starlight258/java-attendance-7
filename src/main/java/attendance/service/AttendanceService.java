package attendance.service;

import attendance.domain.CampusTimeChecker;
import attendance.domain.CrewLogs;
import attendance.dto.InformDto;
import attendance.dto.ModifyDto;
import attendance.exception.CustomIllegalArgumentException;
import attendance.exception.ErrorMessage;
import java.time.LocalDateTime;

public class AttendanceService {

    private final CrewLogs crewLogs;
    private final CampusTimeChecker campusTimeChecker;

    public AttendanceService(final CrewLogs crewLogs, final CampusTimeChecker campusTimeChecker) {
        this.crewLogs = crewLogs;
        this.campusTimeChecker = campusTimeChecker;
    }

    public void checkAttendanceDate(final LocalDateTime date) {
        campusTimeChecker.checkDate(date);
    }

    public void checkModifyDate(final LocalDateTime now, final LocalDateTime date) {
        if (date.toLocalDate().isAfter(now.toLocalDate())) {
            throw new CustomIllegalArgumentException(ErrorMessage.INVALID_DAY_FUTURE);
        }
        campusTimeChecker.checkDate(date);
    }

    public void checkNickname(final String nickname) {
        crewLogs.checkNickname(nickname);
    }

    public InformDto processAttendance(final String nickname, final LocalDateTime time) {
        crewLogs.addLog(nickname, time);
        return InformDto.of(time);
    }

    public ModifyDto modifyTime(final String nickname, final LocalDateTime todayTime) {
        LocalDateTime previousTime = crewLogs.modifyTime(nickname, todayTime);
        return ModifyDto.of(previousTime, todayTime);
    }
}
