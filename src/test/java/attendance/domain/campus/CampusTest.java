package attendance.domain.campus;

import static attendance.exception.ErrorMessage.INVALID_ATTENDANCE_CAMPUS_OPERATION_TIME;
import static attendance.support.CustomAssert.assertIllegalArgument;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("캠퍼스 테스트")
class CampusTest {

    private Campus campus;

    @BeforeEach
    void setUp() {
        campus = new Campus();
    }

    @ParameterizedTest(name = "시간 : {0}")
    @CsvSource({
            "08:00",
            "08:30",
            "22:30",
            "23:00",
    })
    void 운영시간내인지_확인한다(final LocalTime time) {
        // Given

        // When & Then
        assertThatCode(() -> {
            campus.checkOperationTime(time);
        }).doesNotThrowAnyException();
    }

    @ParameterizedTest(name = "시간 : {0}")
    @CsvSource({
            "07:30",
            "23:30"
    })
    void 운영시간이_아니면_예외가_발생한다(final LocalTime time) {
        // Given

        // When & Then
        assertIllegalArgument(() -> campus.checkOperationTime(time), INVALID_ATTENDANCE_CAMPUS_OPERATION_TIME);
    }

    @ParameterizedTest(name = "날짜 : {0}, 운영 여부 : {1}")
    @CsvSource({
            "2024-12-02, false",
            "2024-12-07, true",
            "2024-12-08, true",
            "2024-12-25, true",
    })
    void 주말_또는_공휴일은_운영하지_않는다(final LocalDate today, boolean expected) {
        // Given

        // When & Then
        assertThat(campus.isNotOperationDay(today)).isEqualTo(expected);
    }
}
