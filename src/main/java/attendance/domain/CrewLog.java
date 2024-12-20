package attendance.domain;

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
        if (logs.contains(time)) {
            throw new CustomIllegalArgumentException(INVALID_DUPLICATE_ATTENDANCE);
        }
        logs.add(time);
    }
}
