package attendance.domain.crew;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("크루 타입 테스트")
class CrewTypeTest {

    @ParameterizedTest(name = "결석 횟수 : {0}, 지각 횟수 : {1}, 출석 상태 : {2}")
    @CsvSource({
            "0, 2, 성실",
            "1, 2, 성실",
            "0, 6, 경고",
            "1, 3, 경고",
            "2, 2, 경고",
            "0, 9, 면담",
            "0, 16, 면담",
            "1, 6, 면담",
            "5, 1, 면담",
            "0, 18, 제적",
            "5, 3, 제적",
            "6, 0, 제적",
    })
    @DisplayName("지각 3회를 결석으로 간주하여 크루 타입을 조회한다")
    void 지각_3회를_결석으로_간주하여_크루_타입을_조회한다(int absentCount, int lateCount, String expected) {
        // Given

        // When & Then
        assertThat(CrewType.from(absentCount, lateCount).name()).isEqualTo(expected);
    }
}
