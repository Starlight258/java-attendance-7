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

    @Test
    void 출석_횟수를_조회한다() {
        // Given

        // When & Then
        assertThat(attendanceResult.getAttendanceCount()).isEqualTo(12);
    }

    @Test
    void 지각_횟수를_조회한다() {
        // Given

        // When & Then
        assertThat(attendanceResult.getLateCount()).isEqualTo(4);
    }

    @Test
    void 결석_횟수를_조회한다() {
        // Given

        // When & Then
        assertThat(attendanceResult.getAbsentCount()).isEqualTo(3);
    }

    @Test
    void 지각_3회를_결석으로_간주하여_결석_횟수를_조회한다() {
        // Given

        // When & Then
        assertThat(attendanceResult.calculateAbsentCountWithLate()).isEqualTo(4);
    }

    @Test
    void 지각_3회를_결석으로_간주한_횟수를_제외한_지각_횟수를_조회한다() {
        // Given

        // When & Then
        assertThat(attendanceResult.calculateLateCountWithoutAbsent()).isEqualTo(1);
    }

    private AttendanceResult makeResult() {
        return new AttendanceResult(Map.of(AttendanceType.출석, 12, AttendanceType.지각, 4, AttendanceType.결석, 3));
    }
}
