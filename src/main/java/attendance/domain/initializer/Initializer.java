package attendance.domain.initializer;

import attendance.domain.log.CrewLogs;
import attendance.exception.CustomIllegalArgumentException;
import attendance.exception.ErrorMessage;
import attendance.util.AttendanceFileReader;
import attendance.util.FileContentParser;
import attendance.util.StringParser;
import attendance.util.TimeUtils;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class Initializer {

    private static final String DELIMITER = ",";
    private static final int TOKEN_SIZE = 2;

    public CrewLogs makeCrewLogs() {
        CrewLogs crewLogs = new CrewLogs(new HashMap<>());
        List<String> attendances = readAttendances();
        for (String attendance : attendances) {
            List<String> tokens = parseByDelimiter(attendance);
            String name = tokens.getFirst();
            LocalDateTime attendanceTime = TimeUtils.toLocalDateTime(tokens.getLast());
            crewLogs.initialize(name, attendanceTime);
        }
        return crewLogs;
    }

    private List<String> parseByDelimiter(final String token) {
        List<String> values = StringParser.parseByDelimiter(token, DELIMITER);
        if (values.size() != TOKEN_SIZE) {
            throw new CustomIllegalArgumentException(ErrorMessage.INVALID_FILE_FORMAT);
        }
        return values;
    }

    private List<String> readAttendances() {
        List<String> inputs = AttendanceFileReader.readAttendances();
        return FileContentParser.removeHeaders(inputs);
    }
}
