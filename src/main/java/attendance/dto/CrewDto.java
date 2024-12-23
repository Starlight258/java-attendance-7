package attendance.dto;

import attendance.domain.crew.CrewType;
import attendance.domain.attendance.AttendanceResult;

public record CrewDto(String name, int absentCount, int lateCount, String subjectType) {

    public static CrewDto of(final String nickname, final AttendanceResult result) {
        int lateCount = result.getLateCount();
        int absentCount = result.getAbsentCount();
        CrewType crewType = CrewType.from(lateCount, absentCount);
        return new CrewDto(nickname, absentCount, lateCount, crewType.name());
    }
}
