package attendance.dto;

import attendance.domain.attendance.AttendanceType;
import attendance.domain.crew.SubjectType;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

// 출석: 4회
//지각: 2회
//결석: 3회
// 대상자
public record MonthTotalAttendanceDto(List<InformDto> dtos, int attendanceCount, int lateCount,
                                      int absentCount, String subject) {
    public static MonthTotalAttendanceDto from(final List<InformDto> dtos, Map<AttendanceType, Integer> countMap) {
        Integer attendanceCount = countMap.getOrDefault(AttendanceType.출석, 0);
        Integer lateCount = countMap.getOrDefault(AttendanceType.지각, 0);
        Integer absentCount = calculateAbsentCount(countMap);
        SubjectType subjectType = SubjectType.from(lateCount, absentCount);
        return new MonthTotalAttendanceDto(dtos, attendanceCount,
                lateCount, absentCount, subjectType.name());
    }

    private static int calculateAbsentCount(final Map<AttendanceType, Integer> result) {
        return result.entrySet().stream()
                .filter(entry -> entry.getKey().isAbsent())
                .mapToInt(Entry::getValue)
                .sum();
    }
}
