package attendance.domain.attendance;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class AttendanceResult {

    private final Map<AttendanceType, Integer> result;

    public AttendanceResult(final Map<AttendanceType, Integer> result) {
        this.result = new HashMap<>(result);
    }

    public int getAttendanceCount() {
        return result.getOrDefault(AttendanceType.출석, 0);
    }

    public int getLateCount() {
        return result.getOrDefault(AttendanceType.지각, 0);
    }

    public int getAbsentCount() {
        return result.entrySet().stream()
                .filter(entry -> entry.getKey().isAbsent())
                .mapToInt(Entry::getValue)
                .sum();
    }
}
