package attendance.domain.campus;

import attendance.domain.date.Holiday;
import attendance.exception.CustomIllegalArgumentException;
import attendance.exception.ErrorMessage;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Campus {

    private static final int START_HOUR = 8;
    private static final int END_HOUR = 23;

    public void checkOperationTime(final LocalTime time) {
        if (isNotOperationTime(time)) {
            return;
        }
        throw new CustomIllegalArgumentException(ErrorMessage.INVALID_CAMPUS_OPERATION_TIME);
    }

    public boolean isNotOperationDay(final LocalDateTime localDate) {
        return Holiday.isHoliday(localDate.getDayOfMonth()) || isWeekend(localDate);
    }

    private boolean isNotOperationTime(final LocalTime time) {
        LocalTime startTime = LocalTime.MIN.withHour(START_HOUR);
        LocalTime endTime = LocalTime.MIN.withHour(END_HOUR);
        return time.equals(startTime) || time.equals(endTime)
                || (time.isAfter(startTime) && time.isBefore(endTime));
    }

    private boolean isWeekend(final LocalDateTime localDate) {
        return localDate.getDayOfWeek() == DayOfWeek.SATURDAY || localDate.getDayOfWeek() == DayOfWeek.SUNDAY;
    }
}
