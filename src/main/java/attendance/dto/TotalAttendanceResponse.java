package attendance.dto;

import attendance.domain.crew.CrewType;
import attendance.domain.attendance.AttendanceResult;
import java.util.List;

public record TotalAttendanceResponse(List<AttendanceResponse> responses, int attendanceCount, int lateCount,
                                      int absentCount, String subject) {
    public static TotalAttendanceResponse from(final List<AttendanceResponse> responses, AttendanceResult result) {
        int attendanceCount = result.getAttendanceCount();
        int absentCount = result.getAbsentCount();
        int lateCount = result.getLateCount();
        CrewType crewType = CrewType.from(absentCount, lateCount);
        return new TotalAttendanceResponse(responses, attendanceCount,
                lateCount, absentCount, crewType.name());
    }
}
