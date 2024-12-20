package attendance.domain;

import static attendance.domain.CampusOperationTime.운영시간;
import static attendance.exception.ErrorMessage.INVALID_ATTENDANCE_DAY;

import attendance.exception.CustomIllegalArgumentException;
import attendance.exception.ErrorMessage;
import attendance.util.TimeFormatter;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CampusTimeChecker {

    public void checkDate(final LocalDateTime localDate) {
        if (Holiday.isHoliday(localDate.getDayOfMonth()) || isWeekend(localDate)) {
            throw new CustomIllegalArgumentException(
                    INVALID_ATTENDANCE_DAY.getMessage(TimeFormatter.makeDateMessage(localDate)));
        }
    }

    private boolean isWeekend(final LocalDateTime localDate) {
        return localDate.getDayOfWeek() == DayOfWeek.SATURDAY || localDate.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

}
