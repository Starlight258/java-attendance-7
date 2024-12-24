package attendance.service;

import static attendance.exception.ErrorMessage.INVALID_NICKNAME;

import attendance.domain.attendance.AttendanceResult;
import attendance.domain.crew.CrewType;
import attendance.domain.crew.CrewHistory;
import attendance.domain.crew.CrewHistories;
import attendance.dto.CrewDto;
import attendance.dto.AttendanceDto;
import attendance.dto.ModifyDto;
import attendance.dto.TotalAttendanceDto;
import attendance.exception.CustomIllegalArgumentException;
import attendance.exception.ErrorMessage;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AttendanceService {

    private final CrewHistories crewHistories;

    public AttendanceService(final CrewHistories crewHistories) {
        this.crewHistories = crewHistories;
    }

    public void checkAttendanceDate(final LocalDateTime date) {
        crewHistories.checkDate(date);
    }

    public void checkModifyDate(final LocalDateTime now, final LocalDateTime date) {
        if (date.toLocalDate().isAfter(now.toLocalDate())) {
            throw new CustomIllegalArgumentException(ErrorMessage.INVALID_DAY_FUTURE);
        }
        crewHistories.checkDate(date);
    }

    public void checkNickname(final String nickname) {
        if (crewHistories.notContains(nickname)) {
            throw new CustomIllegalArgumentException(INVALID_NICKNAME);
        }
    }

    public AttendanceDto processAttendance(final String nickname, final LocalDateTime time) {
        crewHistories.addHistory(nickname, time);
        return AttendanceDto.of(time);
    }

    public ModifyDto modifyTime(final String nickname, final LocalDateTime todayTime) {
        LocalDateTime previousTime = crewHistories.modifyTime(nickname, todayTime);
        return ModifyDto.of(previousTime, todayTime);
    }

    public TotalAttendanceDto checkCrewHistory(final String nickname, final int day) {
        CrewHistory crewHistory = crewHistories.getCrewHistory(nickname);
        List<AttendanceDto> dtos = convertToInformDtos(day, crewHistory);
        AttendanceResult result = crewHistory.makeResult(day);
        return TotalAttendanceDto.from(dtos, result);
    }

    public List<CrewDto> checkDangerCrew(final LocalDateTime now) {
        Map<String, AttendanceResult> results = crewHistories.makeSortedResults(now.getDayOfMonth());
        return results.entrySet().stream()
                .map(entry -> CrewDto.of(entry.getKey(), entry.getValue()))
                .filter(dto -> !Objects.equals(dto.subjectType(), CrewType.NONE.name()))
                .toList();
    }

    private List<AttendanceDto> convertToInformDtos(final int day, final CrewHistory crewHistory) {
        return crewHistory.getAttendanceStateUntilYesterday(day).stream()
                .map(value -> AttendanceDto.of(value.attendanceTime(), value.attendanceType()))
                .toList();
    }
}
