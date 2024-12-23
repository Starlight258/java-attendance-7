package attendance.dto;

import attendance.domain.crew.CrewType;
import attendance.domain.attendance.AttendanceResult;
import java.util.List;

public record TotalAttendanceDto(List<AttendanceDto> dtos, int attendanceCount, int lateCount,
                                 int absentCount, String subject) {
    public static TotalAttendanceDto from(final List<AttendanceDto> dtos, AttendanceResult result) {
        int attendanceCount = result.getAttendanceCount();
        int lateCount = result.getLateCount();
        int absentCount = result.getAbsentCount();
        CrewType crewType = CrewType.from(lateCount, absentCount);
        return new TotalAttendanceDto(dtos, attendanceCount,
                lateCount, absentCount, crewType.name());
    }
}
