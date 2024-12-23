package attendance.dto;

import attendance.domain.crew.SubjectType;
import attendance.domain.attendance.AttendanceResult;
import java.util.List;

public record MonthTotalAttendanceDto(List<AttendanceDto> dtos, int attendanceCount, int lateCount,
                                      int absentCount, String subject) {
    public static MonthTotalAttendanceDto from(final List<AttendanceDto> dtos, AttendanceResult result) {
        int attendanceCount = result.getAttendanceCount();
        int lateCount = result.getLateCount();
        int absentCount = result.getAbsentCount();
        SubjectType subjectType = SubjectType.from(lateCount, absentCount);
        return new MonthTotalAttendanceDto(dtos, attendanceCount,
                lateCount, absentCount, subjectType.name());
    }
}
