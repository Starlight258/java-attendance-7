package attendance.domain.attendance;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AttendanceResult {

    private static final int DEFAULT_VALUE = 0;
    private static final int LATE_LIMIT = 3;

    private final Map<AttendanceType, Integer> result;

    public AttendanceResult(final Map<AttendanceType, Integer> result) {
        this.result = new HashMap<>(result);
    }

    public int getAttendanceCount() {
        return result.getOrDefault(AttendanceType.출석, DEFAULT_VALUE);
    }

    public int getLateCount() {
        return result.getOrDefault(AttendanceType.지각, DEFAULT_VALUE);
    }

    public int getAbsentCount() {
        return result.getOrDefault(AttendanceType.결석, DEFAULT_VALUE);
    }

    public int calculateAbsentCountWithLate() {
        return getAbsentCount() + getLateCount() / LATE_LIMIT;
    }

    public int calculateLateCountWithoutAbsent() {
        return getLateCount() % LATE_LIMIT;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof AttendanceResult that)) {
            return false;
        }
        return Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(result);
    }
}
