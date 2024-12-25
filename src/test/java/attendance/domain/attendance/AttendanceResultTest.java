package attendance.domain.attendance;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("출석 결과 테스트")
class AttendanceResultTest {

    private AttendanceResult attendanceResult;

    @BeforeEach
    void setUp() {
        attendanceResult = makeResult();
    }

    @DisplayName("출석 횟수를 조회한다.")
    @Test
    void getAttendanceCount() {
        // Given

        // When & Then
        assertThat(attendanceResult.getAttendanceCount()).isEqualTo(12);
    }

    @DisplayName("지각 횟수를 조회한다.")
    @Test
    void getLateCount() {
        // Given

        // When & Then
        assertThat(attendanceResult.getLateCount()).isEqualTo(4);
    }

    @DisplayName("결석 횟수를 조회한다.")
    @Test
    void getAbsentCount() {
        // Given

        // When & Then
        assertThat(attendanceResult.getAbsentCount()).isEqualTo(3);
    }

    @DisplayName("지각 3회를 결석으로 간주하여 결석 횟수를 조회한다.")
    @Test
    void calculateAbsentCountWithLate() {
        // Given

        // When & Then
        assertThat(attendanceResult.calculateAbsentCountWithLate()).isEqualTo(4);
    }

    @DisplayName("지각 3회를 결석으로 간주한 횟수를 제외한 지각 횟수를 조회한다.")
    @Test
    void calculateLateCountWithoutAbsent() {
        // Given

        // When & Then
        assertThat(attendanceResult.calculateLateCountWithoutAbsent()).isEqualTo(1);
    }

    private AttendanceResult makeResult() {
        return new AttendanceResult(Map.of(AttendanceType.출석, 12, AttendanceType.지각, 4, AttendanceType.결석, 3));
    }
}
