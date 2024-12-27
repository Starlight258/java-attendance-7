package attendance.dto;

import attendance.domain.crew.CrewType;
import attendance.domain.attendance.AttendanceResult;

public record CrewResponse(String name, int absentCount, int lateCount, String subjectType) {

    public static CrewResponse of(final String nickname, final AttendanceResult result) {
        int absentCount = result.getAbsentCount();
        int lateCount = result.getLateCount();
        CrewType crewType = CrewType.from(absentCount, lateCount);
        return new CrewResponse(nickname, absentCount, lateCount, crewType.name());
    }
}
