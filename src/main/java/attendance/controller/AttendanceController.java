package attendance.controller;

import attendance.domain.Command;
import attendance.domain.CrewLogs;
import attendance.exception.CustomIllegalArgumentException;
import attendance.exception.ErrorMessage;
import attendance.exception.ExceptionHandler;
import attendance.service.AttendanceService;
import attendance.util.AttendanceFileReader;
import attendance.util.FileContentParser;
import attendance.util.StringParser;
import attendance.util.TimeParser;
import attendance.view.InputView;
import attendance.view.OutputView;
import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class AttendanceController {

    private static final String DELIMITER = ",";
    private static final int TOKEN_SIZE = 2;
    private final InputView inputView;
    private final OutputView outputView;
    private final ExceptionHandler exceptionHandler;
    private final AttendanceService attendanceService;

    public AttendanceController(final InputView inputView, final OutputView outputView,
                                final ExceptionHandler exceptionHandler,
                                final AttendanceService attendanceService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.exceptionHandler = exceptionHandler;
        this.attendanceService = attendanceService;
    }

    public void process() {
        // 파일 읽기
        CrewLogs crewLogs = makeCrewLogs();
        // 기능 입력
        LocalDateTime now = DateTimes.now();
        outputView.showTitleWelcome(now);
        Command command = Command.from(inputView.readFunction());


    }

    private CrewLogs makeCrewLogs() {
        CrewLogs crewLogs = new CrewLogs(new HashMap<>());
        List<String> inputs = AttendanceFileReader.readAttendances();
        List<String> tokens = FileContentParser.removeHeaders(inputs);
        for (String token : tokens) {
            List<String> values = StringParser.parseByDelimiter(token, DELIMITER);
            if (values.size() != TOKEN_SIZE) {
                throw new CustomIllegalArgumentException(ErrorMessage.INVALID_FILE_FORMAT);
            }
            String name = values.getFirst();
            LocalDateTime attendanceTime = TimeParser.toLocalDateTime(values.getLast());
            crewLogs.put(name, attendanceTime);
        }
        return crewLogs;
    }

}
