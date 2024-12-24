package attendance.domain.campus;

import attendance.domain.date.Holiday;
import attendance.exception.CustomIllegalArgumentException;
import attendance.exception.ErrorMessage;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Campus {

    private static final LocalTime START_TIME = LocalTime.MIN.withHour(8);
    private static final LocalTime END_TIME = LocalTime.MIN.withHour(23);

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
        return time.equals(START_TIME) || time.equals(END_TIME)
                || (time.isAfter(START_TIME) && time.isBefore(END_TIME));
    }

    private boolean isWeekend(final LocalDateTime localDate) {
        return localDate.getDayOfWeek() == DayOfWeek.SATURDAY || localDate.getDayOfWeek() == DayOfWeek.SUNDAY;
    }
}
