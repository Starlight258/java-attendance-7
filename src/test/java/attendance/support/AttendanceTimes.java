package attendance.support;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AttendanceTimes {

    private static final int mondayStartHour = 13;
    private static final int weekdayStartHour = 10;

    private final LocalDate startDate;

    public AttendanceTimes(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getMonday() {
        return startDate.atTime(mondayStartHour, 0);
    }

    public LocalDateTime getTuesday() {
        return startDate.plusDays(1).atTime(weekdayStartHour, 0);
    }

    public LocalDateTime getWednesday() {
        return startDate.plusDays(2).atTime(weekdayStartHour, 0);
    }

    public LocalDateTime getThursday() {
        return startDate.plusDays(3).atTime(weekdayStartHour, 0);
    }

    public LocalDateTime getFriday() {
        return startDate.plusDays(4).atTime(weekdayStartHour, 0);
    }

    public LocalDateTime getNextMonday() {
        return startDate.plusDays(7).atTime(mondayStartHour, 0);
    }

    public LocalDateTime getNextTuesday() {
        return startDate.plusDays(8).atTime(weekdayStartHour, 0);
    }

    public LocalDateTime getToday() {
        return startDate.plusDays(9).atTime(weekdayStartHour, 0);
    }
}
