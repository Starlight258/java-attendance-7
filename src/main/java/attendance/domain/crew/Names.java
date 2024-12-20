package attendance.domain.crew;

import static attendance.exception.ErrorMessage.INVALID_CREW_NAMES;

import attendance.exception.CustomIllegalArgumentException;
import java.util.ArrayList;
import java.util.List;

public class Names {

    private final List<String> names;

    public Names(final List<String> names) {
        validate(names);
        this.names = new ArrayList<>(names);
    }

    public void add(final String name) {
        names.add(name);
    }

    private void validate(final List<String> names) {
        if (names.size() != names.stream().distinct().count()) {
            throw new CustomIllegalArgumentException(INVALID_CREW_NAMES);
        }
    }
}
