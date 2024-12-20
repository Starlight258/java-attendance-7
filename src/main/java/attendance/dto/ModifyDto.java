package attendance.dto;

// 12월 03일 화요일 10:07 (지각) -> 09:58 (출석) 수정 완료!
public record ModifyDto(String previousTime, String previousType, String currentTime, String currentType) {

}
