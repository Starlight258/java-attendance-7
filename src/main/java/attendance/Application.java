package attendance;

import attendance.controller.AttendanceController;
import attendance.domain.CampusTimeChecker;
import attendance.exception.ExceptionHandler;
import attendance.service.AttendanceService;
import attendance.view.InputView;
import attendance.view.OutputView;
import camp.nextstep.edu.missionutils.Console;

public class Application {

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        ExceptionHandler exceptionHandler = new ExceptionHandler(outputView);
        CampusTimeChecker campusTimeChecker = new CampusTimeChecker();
        AttendanceService attendanceService = new AttendanceService(campusTimeChecker);
        AttendanceController attendanceController = new AttendanceController(inputView, outputView, exceptionHandler, attendanceService);
        try {
            attendanceController.process();
        } finally {
            Console.close();
        }
    }
}
