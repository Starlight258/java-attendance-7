package attendance.domain.campus;

import static attendance.support.CustomAssert.assertIllegalArgument;
import static org.assertj.core.api.Assertions.assertThat;

import attendance.exception.ErrorMessage;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("캠퍼스 교육시간 테스트")
class CampusEducationTimeTest {

    @ParameterizedTest(name = "날짜/시간 : {0}, 운영 시작 시간 : {1}")
    @CsvSource({
            "2024-12-02T09:00, 13",
            "2024-12-03T09:00, 10"
    })
    void 요일에_따라_교육_시작_시간을_조회한다(final LocalDateTime now, final int expected) {
        // Given

        // When & Then
        assertThat(CampusEducationTime.getStartHour(now)).isEqualTo(expected);
    }

    @ParameterizedTest(name = "날짜/시간 : {0}")
    @CsvSource({
            "2024-12-01T09:00",
            "2024-12-07T09:00"
    })
    void 주말이거나_공휴일이면_예외가_발생한다(final LocalDateTime now) {
        // Given

        // When & Then
        assertIllegalArgument(() -> CampusEducationTime.getStartHour(now), ErrorMessage.INVALID_CAMPUS_OPERATION_TIME);
    }
}
