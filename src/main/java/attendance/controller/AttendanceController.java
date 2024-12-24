package attendance.controller;

import attendance.domain.command.Command;
import attendance.dto.CrewDto;
import attendance.dto.AttendanceDto;
import attendance.dto.ModifyDto;
import attendance.dto.TotalAttendanceDto;
import attendance.service.AttendanceService;
import attendance.util.TimeUtils;
import attendance.view.InputView;
import attendance.view.OutputView;
import camp.nextstep.edu.missionutils.DateTimes;
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
            LocalDateTime now = DateTimes.now();
            outputView.showTitleWelcome(now);
            Command command = Command.from(inputView.readFunction());
            processAttendance(now, command);
            if (command == Command.QUIT) {
                return;
            }
            outputView.showBlank();
        }
    }

    private void processAttendance(final LocalDateTime now, final Command command) {
        if (command == Command.ATTENDANCE_CHECK) {
            checkAttendance(now);
        }
        if (command == Command.ATTENDANCE_MODIFY) {
            modifyAttendance(now);
        }
        if (command == Command.ATTENDANCE_CREW_HISTORY) {
            checkCrewHistory(now);
        }
        if (command == Command.ATTENDANCE_DANGER) {
            checkDangerCrew(now);
        }
    }

    private void checkAttendance(final LocalDateTime now) {
        attendanceService.checkAttendanceDate(now);
        String nickname = readNickname();
        LocalDateTime todayTime = readTime(now);
        AttendanceDto attendanceDto = attendanceService.processAttendance(nickname, todayTime);
        outputView.showInformCheck(attendanceDto);
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
        return TimeUtils.makeTime(now, attendanceTime);
    }

    private void modifyAttendance(final LocalDateTime now) {
        String nickname = readModifyNickname();
        LocalDateTime today = readModifyDay(now);
        LocalDateTime todayTime = readModifyTime(today);
        ModifyDto modifyDto = attendanceService.modifyTime(nickname, todayTime);
        outputView.showInformModify(modifyDto);
    }

    private String readModifyNickname() {
        outputView.showRequestModifyNickname();
        String nickname = inputView.readNickname();
        attendanceService.checkNickname(nickname);
        return nickname;
    }

    private LocalDateTime readModifyDay(final LocalDateTime now) {
        outputView.showRequestModifyDay();
        int day = inputView.readModifyDay();
        LocalDateTime today = TimeUtils.makeDay(now, day);
        attendanceService.checkModifyDate(now, today);
        return today;
    }

    private LocalDateTime readModifyTime(final LocalDateTime today) {
        outputView.showRequestModifyTime();
        LocalTime time = inputView.readTime();
        return TimeUtils.makeTime(today, time);
    }

    private void checkCrewHistory(final LocalDateTime now) {
        String nickname = readHistoryNickname();
        TotalAttendanceDto totalAttendanceDto = attendanceService.checkCrewHistory(nickname, now.getDayOfMonth());
        outputView.showTotalHistories(nickname, totalAttendanceDto);
    }

    private String readHistoryNickname() {
        outputView.showRequestHistoryNickname();
        String nickname = inputView.readNickname();
        attendanceService.checkNickname(nickname);
        return nickname;
    }

    private void checkDangerCrew(final LocalDateTime now) {
        List<CrewDto> crewDtos = attendanceService.checkDangerCrew(now);
        outputView.showTitleDangerSubject(crewDtos);
    }
}
