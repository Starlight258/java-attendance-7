package attendance.domain.campus;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum CampusEducationTime {
    // 월요일 : 13:00~18:00
    // 화~금요일 : 10:00~18:00
    교육시간_월(makeTime(13, 0), makeTime(18, 0)),
    교육시간_월제외(makeTime(10, 0), makeTime(18, 0));

    private final LocalTime startTime;
    private final LocalTime endTime;

    CampusEducationTime(final LocalTime startTime, final LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static CampusEducationTime of(final LocalDateTime time) {
        if (time.getDayOfWeek() == DayOfWeek.MONDAY) {
            return 교육시간_월;
        }
        return 교육시간_월제외;
    }

    public static LocalTime makeTime(int hour, int minute) {
        return LocalTime.of(hour, minute);
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}
