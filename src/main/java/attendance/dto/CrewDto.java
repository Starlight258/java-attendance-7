package attendance.dto;

import attendance.domain.AttendanceType;
import attendance.domain.SubjectType;
import java.util.Map;

// - 빙티: 결석 3회, 지각 2회 (면담)
public record CrewDto(String name, int absentCount, int lateCount, String subjectType) {

    public static CrewDto of(final String nickname, final Map<AttendanceType, Integer> result) {
        Integer lateCount = result.getOrDefault(AttendanceType.지각, 0);
        Integer absentCount = result.getOrDefault(AttendanceType.결석, 0);
        SubjectType subjectType = SubjectType.from(lateCount, absentCount);
        return new CrewDto(nickname, absentCount, lateCount, subjectType.name());
    }
}
