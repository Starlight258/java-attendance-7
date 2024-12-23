package attendance.domain.campus;

import attendance.domain.date.Holiday;
import attendance.exception.CustomIllegalArgumentException;
import attendance.exception.ErrorMessage;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum CampusOperationTime {

    // 운영시간
    // 매일 08:00~23:00
    //- 주말 및 공휴일에는 출석을 받지 않는다.
    운영시간(makeTime(8, 0), makeTime(23, 0));

    private final LocalTime startTime;
    private final LocalTime endTime;

    CampusOperationTime(final LocalTime startTime, final LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static void checkOperationTime(final LocalTime time) {
        if (time.equals(운영시간.startTime) || time.equals(운영시간.endTime)
                || (time.isAfter(운영시간.startTime) && time.isBefore(운영시간.endTime))) {
            return;
        }
        throw new CustomIllegalArgumentException(ErrorMessage.INVALID_CAMPUS_OPERATION_TIME);
    }

    public static boolean isNotOperationDay(final LocalDateTime localDate) {
        return Holiday.isHoliday(localDate.getDayOfMonth()) || isWeekend(localDate);
    }

    private static boolean isWeekend(final LocalDateTime localDate) {
        return localDate.getDayOfWeek() == DayOfWeek.SATURDAY || localDate.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    private static LocalTime makeTime(int hour, int minute) {
        return LocalTime.of(hour, minute);
    }
}
