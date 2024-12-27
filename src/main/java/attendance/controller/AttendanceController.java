package attendance.controller;

import attendance.domain.command.Command;
import attendance.dto.AttendanceResponse;
import attendance.dto.CrewResponse;
import attendance.dto.ModifyResponse;
import attendance.dto.TotalAttendanceResponse;
import attendance.service.AttendanceService;
import attendance.util.TimeUtils;
import attendance.view.InputView;
import attendance.view.OutputView;
import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceService attendanceService;

    public AttendanceController(final InputView inputView, final OutputView outputView,
                                final AttendanceService attendanceService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.attendanceService = attendanceService;
    }

    public void process() {
        while (true) {
            outputView.showTitleWelcome(DateTimes.now().toLocalDate());
            Command command = Command.from(inputView.readFunction());
            processAttendance(command);
            if (command == Command.QUIT) {
                return;
            }
            outputView.showBlank();
        }
    }

    private void processAttendance(final Command command) {
        if (command == Command.ATTENDANCE) {
            attend();
        }
        if (command == Command.ATTENDANCE_MODIFY) {
            modifyAttendance();
        }
        if (command == Command.ATTENDANCE_CREW_HISTORY) {
            checkCrewHistory();
        }
        if (command == Command.ATTENDANCE_DANGER) {
            checkDangerCrew();
        }
    }

    private void attend() {
        LocalDateTime now = DateTimes.now();
        attendanceService.checkAttendanceDate(now.toLocalDate());
        String nickname = readNickname();
        LocalDateTime todayTime = readTime(now);
        AttendanceResponse attendanceResponse = attendanceService.attend(nickname, todayTime);
        outputView.showInformAttend(attendanceResponse);
    }

    private void modifyAttendance() {
        String nickname = readModifyNickname();
        LocalDate today = readModifyDay();
        LocalDateTime todayTime = readModifyTime(today);
        ModifyResponse modifyResponse = attendanceService.modifyTime(nickname, todayTime);
        outputView.showInformModify(modifyResponse);
    }

    private void checkCrewHistory() {
        String nickname = readHistoryNickname();
        TotalAttendanceResponse totalAttendanceResponse = attendanceService.checkCrewHistory(nickname);
        outputView.showTotalHistories(nickname, totalAttendanceResponse);
    }

    private void checkDangerCrew() {
        List<CrewResponse> crewResponses = attendanceService.checkDangerCrew();
        outputView.showTitleDangerSubject(crewResponses);
    }

    private String readNickname() {
        outputView.showRequestCheckNickname();
        String nickname = inputView.readNickname();
        attendanceService.checkNickname(nickname);
        return nickname;
    }

    private LocalDateTime readTime(final LocalDateTime now) {
        outputView.showRequestCheckAttendanceTime();
        LocalTime attendanceTime = inputView.readTime();
        return LocalDateTime.of(now.toLocalDate(), attendanceTime);
    }

    private String readModifyNickname() {
        outputView.showRequestModifyNickname();
        String nickname = inputView.readNickname();
        attendanceService.checkNickname(nickname);
        return nickname;
    }

    private LocalDate readModifyDay() {
        outputView.showRequestModifyDay();
        int modifyDay = inputView.readModifyDay();
        LocalDateTime now = DateTimes.now();
        LocalDate modifyDate = TimeUtils.alterDay(now.toLocalDate(), modifyDay);
        attendanceService.checkModifyDate(now.toLocalDate(), modifyDate);
        return modifyDate;
    }

    private LocalDateTime readModifyTime(final LocalDate today) {
        outputView.showRequestModifyTime();
        LocalTime time = inputView.readTime();
        return LocalDateTime.of(today, time);
    }

    private String readHistoryNickname() {
        outputView.showRequestHistoryNickname();
        String nickname = inputView.readNickname();
        attendanceService.checkNickname(nickname);
        return nickname;
    }
}
