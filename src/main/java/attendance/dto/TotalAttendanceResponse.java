package attendance.dto;

import attendance.domain.crew.CrewType;
import attendance.domain.attendance.AttendanceResult;
import java.util.List;

public record TotalAttendanceResponse(List<AttendanceResponse> responses, int attendanceCount, int lateCount,
                                      int absentCount, String subject) {
    public static TotalAttendanceResponse from(final List<AttendanceResponse> responses, AttendanceResult result) {
        int attendanceCount = result.getAttendanceCount();
        int lateCount = result.getLateCount();
        int absentCount = result.getAbsentCount();
        CrewType crewType = CrewType.from(lateCount, absentCount);
        return new TotalAttendanceResponse(responses, attendanceCount,
                lateCount, absentCount, crewType.name());
    }
}
