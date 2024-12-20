package attendance.view;

import attendance.exception.ErrorMessage;
import attendance.util.InputValidator;
import camp.nextstep.edu.missionutils.Console;

public class InputView {

    private String readLine(ErrorMessage errorMessage) {
            String line = Console.readLine();
            InputValidator.validateNotNullOrBlank(line, errorMessage);
            return line;
        }

}
