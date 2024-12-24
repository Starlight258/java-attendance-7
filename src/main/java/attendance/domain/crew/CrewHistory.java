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

public class CrewHistory {

    private static final int INCREASE_UNIT = 1;

    private final Map<Integer, AttendanceState> history;

    public CrewHistory(final LocalDateTime now, final List<Integer> weekday) {
        this.history = initialize(now, weekday);
    }

    public void add(final LocalDateTime input) {
        if (hasAttendance(input)) {
            throw new CustomIllegalArgumentException(INVALID_DUPLICATE_ATTENDANCE);
        }
        history.put(input.getDayOfMonth(), AttendanceState.makeAttendance(input));
    }

    public AttendanceState findHistory(final LocalDateTime input) {
        if (isNotExist(input)) {
            throw new CustomIllegalArgumentException(INVALID_DAY_FUTURE);
        }
        return history.get(input.getDayOfMonth());
    }

    public LocalDateTime modify(final LocalDateTime todayTime) {
        AttendanceState previousHistory = findHistory(todayTime);
        history.put(todayTime.getDayOfMonth(), AttendanceState.makeAttendance(todayTime));
        return previousHistory.attendanceTime();
    }

    public AttendanceResult makeResult(int today) {
        return new AttendanceResult(getAttendanceStateUntilYesterday(today).stream()
                .collect(Collectors.toMap(AttendanceState::attendanceType, v -> INCREASE_UNIT,
                        Integer::sum, LinkedHashMap::new)));
    }

    public List<AttendanceState> getAttendanceStateUntilYesterday(final int today) {
        return history.entrySet().stream()
                .filter(entry -> entry.getKey() != today)
                .map(Entry::getValue)
                .toList();
    }

    private Map<Integer, AttendanceState> initialize(final LocalDateTime now, final List<Integer> weekdays) {
        return weekdays.stream()
                .collect(Collectors.toMap(key -> key,
                        v -> AttendanceState.makeDefault(now.toLocalDate(), v),
                        (x, y) -> y, LinkedHashMap::new));
    }

    private boolean hasAttendance(final LocalDateTime input) {
        int day = input.getDayOfMonth();
        return history.containsKey(day) && !history.get(day).isDefaultValue();
    }

    private boolean isNotExist(final LocalDateTime input) {
        int day = input.getDayOfMonth();
        return !history.containsKey(day);
    }
}
