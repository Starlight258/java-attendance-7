package attendance.view;

import attendance.exception.ErrorMessage;
import attendance.util.InputValidator;
import attendance.util.StringParser;
import attendance.util.TimeUtils;
import camp.nextstep.edu.missionutils.Console;
import java.time.LocalTime;

public class InputView {

    public String readFunction() {
        return readLine();
    }

    public String readNickname() {
        return readLine();
    }

    public LocalTime readTime() {
        String line = readLine();
        return TimeUtils.toLocalTime(line);
    }

    public int readModifyDay() {
        String line = Console.readLine();
        return StringParser.parseToInteger(line, ErrorMessage.INVALID_FORMAT);
    }

    private String readLine() {
        String line = Console.readLine();
        InputValidator.validateNotNullOrBlank(line, ErrorMessage.INVALID_FORMAT);
        return line;
    }
}
