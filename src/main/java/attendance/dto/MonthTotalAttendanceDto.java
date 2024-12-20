package attendance.dto;

// 출석: 4회
//지각: 2회
//결석: 3회
// 대상자
public record MonthTotalAttendanceDto(int attendanceCount, int lateCount, int absentCount, String subject) {
}
