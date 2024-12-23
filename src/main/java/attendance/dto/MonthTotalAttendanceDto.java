package attendance.dto;

import attendance.domain.crew.SubjectType;
import attendance.domain.attendance.AttendanceResult;
import java.util.List;

// 출석: 4회
//지각: 2회
//결석: 3회
// 대상자
public record MonthTotalAttendanceDto(List<InformDto> dtos, int attendanceCount, int lateCount,
                                      int absentCount, String subject) {
    public static MonthTotalAttendanceDto from(final List<InformDto> dtos, AttendanceResult result) {
        int attendanceCount = result.getAttendanceCount();
        int lateCount = result.getLateCount();
        int absentCount = result.getAbsentCount();
        SubjectType subjectType = SubjectType.from(lateCount, absentCount);
        return new MonthTotalAttendanceDto(dtos, attendanceCount,
                lateCount, absentCount, subjectType.name());
    }
}
