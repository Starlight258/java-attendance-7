package attendance.domain.command;

import attendance.exception.CustomIllegalArgumentException;
import attendance.exception.ErrorMessage;
import java.util.Arrays;

public enum Command {

    ATTENDANCE("1"), ATTENDANCE_MODIFY("2"), ATTENDANCE_CREW_HISTORY("3"),
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
