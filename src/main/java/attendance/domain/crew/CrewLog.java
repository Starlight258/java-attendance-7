package attendance.domain.crew;

import static attendance.exception.ErrorMessage.INVALID_DAY_FUTURE;
import static attendance.exception.ErrorMessage.INVALID_DUPLICATE_ATTENDANCE;

import attendance.domain.attendance.AttendanceResult;
import attendance.domain.attendance.AttendanceState;
import attendance.exception.CustomIllegalArgumentException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class CrewLog {

    private final Map<Integer, AttendanceState> log;

    public CrewLog(final LocalDateTime now, final List<Integer> weekday) {
        this.log = initialize(now, weekday);
    }

    public void add(final LocalDateTime input) {
        if (isNotNone(input)) {
            throw new CustomIllegalArgumentException(INVALID_DUPLICATE_ATTENDANCE);
        }
        log.put(input.getDayOfMonth(), AttendanceState.makeAttendance(input));
    }

    public AttendanceState findExistLog(final LocalDateTime input) {
        if (isNotExist(input)) {
            throw new CustomIllegalArgumentException(INVALID_DAY_FUTURE);
        }
        return log.get(input.getDayOfMonth());
    }

    public LocalDateTime modify(final LocalDateTime todayTime) {
        AttendanceState previousLog = findExistLog(todayTime);
        log.put(todayTime.getDayOfMonth(), AttendanceState.makeAttendance(todayTime));
        return previousLog.attendanceTime();
    }

    public AttendanceResult makeResult(int today) {
        return new AttendanceResult(getAttendanceStateUntilToday(today).stream()
                .collect(Collectors.toMap(AttendanceState::attendanceType, v -> 1,
                        Integer::sum, LinkedHashMap::new)));
    }

    public List<AttendanceState> getAttendanceStateUntilToday(final int today) {
        return log.entrySet().stream()
                .filter(entry -> entry.getKey() != today)
                .map(Entry::getValue)
                .toList();
    }

    private boolean isNotNone(final LocalDateTime input) {
        int day = input.getDayOfMonth();
        return log.containsKey(day) && !log.get(day).isNone();
    }

    private boolean isNotExist(final LocalDateTime input) {
        int day = input.getDayOfMonth();
        return !log.containsKey(day);
    }

    private Map<Integer, AttendanceState> initialize(final LocalDateTime now, final List<Integer> weekdays) {
        return weekdays.stream()
                .collect(Collectors.toMap(key -> key,
                        v -> AttendanceState.makeDefault(now, v),
                        (x, y) -> y, LinkedHashMap::new));
    }
}