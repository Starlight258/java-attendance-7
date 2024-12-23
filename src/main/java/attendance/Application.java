package attendance;

import attendance.controller.AttendanceController;
import attendance.domain.initializer.Initializer;
import attendance.domain.crew.CrewLogs;
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
        AttendanceService attendanceService = makeService();
        return new AttendanceController(inputView, outputView, attendanceService);
    }

    private static AttendanceService makeService() {
        Initializer initializer = new Initializer();
        CrewLogs crewLogs = initializer.makeCrewLogs();
        return new AttendanceService(crewLogs);
    }
}
