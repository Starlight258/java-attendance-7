package attendance.controller;

import attendance.domain.command.Command;
import attendance.dto.CrewDto;
import attendance.dto.AttendanceDto;
import attendance.dto.ModifyDto;
import attendance.dto.MonthTotalAttendanceDto;
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
        if (command == Command.ATTENDANCE_CREW_LOG) {
            checkCrewLog(now);
        }
        if (command == Command.ATTENDANCE_DANGER) {
            checkDangerCrew(now);
        }
    }

    private void checkDangerCrew(final LocalDateTime now) {
        List<CrewDto> crewDtos = attendanceService.checkDangerCrew(now);
        outputView.showTitleDangerSubject(crewDtos);
    }

    private void checkCrewLog(final LocalDateTime now) {
        outputView.showRequestLogNickname();
        String nickname = inputView.readNickname();
        attendanceService.checkNickname(nickname);
        MonthTotalAttendanceDto monthTotalAttendanceDto = attendanceService.checkCrewLog(nickname, now.getDayOfMonth());
        outputView.showTotalLog(nickname, monthTotalAttendanceDto);
    }

    private void modifyAttendance(final LocalDateTime now) {
        outputView.showRequestModifyNickname();
        String nickname = inputView.readNickname();
        attendanceService.checkNickname(nickname);
        outputView.showRequestModifyDay();
        int day = inputView.readModifyDay();
        LocalDateTime today = TimeUtils.makeDay(now, day);
        attendanceService.checkModifyDate(now, today);
        outputView.showRequestModifyTime();
        LocalTime time = inputView.readTime();
        LocalDateTime todayTime = TimeUtils.makeTime(today, time);
        ModifyDto modifyDto = attendanceService.modifyTime(nickname, todayTime);
        outputView.showInformModify(modifyDto);
    }

    private void checkAttendance(final LocalDateTime now) {
        attendanceService.checkAttendanceDate(now);
        outputView.showRequestCheckNickname();
        String nickname = inputView.readNickname();
        attendanceService.checkNickname(nickname);
        outputView.showRequestCheckAttendanceTime();
        LocalTime attendanceTime = inputView.readTime();
        LocalDateTime todayTime = TimeUtils.makeTime(now, attendanceTime);
        AttendanceDto attendanceDto = attendanceService.processAttendance(nickname, todayTime);
        outputView.showInformCheck(attendanceDto);
    }

}
