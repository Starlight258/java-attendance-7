package attendance.domain.crew;

import static attendance.exception.ErrorMessage.INVALID_ATTENDANCE_DAY;
import static attendance.exception.ErrorMessage.INVALID_NICKNAME;

import attendance.domain.attendance.AttendanceResult;
import attendance.domain.campus.Campus;
import attendance.exception.CustomIllegalArgumentException;
import attendance.util.TimeFormatter;
import attendance.util.TimeUtils;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CrewHistories {

    private static final int FIRST_DAY = 1;

    private final Map<String, CrewHistory> histories;
    private final Campus campus;

    public CrewHistories(final Map<String, CrewHistory> histories, final Campus campus) {
        this.histories = new HashMap<>(histories);
        this.campus = campus;
    }

    public void initialize(final LocalDateTime now, final String name, final LocalDateTime time) {
        checkTime(time);
        if (notContains(name)) {
            histories.put(name, new CrewHistory(now, getWeekday(now)));
        }
        CrewHistory crewHistory = histories.get(name);
        crewHistory.add(time);
    }

    public boolean notContains(final String nickname) {
        return !histories.containsKey(nickname);
    }

    public void addHistory(final String name, final LocalDateTime time) {
        checkTime(time);
        CrewHistory crewHistory = getCrewHistory(name);
        crewHistory.add(time);
    }

    public LocalDateTime modifyTime(final String nickname, final LocalDateTime time) {
        checkTime(time);
        CrewHistory crewHistory = getCrewHistory(nickname);
        return crewHistory.modify(time);
    }

    public CrewHistory getCrewHistory(final String name) {
        if (notContains(name)) {
            throw new CustomIllegalArgumentException(INVALID_NICKNAME);
        }
        return histories.get(name);
    }

    public void checkDate(final LocalDateTime date) {
        if (campus.isNotOperationDay(date)) {
            throw new CustomIllegalArgumentException(
                    INVALID_ATTENDANCE_DAY.getMessage(TimeFormatter.makeDateMessage(date)));
        }
    }

    public Map<String, AttendanceResult> makeSortedResults(int today) {
        Map<String, AttendanceResult> results = histories.entrySet().stream()
                .collect(Collectors.toMap(Entry::getKey, entry -> entry.getValue().makeResult(today)));
        return sort(results);
    }

    private Map<String, AttendanceResult> sort(final Map<String, AttendanceResult> results) {
        return results.entrySet().stream()
                .sorted(this::compareResult)
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (oldVal, newVal) -> newVal,
                        LinkedHashMap::new));
    }

    private int compareResult(final Entry<String, AttendanceResult> entry,
                              final Entry<String, AttendanceResult> compared) {
        AttendanceResult result = entry.getValue();
        AttendanceResult comparedResult = compared.getValue();
        int absentCount = result.calculateAbsentCountWithLate();
        int lateCount = result.calculateLateCountWithoutAbsent();
        int comparedAbsentCount = comparedResult.calculateAbsentCountWithLate();
        int comparedLateCount = comparedResult.calculateLateCountWithoutAbsent();
        if (absentCount == comparedAbsentCount) {
            if (lateCount == comparedLateCount) {
                return entry.getKey().compareTo(compared.getKey());
            }
            return Integer.compare(comparedLateCount, lateCount);
        }
        return Integer.compare(comparedAbsentCount, absentCount);
    }

    private void checkTime(final LocalDateTime time) {
        campus.checkOperationTime(time.toLocalTime());
    }

    private List<Integer> getWeekday(LocalDateTime now) {
        return IntStream.range(FIRST_DAY, now.getDayOfMonth())
                .filter(day -> !campus.isNotOperationDay(TimeUtils.makeDay(now, day)))
                .boxed()
                .toList();
    }
}
