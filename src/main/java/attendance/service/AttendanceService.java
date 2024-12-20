package attendance.service;

import attendance.domain.AttendanceType;
import attendance.domain.CampusTimeChecker;
import attendance.domain.CrewLog;
import attendance.domain.CrewLogs;
import attendance.domain.MonthLogProcessor;
import attendance.domain.SubjectType;
import attendance.dto.CrewDto;
import attendance.dto.InformDto;
import attendance.dto.ModifyDto;
import attendance.dto.MonthTotalAttendanceDto;
import attendance.exception.CustomIllegalArgumentException;
import attendance.exception.ErrorMessage;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

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

    public MonthTotalAttendanceDto checkCrewLog(final String nickname, final LocalDateTime now) {
        CrewLog crewLog = crewLogs.getCrewLog(nickname);
        List<Integer> weekdays = campusTimeChecker.getWeekday(now);
        MonthLogProcessor monthLogProcessor = new MonthLogProcessor(now, weekdays, crewLog);
        Map<LocalDateTime, AttendanceType> result = monthLogProcessor.getResult();
        List<InformDto> dtos = result.entrySet().stream()
                .map(entry -> InformDto.of(entry.getKey(), entry.getValue()))
                .toList();
        Map<AttendanceType, Integer> countMap = monthLogProcessor.getTotalCount();
        return MonthTotalAttendanceDto.from(dtos, countMap);
    }

    public List<CrewDto> checkDangerCrew(final LocalDateTime now) {
        List<CrewDto> dtos = new ArrayList<>();
        Map<String, CrewLog> logs = crewLogs.getLogs();
        for (Entry<String, CrewLog> entry : logs.entrySet()) {
            Optional<CrewDto> crewDto = makeEachCrew(now, entry);
            crewDto.ifPresent(dtos::add);
        }
        sort(dtos);
        return dtos;
    }

    private void sort(final List<CrewDto> dtos) {
        dtos.sort((dto1, dto2) -> {
            int absentCount1 = calculateAbsentCount(dto1);
            int lateCount1 = calculateLateCount(dto1);
            int absentCount2 = calculateAbsentCount(dto2);
            int lateCount2 = calculateLateCount(dto2);
            if (absentCount1 == absentCount2) {
                if (lateCount1 == lateCount2) {
                    return dto1.name().compareTo(dto2.name());
                }
                return Integer.compare(lateCount2, lateCount1);
            }
            return Integer.compare(absentCount2, absentCount1);
        });
    }

    private int calculateAbsentCount(final CrewDto dto) {
        return dto.absentCount() + dto.lateCount() / 3;
    }

    private int calculateLateCount(final CrewDto dto) {
        return dto.lateCount() % 3;
    }

    private Optional<CrewDto> makeEachCrew(final LocalDateTime now, final Entry<String, CrewLog> entry) {
        List<Integer> weekdays = campusTimeChecker.getWeekday(now);
        MonthLogProcessor monthLogProcessor = new MonthLogProcessor(now, weekdays, entry.getValue());
        Map<AttendanceType, Integer> result = monthLogProcessor.getTotalCount();
        SubjectType subjectType = SubjectType.from(result);
        if (subjectType == SubjectType.NONE) {
            return Optional.empty();
        }
        return Optional.of(CrewDto.of(entry.getKey(), result));
    }
}
