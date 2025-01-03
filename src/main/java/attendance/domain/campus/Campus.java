package attendance.domain.campus;

import attendance.exception.CustomIllegalArgumentException;
import attendance.exception.ErrorMessage;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class Campus {

    private static final LocalTime START_TIME = LocalTime.MIN.withHour(8);
    private static final LocalTime END_TIME = LocalTime.MIN.withHour(23);

    public void checkOperationTime(final LocalTime time) {
        if (isOperationTime(time)) {
            return;
        }
        throw new CustomIllegalArgumentException(ErrorMessage.INVALID_ATTENDANCE_CAMPUS_OPERATION_TIME);
    }

    public boolean isNotOperationDay(final LocalDate localDate) {
        return Holiday.isHoliday(localDate.getDayOfMonth()) || isWeekend(localDate);
    }

    private boolean isOperationTime(final LocalTime time) {
        return time.equals(START_TIME) || time.equals(END_TIME)
                || (time.isAfter(START_TIME) && time.isBefore(END_TIME));
    }

    private boolean isWeekend(final LocalDate localDate) {
        return localDate.getDayOfWeek() == DayOfWeek.SATURDAY || localDate.getDayOfWeek() == DayOfWeek.SUNDAY;
    }
}
