package attendance.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class CrewLogs {

    private final Map<String, CrewLog> logs;

    public CrewLogs(final Map<String, CrewLog> logs) {
        this.logs = logs;
    }

    public void put(final String name, final LocalDateTime time) {
        if (logs.containsKey(name)) {
            CrewLog crewLog = logs.get(name);
            crewLog.add(time);
            return;
        }
        logs.put(name, new CrewLog(List.of(time)));
    }
}
