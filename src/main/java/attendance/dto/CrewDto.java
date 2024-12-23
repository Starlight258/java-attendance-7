package attendance.dto;

import attendance.domain.crew.SubjectType;
import attendance.domain.attendance.AttendanceResult;

// - 빙티: 결석 3회, 지각 2회 (면담)
public record CrewDto(String name, int absentCount, int lateCount, String subjectType) {

    public static CrewDto of(final String nickname, final AttendanceResult result) {
        int lateCount = result.getLateCount();
        int absentCount = result.getAbsentCount();
        SubjectType subjectType = SubjectType.from(lateCount, absentCount);
        return new CrewDto(nickname, absentCount, lateCount, subjectType.name());
    }
}
