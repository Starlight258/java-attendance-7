package attendance.controller;

import attendance.domain.Command;
import attendance.dto.InformDto;
import attendance.exception.ExceptionHandler;
import attendance.service.AttendanceService;
import attendance.util.TimeParser;
import attendance.view.InputView;
import attendance.view.OutputView;
import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AttendanceController {

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
        while (true) {
            LocalDateTime now = DateTimes.now();
            outputView.showTitleWelcome(now);
            Command command = Command.from(inputView.readFunction());
            processAttendance(now, command);
            if (command == Command.QUIT) {
                return;
            }
        }
    }

    private void processAttendance(final LocalDateTime now, final Command command) {
        if (command == Command.ATTENDANCE_CHECK) {
            checkAttendance(now);
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
    private void checkAttendance(final LocalDateTime now) {
        attendanceService.checkDate(now);
        outputView.showRequestCheckNickname();
        String nickname = inputView.readCheckNickname();
        attendanceService.checkNickname(nickname);
        outputView.showRequestCheckAttendanceTime();
        LocalTime attendanceTime = inputView.readCheckAttendanceTime();
        LocalDateTime todayTime = TimeParser.makeTodayTime(now, attendanceTime);
        InformDto informDto = attendanceService.processAttendance(nickname, todayTime);
        outputView.showInformCheck(informDto);
    }


}
