package attendance.domain;

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
