package attendance.domain.crew;

import attendance.exception.CustomIllegalArgumentException;
import attendance.exception.ErrorMessage;
import java.util.Arrays;
import java.util.List;

public enum CrewType {

    성실(0), 경고(2), 면담(3), 제적(6);

    private final int count;

    CrewType(final int count) {
        this.count = count;
    }

    public static List<CrewType> sort() {
        return Arrays.stream(CrewType.values())
                .sorted((s1, s2) -> Integer.compare(s2.count, s1.count))
                .toList();
    }

    public static CrewType from(final int lateCount, final int absentCount) {
        return sort().stream()
                .filter(crewType -> crewType.count <= absentCount + (lateCount / 3))
                .findFirst()
                .orElseThrow(() -> new CustomIllegalArgumentException(ErrorMessage.INVALID_STATE));
    }
}
