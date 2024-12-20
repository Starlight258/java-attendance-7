package attendance.dto;

import java.util.List;

// "12월 %02d일 %s요일 %02d:%02d (%s)"
public record MonthAttendanceDto(List<InformDto> dtos) {
}
