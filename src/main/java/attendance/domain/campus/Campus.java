package attendance.domain.campus;

import attendance.domain.date.Holiday;
import attendance.exception.CustomIllegalArgumentException;
import attendance.exception.ErrorMessage;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Campus {

    // 운영시간
    // 매일 08:00~23:00
    // 주말 및 공휴일에는 출석을 받지 않는다.
    private static final int START_HOUR = 8;
    private static final int END_HOUR = 23;

    public void checkOperationTime(final LocalTime time) {
        if (isNotOperationTime(time)) {
            return;
        }
        throw new CustomIllegalArgumentException(ErrorMessage.INVALID_CAMPUS_OPERATION_TIME);
    }

    private boolean isNotOperationTime(final LocalTime time) {
        LocalTime startTime = LocalTime.of(START_HOUR, 0);
        LocalTime endTime = LocalTime.of(END_HOUR, 0);
        return time.equals(startTime) || time.equals(endTime)
                || (time.isAfter(startTime) && time.isBefore(endTime));
    }

    public boolean isNotOperationDay(final LocalDateTime localDate) {
        return Holiday.isHoliday(localDate.getDayOfMonth()) || isWeekend(localDate);
    }

    private boolean isWeekend(final LocalDateTime localDate) {
        return localDate.getDayOfWeek() == DayOfWeek.SATURDAY || localDate.getDayOfWeek() == DayOfWeek.SUNDAY;
    }
}
