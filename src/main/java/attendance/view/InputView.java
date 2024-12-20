package attendance.view;

import attendance.exception.ErrorMessage;
import attendance.util.InputValidator;
import attendance.util.TimeFormatter;
import attendance.util.TimeParser;
import camp.nextstep.edu.missionutils.Console;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class InputView {

    public String readFunction() {
        return readLine(ErrorMessage.INVALID_FORMAT);
    }

    public String readCheckNickname() {
        return readLine(ErrorMessage.INVALID_FORMAT);
    }

    public LocalTime readCheckAttendanceTime() {
        String line = readLine(ErrorMessage.INVALID_FORMAT);
        return TimeParser.toLocalTime(line);
    }

    private String readLine(ErrorMessage errorMessage) {
            String line = Console.readLine();
            InputValidator.validateNotNullOrBlank(line, errorMessage);
            return line;
        }

}
