package attendance.dto;

import attendance.domain.crew.CrewType;
import attendance.domain.attendance.AttendanceResult;

public record CrewResponse(String name, int absentCount, int lateCount, String subjectType) {

    public static CrewResponse of(final String nickname, final AttendanceResult result) {
        int lateCount = result.getLateCount();
        int absentCount = result.getAbsentCount();
        CrewType crewType = CrewType.from(lateCount, absentCount);
        return new CrewResponse(nickname, absentCount, lateCount, crewType.name());
    }
}
