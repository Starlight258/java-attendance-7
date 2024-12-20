package attendance.domain.date;

import java.util.Arrays;

public enum Holiday {
    CHRISTMAS(25);

    private final int day;

    Holiday(final int day) {
        this.day = day;
    }

    public static boolean isHoliday(final int input) {
        return Arrays.stream(values())
                .anyMatch(holiday -> holiday.day == input);
    }
}
