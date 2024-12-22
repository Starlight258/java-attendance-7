package attendance.domain.log;

import static attendance.exception.ErrorMessage.INVALID_NICKNAME;

import attendance.exception.CustomIllegalArgumentException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CrewLogs {

    private final Map<String, CrewLog> logs;

    public CrewLogs(final Map<String, CrewLog> logs) {
        this.logs = logs;
    }

    public void initialize(final String name, final LocalDateTime time) {
        if (logs.containsKey(name)) {
            CrewLog crewLog = logs.get(name);
            crewLog.add(time);
            return;
        }
        logs.put(name, new CrewLog(List.of(time)));
    }

    public boolean notContains(final String nickname) {
        return !logs.containsKey(nickname);
    }

    public void addLog(final String name, final LocalDateTime time) {
        CrewLog crewLog = getCrewLog(name);
        crewLog.add(time);
    }

    public CrewLog getCrewLog(final String name) {
        if (notContains(name)) {
            throw new CustomIllegalArgumentException(INVALID_NICKNAME);
        }
        return logs.get(name);
    }

    public LocalDateTime modifyTime(final String nickname, final LocalDateTime todayTime) {
        CrewLog crewLog = getCrewLog(nickname);
        return crewLog.modify(todayTime);
    }

    public Map<String, CrewLog> getLogs() {
        return Collections.unmodifiableMap(logs);
    }
}
