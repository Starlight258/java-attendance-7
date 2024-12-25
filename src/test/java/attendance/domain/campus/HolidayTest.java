package attendance.domain.campus;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class HolidayTest {

    @ParameterizedTest(name = "날짜 : 12월 {0}일, 공휴일 여부 : {1}")
    @CsvSource({
            "1, false",
            "25, true"
    })
    void 공휴일이면_true를_반환한다(final int day, final boolean expected) {
        // Given

        // When & Then
        assertThat(Holiday.isHoliday(day)).isEqualTo(expected);
    }
}
