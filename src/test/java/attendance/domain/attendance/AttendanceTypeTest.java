package attendance.domain.attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceTypeTest {

    @ParameterizedTest(name = "시간 : {0}, 상태 : {1}")
    @CsvSource({
            "09:00, 출석",
            "10:00, 출석",
            "10:05, 출석",
            "10:06, 지각",
            "10:30, 지각",
            "10:31, 결석",
            "00:00, 결석",
    })
    void 결석_상태를_조회한다(final LocalTime time, final AttendanceType expected) {
        // Given
        LocalDate today = LocalDate.of(2024, 12, 20);
        LocalDateTime now = LocalDateTime.of(today, time);

        // When
        AttendanceType attendanceType = AttendanceType.getAttendanceType(now);

        // Then
        Assertions.assertThat(attendanceType).isEqualTo(expected);
    }
}
