package attendance.dto;

import java.util.List;

// - 빙티: 결석 3회, 지각 2회 (면담)
public record DangerCrewsDto(List<DangerDto> dtos) {

    public record DangerDto(String name, int absentCount, int lateCount, String subjectType) {

    }
}
