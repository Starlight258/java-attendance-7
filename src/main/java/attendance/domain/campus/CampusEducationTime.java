package attendance.domain.campus;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum CampusEducationTime {
    // 월요일 : 13:00~18:00
    // 화~금요일 : 10:00~18:00
    교육시간_월(13, 18),
    교육시간_월제외(10, 18);

    private final int startHour;
    private final int endHour;

    CampusEducationTime(final int startHour, final int endHour) {
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public static CampusEducationTime of(final LocalDateTime time) {
        if (time.getDayOfWeek() == DayOfWeek.MONDAY) {
            return 교육시간_월;
        }
        return 교육시간_월제외;
    }

    public LocalTime getStartTime() {
        return LocalTime.of(startHour, 0);
    }
}
