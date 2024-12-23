package attendance.domain.log;

import static attendance.exception.ErrorMessage.INVALID_DAY_FUTURE;
import static attendance.exception.ErrorMessage.INVALID_DUPLICATE_ATTENDANCE;

import attendance.domain.campus.CampusOperationTime;
import attendance.domain.crew.AttendanceType;
import attendance.exception.CustomIllegalArgumentException;
import attendance.util.TimeUtils;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CrewLog {

    private static final int FIRST_DAY = 1;

    private final Map<Integer, AttendanceState> logs;

    public CrewLog(final LocalDateTime now) {
        this.logs = initialize(now, getWeekday(now));
    }

    public void add(final LocalDateTime input) {
        if (isNotNone(input)) {
            throw new CustomIllegalArgumentException(INVALID_DUPLICATE_ATTENDANCE);
        }
        logs.put(input.getDayOfMonth(), AttendanceState.makeAttendance(input));
    }

    public AttendanceState findExistLog(final LocalDateTime input) {
        if (isNotExist(input)) {
            throw new CustomIllegalArgumentException(INVALID_DAY_FUTURE);
        }
        return logs.get(input.getDayOfMonth());
    }

    public LocalDateTime modify(final LocalDateTime todayTime) {
        AttendanceState previousLog = findExistLog(todayTime);
        logs.put(todayTime.getDayOfMonth(), AttendanceState.makeAttendance(todayTime));
        return previousLog.getAttendanceTime();
    }

    public Map<AttendanceType, Integer> getTotalCount(int day) {
        return logs.entrySet().stream()
                .filter(entry -> entry.getKey() != day)
                .map(Entry::getValue)
                .collect(
                        Collectors.toMap(AttendanceState::getAttendanceType, v -> 1, Integer::sum, LinkedHashMap::new));
    }

    public Map<Integer, AttendanceState> getLogs() {
        return Collections.unmodifiableMap(logs);
    }

    private boolean isNotNone(final LocalDateTime input) {
        int day = input.getDayOfMonth();
        return logs.containsKey(day) && !logs.get(day).isNone();
    }

    private boolean isNotExist(final LocalDateTime input) {
        int day = input.getDayOfMonth();
        return !logs.containsKey(day);
    }

    private Map<Integer, AttendanceState> initialize(final LocalDateTime now, final List<Integer> weekdays) {
        return weekdays.stream()
                .collect(Collectors.toMap(key -> key,
                        v -> AttendanceState.makeDefault(now, v),
                        (x, y) -> y, LinkedHashMap::new));
    }

    private List<Integer> getWeekday(LocalDateTime now) {
        return IntStream.range(FIRST_DAY, now.getDayOfMonth())
                .filter(day -> !CampusOperationTime.isNotOperationDay(TimeUtils.makeDay(now, day)))
                .boxed()
                .toList();
    }
}
