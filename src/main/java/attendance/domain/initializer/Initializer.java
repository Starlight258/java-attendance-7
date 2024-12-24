package attendance.domain.initializer;

import attendance.domain.campus.Campus;
import attendance.domain.crew.CrewHistories;
import attendance.exception.CustomIllegalArgumentException;
import attendance.exception.ErrorMessage;
import attendance.util.AttendanceFileReader;
import attendance.util.FileContentParser;
import attendance.util.StringParser;
import attendance.util.TimeUtils;
import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class Initializer {

    private static final String DELIMITER = ",";
    private static final int TOKEN_SIZE = 2;

    public CrewHistories makeCrewHistories() {
        CrewHistories crewHistories = new CrewHistories(new HashMap<>(), new Campus());
        List<String> attendances = readAttendances();
        LocalDateTime now = DateTimes.now();
        for (String attendance : attendances) {
            addAttendance(attendance, crewHistories, now);
        }
        return crewHistories;
    }

    private List<String> readAttendances() {
        List<String> inputs = AttendanceFileReader.readAttendances();
        return FileContentParser.removeHeaders(inputs);
    }

    private void addAttendance(final String attendance, final CrewHistories crewHistories, final LocalDateTime now) {
        List<String> tokens = parseByDelimiter(attendance);
        String name = tokens.getFirst();
        LocalDateTime attendanceTime = TimeUtils.toLocalDateTime(tokens.getLast());
        crewHistories.initialize(now, name, attendanceTime);
    }

    private List<String> parseByDelimiter(final String token) {
        List<String> values = StringParser.parseByDelimiter(token, DELIMITER);
        if (values.size() != TOKEN_SIZE) {
            throw new CustomIllegalArgumentException(ErrorMessage.INVALID_FILE_FORMAT);
        }
        return values;
    }
}
