package attendance.domain.attendance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("출석 상태 테스트")
class AttendanceStateTest {

    @Test
    void 해당_날짜로의_기본_상태를_만든다() {
        // Given
        LocalDate now = LocalDate.of(2024, 12, 3);
        // When
        AttendanceState attendanceState = AttendanceState.makeDefault(now, 10);

        // Then
        assertAll(
                () -> assertThat(attendanceState.attendanceTime().getDayOfMonth()).isEqualTo(10),
                () -> assertThat(attendanceState.attendanceType()).isEqualTo(AttendanceType.결석)
        );
    }

    @ParameterizedTest(name = "시간: {0}, 상태 : {1}")
    @CsvSource({
            "09:00, 출석",
            "10:05, 출석",
            "10:06, 지각",
            "10:30, 지각",
            "10:31, 결석",
    })
    void 출석_상태를_생성한다(LocalTime time, String expected) {
        // Given
        LocalDate today = LocalDate.of(2024, 12, 3);
        LocalDateTime now = LocalDateTime.of(today, time);

        // When
        AttendanceState attendanceState = AttendanceState.makeAttendance(now);

        // Then
        assertAll(
                () -> assertThat(attendanceState.attendanceTime()).isEqualTo(now),
                () -> assertThat(attendanceState.attendanceType().name()).isEqualTo(expected)
        );
    }

    @ParameterizedTest(name = "시간: {0}, 상태 : {1}")
    @CsvSource({
            "00:00, true",
            "10:05, false",
    })
    void 기본_상태인지_확인한다(LocalTime time, boolean expected) {
        // Given
        LocalDate today = LocalDate.of(2024, 12, 3);
        LocalDateTime now = LocalDateTime.of(today, time);
        AttendanceState state = AttendanceState.makeAttendance(now);

        // When & Then
        assertThat(state.isDefaultValue()).isEqualTo(expected);
    }
}
