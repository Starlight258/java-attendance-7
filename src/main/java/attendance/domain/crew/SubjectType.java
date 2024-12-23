package attendance.domain.crew;

import attendance.domain.attendance.AttendanceType;
import attendance.exception.CustomIllegalArgumentException;
import attendance.exception.ErrorMessage;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public enum SubjectType {
    NONE(0), 경고(2), 면담(3), 제적(6);

    private final int count;

    SubjectType(final int count) {
        this.count = count;
    }

    public static List<SubjectType> sort() {
        return Arrays.stream(SubjectType.values()).
                sorted((s1, s2) -> Integer.compare(s2.count, s1.count))
                .toList();
    }

    public static SubjectType from(final int lateCount, final int absentCount) {
        return sort().stream()
                .filter(subjectType -> subjectType.count <= absentCount + (lateCount / 3))
                .findFirst()
                .orElseThrow(() -> new CustomIllegalArgumentException(ErrorMessage.INVALID_STATE));
    }

    public static SubjectType from(final Map<AttendanceType, Integer> result) {
        Integer lateCount = result.getOrDefault(AttendanceType.지각, 0);
        Integer absentCount = calculateAbsentCount(result);
        return SubjectType.from(lateCount, absentCount);
    }

    private static int calculateAbsentCount(final Map<AttendanceType, Integer> result) {
        return result.entrySet().stream()
                .filter(entry -> entry.getKey().isAbsent())
                .mapToInt(Entry::getValue)
                .sum();
    }
}
