package attendance.view;

import attendance.exception.ErrorMessage;
import attendance.util.InputValidator;
import attendance.util.StringParser;
import attendance.util.TimeUtils;
import camp.nextstep.edu.missionutils.Console;
import java.time.LocalTime;

public class InputView {

    public String readFunction() {
        return readLine(ErrorMessage.INVALID_FORMAT);
    }

    public String readNickname() {
        return readLine(ErrorMessage.INVALID_FORMAT);
    }

    public LocalTime readTime() {
        String line = readLine(ErrorMessage.INVALID_FORMAT);
        return TimeUtils.toLocalTime(line);
    }

    private String readLine(ErrorMessage errorMessage) {
        String line = Console.readLine();
        InputValidator.validateNotNullOrBlank(line, errorMessage);
        return line;
    }

    public int readModifyDay() {
        String line = Console.readLine();
        return StringParser.parseToInteger(line, ErrorMessage.INVALID_FORMAT);
    }
}
