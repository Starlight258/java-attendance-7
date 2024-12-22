package attendance.domain.log;

import static attendance.exception.ErrorMessage.INVALID_DAY_FUTURE;
import static attendance.exception.ErrorMessage.INVALID_DUPLICATE_ATTENDANCE;

import attendance.exception.CustomIllegalArgumentException;
import attendance.util.TimeUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class CrewLog {

    private final Map<LocalDate, LocalDateTime> logs;

    public CrewLog(final Map<LocalDate, LocalDateTime> logs) {
        this.logs = new HashMap<>(logs);
    }

    public void add(final LocalDateTime input) {
        if (contains(input)) {
            throw new CustomIllegalArgumentException(INVALID_DUPLICATE_ATTENDANCE);
        }
        logs.put(input.toLocalDate(), input);
    }

    public LocalDateTime findExistLog(final LocalDateTime input) {
        if (!contains(input)) {
            throw new CustomIllegalArgumentException(INVALID_DAY_FUTURE);
        }
        return logs.get(input.toLocalDate());
    }

    public LocalDateTime modify(final LocalDateTime todayTime) {
        LocalDateTime previousLog = findExistLog(todayTime);
        logs.put(todayTime.toLocalDate(), todayTime);
        return previousLog;
    }

    public LocalDateTime findAllLog(final LocalDateTime now, final int inputDay) {
        LocalDate localDate = TimeUtils.makeThatDay(now, inputDay).toLocalDate();
        return logs.getOrDefault(localDate, null);
    }

    private boolean contains(final LocalDateTime input) {
        return logs.containsKey(input.toLocalDate());
    }
}
