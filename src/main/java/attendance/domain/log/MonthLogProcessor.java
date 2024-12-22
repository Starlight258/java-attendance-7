package attendance.domain.log;

import attendance.domain.crew.AttendanceType;
import attendance.util.TimeUtils;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MonthLogProcessor {

    private final Map<LocalDateTime, AttendanceType> result;

    public MonthLogProcessor(final LocalDateTime now, final List<Integer> weekdays, final CrewLog crewLog) {
        this.result = processMonth(now, weekdays, crewLog);
    }

    public Map<AttendanceType, Integer> getTotalCount() {
        Map<AttendanceType, Integer> totalCountResult = new HashMap<>();
        for (AttendanceType attendanceType : result.values()) {
            totalCountResult.merge(attendanceType, 1, Integer::sum);
        }
        return totalCountResult;
    }

    private Map<LocalDateTime, AttendanceType> processMonth(final LocalDateTime now, final List<Integer> weekdays,
                                                            final CrewLog crewLog) {
        Map<LocalDateTime, AttendanceType> temp = new LinkedHashMap<>();
        for (Integer weekday : weekdays) {
            LocalDateTime time = crewLog.findAllLog(now, weekday);
            if (time == null) {
                temp.put(TimeUtils.makeDay(now, weekday), AttendanceType.결석);
                continue;
            }
            temp.put(time, AttendanceType.getAttendanceType(time));
        }
        return temp;
    }

    public Map<LocalDateTime, AttendanceType> getResult() {
        return Collections.unmodifiableMap(result);
    }
}
