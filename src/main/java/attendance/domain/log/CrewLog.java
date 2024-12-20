package attendance.domain.log;

import static attendance.exception.ErrorMessage.INVALID_DAY_FUTURE;
import static attendance.exception.ErrorMessage.INVALID_DUPLICATE_ATTENDANCE;

import attendance.exception.CustomIllegalArgumentException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CrewLog {

    private final List<LocalDateTime> logs;

    public CrewLog(final List<LocalDateTime> logs) {
        this.logs = new ArrayList<>(logs);
    }

    public void add(final LocalDateTime time) {
        if (hasAlreadyExists(time)) {
            throw new CustomIllegalArgumentException(INVALID_DUPLICATE_ATTENDANCE);
        }
        logs.add(time);
    }

    public boolean hasAlreadyExists(final LocalDateTime input) {
        return logs.stream()
                .anyMatch(log -> log.toLocalDate().equals(input.toLocalDate()));
    }

    public LocalDateTime findExistLog(final LocalDateTime input) {
        return logs.stream()
                .filter(log -> log.toLocalDate().equals(input.toLocalDate()))
                .findFirst()
                .orElseThrow(() -> new CustomIllegalArgumentException(INVALID_DAY_FUTURE));
    }

    public LocalDateTime modify(final LocalDateTime todayTime) {
        LocalDateTime log = findExistLog(todayTime);
        logs.remove(log);
        logs.add(todayTime);
        return log;
    }

    public LocalDateTime findAllLog(final int input) {
        return logs.stream()
                .filter(log -> log.getDayOfMonth() == input)
                .findFirst()
                .orElse(null);
    }
}
