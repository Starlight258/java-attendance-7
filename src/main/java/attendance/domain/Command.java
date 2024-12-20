package attendance.domain;

import attendance.exception.CustomIllegalArgumentException;
import attendance.exception.ErrorMessage;
import java.util.Arrays;

public enum Command {

    // 1. 출석 확인
    //2. 출석 수정
    //3. 크루별 출석 기록 확인
    //4. 제적 위험자 확인
    //Q. 종료
    ATTENDANCE_CHECK("1"), ATTENDANCE_MODIFY("2"), ATTENDANCE_CREW_LOG("3"),
    ATTENDANCE_DANGER("4"), QUIT("Q");

    private final String value;

    Command(final String value) {
        this.value = value;
    }

    public static Command from(String input) {
        return Arrays.stream(values())
                .filter(command -> command.value.equals(input))
                .findFirst()
                .orElseThrow(() -> new CustomIllegalArgumentException(ErrorMessage.INVALID_FORMAT));
    }
}
