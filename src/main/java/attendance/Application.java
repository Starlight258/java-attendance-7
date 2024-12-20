package attendance;

import attendance.controller.AttendanceController;
import attendance.domain.CampusTimeChecker;
import attendance.domain.CrewLogs;
import attendance.domain.Initializer;
import attendance.exception.ExceptionHandler;
import attendance.service.AttendanceService;
import attendance.view.InputView;
import attendance.view.OutputView;
import camp.nextstep.edu.missionutils.Console;

public class Application {

    public static void main(String[] args) {
        AttendanceController attendanceController = makeController();
        try {
            attendanceController.process();
        } finally {
            Console.close();
        }
    }

    private static AttendanceController makeController() {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        ExceptionHandler exceptionHandler = new ExceptionHandler(outputView);
        AttendanceService attendanceService = makeService();
        return new AttendanceController(inputView, outputView, exceptionHandler, attendanceService);
    }

    private static AttendanceService makeService() {
        CampusTimeChecker campusTimeChecker = new CampusTimeChecker();
        Initializer initializer = new Initializer();
        CrewLogs crewLogs = initializer.makeCrewLogs();
        return new AttendanceService(crewLogs, campusTimeChecker);
    }
}