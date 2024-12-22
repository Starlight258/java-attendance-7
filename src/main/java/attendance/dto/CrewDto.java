package attendance.dto;

import attendance.domain.crew.AttendanceType;
import attendance.domain.crew.SubjectType;
import java.util.Map;
import java.util.Map.Entry;

// - 빙티: 결석 3회, 지각 2회 (면담)
public record CrewDto(String name, int absentCount, int lateCount, String subjectType) {

    public static CrewDto of(final String nickname, final Map<AttendanceType, Integer> result) {
        Integer lateCount = result.getOrDefault(AttendanceType.지각, 0);
        int absentCount = calculateAbsentCount(result);
        SubjectType subjectType = SubjectType.from(lateCount, absentCount);
        return new CrewDto(nickname, absentCount, lateCount, subjectType.name());
    }

    private static int calculateAbsentCount(final Map<AttendanceType, Integer> result) {
        return result.entrySet().stream()
                .filter(entry -> entry.getKey().isAbsent())
                .mapToInt(Entry::getValue)
                .sum();
    }
}
