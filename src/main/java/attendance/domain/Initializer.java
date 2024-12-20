package attendance.domain;

import attendance.exception.CustomIllegalArgumentException;
import attendance.exception.ErrorMessage;
import attendance.util.AttendanceFileReader;
import attendance.util.FileContentParser;
import attendance.util.StringParser;
import attendance.util.TimeParser;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class Initializer {

    private static final String DELIMITER = ",";
    private static final int TOKEN_SIZE = 2;

    public CrewLogs makeCrewLogs() {
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
