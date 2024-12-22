package attendance.domain.date;

import static attendance.exception.ErrorMessage.INVALID_ATTENDANCE_DAY;

import attendance.exception.CustomIllegalArgumentException;
import attendance.util.TimeFormatter;
import attendance.util.TimeUtils;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

public class CampusTimeChecker {

    private static final int FIRST_DAY = 1;

    public void checkDate(final LocalDateTime localDate) {
        if (isNotOperationDay(localDate)) {
            throw new CustomIllegalArgumentException(
                    INVALID_ATTENDANCE_DAY.getMessage(TimeFormatter.makeDateMessage(localDate)));
        }
    }

    public List<Integer> getWeekday(LocalDateTime now) {
        return IntStream.range(FIRST_DAY, now.getDayOfMonth())
                .filter(day -> !isNotOperationDay(TimeUtils.makeDay(now, day)))
                .boxed()
                .toList();
    }

    private boolean isNotOperationDay(final LocalDateTime localDate) {
        return Holiday.isHoliday(localDate.getDayOfMonth()) || isWeekend(localDate);
    }

    private boolean isWeekend(final LocalDateTime localDate) {
        return localDate.getDayOfWeek() == DayOfWeek.SATURDAY || localDate.getDayOfWeek() == DayOfWeek.SUNDAY;
    }
}
