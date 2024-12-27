package attendance.domain.campus;

import static attendance.exception.ErrorMessage.INVALID_CAMPUS_OPERATION_TIME;

import attendance.exception.CustomIllegalArgumentException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

public enum CampusEducationTime {

    교육시간_월(13, 18),
    교육시간_월제외(10, 18);

    private final int startHour;
    private final int endHour;

    CampusEducationTime(final int startHour, final int endHour) {
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public static int getStartHour(final LocalDateTime time) {
        checkDay(time);
        if (time.getDayOfWeek() == DayOfWeek.MONDAY) {
            return 교육시간_월.startHour;
        }
        return 교육시간_월제외.startHour;
    }

    private static void checkDay(final LocalDateTime time) {
        DayOfWeek dayOfWeek = time.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY || Holiday.isHoliday(
                time.getDayOfMonth())) {
            throw new CustomIllegalArgumentException(INVALID_CAMPUS_OPERATION_TIME);
        }
    }
}
