package attendance.domain;

import static attendance.exception.ErrorMessage.INVALID_NICKNAME;

import attendance.exception.CustomIllegalArgumentException;
import attendance.exception.ErrorMessage;
import java.time.LocalDateTime;
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

    public void checkNickname(final String nickname) {
        if (!logs.containsKey(nickname)) {
            throw new CustomIllegalArgumentException(INVALID_NICKNAME);
        }
    }

    public void addLog(final String name, final LocalDateTime time) {
        if (logs.containsKey(name)) {
            CrewLog crewLog = logs.get(name);
            crewLog.add(time);
            return;
        }
        throw new CustomIllegalArgumentException(ErrorMessage.INVALID_STATE);
    }
}
