package attendance.controller;

import attendance.domain.Command;
import attendance.domain.CrewLogs;
import attendance.dto.InformDto;
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
import java.time.LocalTime;
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
        while (true) {
            LocalDateTime now = DateTimes.now();
            outputView.showTitleWelcome(now);
            Command command = Command.from(inputView.readFunction());
            processAttendance(now, command, crewLogs);
            if (command == Command.QUIT) {
                return;
            }
        }
    }

    private void processAttendance(final LocalDateTime now, final Command command, final CrewLogs crewLogs) {
        if (command == Command.ATTENDANCE_CHECK) {
            checkAttendance(now, crewLogs);
        }
        if (command == Command.ATTENDANCE_MODIFY) {
            modifyAttendance();
        }
        if (command == Command.ATTENDANCE_CREW_LOG) {
            checkCrewLog();
        }
        if (command == Command.ATTENDANCE_DANGER) {
            checkDangerCrew();
        }
    }

    private void checkDangerCrew() {

    }

    private void checkCrewLog() {
    }

    private void modifyAttendance() {

    }

    // 오늘은 12월 13일 금요일입니다. 기능을 선택해 주세요.
    //1. 출석 확인
    //2. 출석 수정
    //3. 크루별 출석 기록 확인
    //4. 제적 위험자 확인
    //Q. 종료
    //1
    //
    //닉네임을 입력해 주세요.
    //이든
    //등교 시간을 입력해 주세요.
    //09:59
    //
    //12월 13일 금요일 09:59 (출석)
    private void checkAttendance(final LocalDateTime now, final CrewLogs crewLogs) {
        attendanceService.checkDate(now);
        outputView.showRequestCheckNickname();
        String nickname = inputView.readCheckNickname();
        crewLogs.checkNickname(nickname);
        outputView.showRequestCheckAttendanceTime();
        LocalTime attendanceTime = inputView.readCheckAttendanceTime();
        LocalDateTime todayTime = TimeParser.makeTodayTime(now, attendanceTime);
        crewLogs.addLog(nickname, todayTime);
        InformDto informDto = attendanceService.processAttendance(todayTime);
        outputView.showInformCheck(informDto);
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
            crewLogs.initialize(name, attendanceTime);
        }
        return crewLogs;
    }

}
