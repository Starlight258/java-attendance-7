package attendance.domain.log;

import static attendance.exception.ErrorMessage.INVALID_ATTENDANCE_DAY;
import static attendance.exception.ErrorMessage.INVALID_NICKNAME;

import attendance.domain.campus.Campus;
import attendance.exception.CustomIllegalArgumentException;
import attendance.util.TimeFormatter;
import attendance.util.TimeUtils;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class CrewLogs {

    private static final int FIRST_DAY = 1;

    private final Map<String, CrewLog> logs;
    private final Campus campus;

    public CrewLogs(final Map<String, CrewLog> logs, final Campus campus) {
        this.logs = new HashMap<>(logs);
        this.campus = campus;
    }

    public void initialize(final LocalDateTime now, final String name, final LocalDateTime time) {
        checkTime(time);
        if (notContains(name)) {
            logs.put(name, new CrewLog(now, getWeekday(now)));
        }
        CrewLog crewLog = logs.get(name);
        crewLog.add(time);
    }

    public void checkTime(final LocalDateTime time) {
        campus.checkOperationTime(time.toLocalTime());
    }

    public boolean notContains(final String nickname) {
        return !logs.containsKey(nickname);
    }

    public void addLog(final String name, final LocalDateTime time) {
        checkTime(time);
        CrewLog crewLog = getCrewLog(name);
        crewLog.add(time);
    }

    public LocalDateTime modifyTime(final String nickname, final LocalDateTime time) {
        checkTime(time);
        CrewLog crewLog = getCrewLog(nickname);
        return crewLog.modify(time);
    }

    public CrewLog getCrewLog(final String name) {
        if (notContains(name)) {
            throw new CustomIllegalArgumentException(INVALID_NICKNAME);
        }
        return logs.get(name);
    }

    public void checkDate(final LocalDateTime date) {
        if (campus.isNotOperationDay(date)) {
            throw new CustomIllegalArgumentException(
                    INVALID_ATTENDANCE_DAY.getMessage(TimeFormatter.makeDateMessage(date)));
        }
    }

    private List<Integer> getWeekday(LocalDateTime now) {
        return IntStream.range(FIRST_DAY, now.getDayOfMonth())
                .filter(day -> !campus.isNotOperationDay(TimeUtils.makeDay(now, day)))
                .boxed()
                .toList();
    }

    public Map<String, CrewLog> getLogs() {
        return Collections.unmodifiableMap(logs);
    }
}
