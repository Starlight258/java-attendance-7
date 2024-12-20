package attendance.view;

import attendance.exception.ErrorMessage;
import attendance.util.InputValidator;
import camp.nextstep.edu.missionutils.Console;

public class InputView {

    public String readFunction() {
        return readLine(ErrorMessage.INVALID_FORMAT);
    }

    public String readCheckNickname() {
        return readLine(ErrorMessage.INVALID_FORMAT);
    }

    public String readCheckAttendanceTime() {
        return readLine(ErrorMessage.INVALID_FORMAT);
    }

    private String readLine(ErrorMessage errorMessage) {
            String line = Console.readLine();
            InputValidator.validateNotNullOrBlank(line, errorMessage);
            return line;
        }

}
