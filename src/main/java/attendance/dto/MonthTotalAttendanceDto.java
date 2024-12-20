package attendance.dto;

import attendance.domain.crew.AttendanceType;
import attendance.domain.crew.SubjectType;
import java.util.List;
import java.util.Map;

// 출석: 4회
//지각: 2회
//결석: 3회
// 대상자
public record MonthTotalAttendanceDto(List<InformDto> dtos, int attendanceCount, int lateCount,
                                      int absentCount, String subject) {
    public static MonthTotalAttendanceDto from(final List<InformDto> dtos, Map<AttendanceType, Integer> countMap) {
        Integer attendanceCount = countMap.getOrDefault(AttendanceType.출석, 0);
        Integer lateCount = countMap.getOrDefault(AttendanceType.지각, 0);
        Integer absentCount = countMap.getOrDefault(AttendanceType.결석, 0);
        SubjectType subjectType = SubjectType.from(lateCount, absentCount);
        return new MonthTotalAttendanceDto(dtos, attendanceCount,
                lateCount, absentCount, subjectType.name());
    }
}
