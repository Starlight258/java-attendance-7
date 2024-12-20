package attendance.exception;

public enum ErrorMessage {

    INVALID_ATTENDANCE_DAY("12월 %d일 %s요일은 등교일이 아닙니다."),
    INVALID_NICKNAME("등록되지 않은 닉네임입니다."),
    INVALID_FORMAT("잘못된 형식을 입력하였습니다."),
    INVALID_DAY_FUTURE("아직 수정할 수 없습니다."),
    INVALID_CAMPUS_OPERATION_TIME("캠퍼스 운영 시간에만 출석이 가능합니다."),
    INVALID_DUPLICATE_ATTENDANCE("이미 출석을 확인하였습니다. 필요한 경우 수정 기능을 이용해 주세요."),
    INVALID_FILE_FORMAT("파일 형식이 잘못되었습니다"),
    INVALID_CREW_NAMES("크루 이름이 중복되었습니다"),

    ;

    private final String message;

    ErrorMessage(final String message) {
        this.message = message;
    }

    public String getMessage(Object... args) {
        return String.format(message, args);
    }

}
