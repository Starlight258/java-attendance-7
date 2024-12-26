package attendance.util;

import static attendance.exception.ErrorMessage.INVALID_FILE_FORMAT;
import static attendance.exception.ErrorMessage.INVALID_FORMAT;
import static attendance.support.CustomAssert.assertIllegalArgument;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("시간 유틸리티 테스트")
class TimeUtilsTest {

    @Nested
    @DisplayName("LocalDateTime 파싱 테스트")
    class LocalDateTime_파싱_테스트 {

        @Test
        @DisplayName("LocalDateTime으로 파싱한다")
        void LocalDateTime_파싱_테스트() {
            // Given

            // When
            LocalDateTime localDateTime = TimeUtils.toLocalDateTime("2024-12-13 10:00");

            // Then
            assertThat(localDateTime).isEqualTo(LocalDateTime.of(2024, 12, 13, 10, 0));
        }

        @Test
        void 형식에_맞지_않을_경우_예외가_발생한다() {
            // Given

            // When & Then
            assertIllegalArgument(() -> TimeUtils.toLocalDateTime("20241213 10:00"), INVALID_FILE_FORMAT);
        }
    }

    @Nested
    @DisplayName("LocalTime 파싱 테스트")
    class LocalTime_파싱_테스트 {

        @Test
        @DisplayName("LocalTime으로 파싱한다")
        void LocalTime_파싱_테스트() {
            // Given

            // When
            LocalTime time = TimeUtils.toLocalTime("10:00");

            // Then
            assertThat(time).isEqualTo(LocalTime.of(10, 0));
        }

        @Test
        void 형식에_맞지_않을_경우_예외가_발생한다() {
            // Given

            // When & Then
            assertIllegalArgument(() -> TimeUtils.toLocalTime("10-00"), INVALID_FORMAT);
        }
    }

    @Nested
    @DisplayName("day 대체 테스트")
    class day_대체_테스트 {

        @Test
        @DisplayName("현재 시간에서 주어진 day로 대체한다")
        void 현재_시간에서_주어진_day로_대체한다() {
            // Given
            LocalDate now = LocalDate.of(2024, 12, 13);

            // When
            LocalDate localDate = TimeUtils.alterDay(now, 14);

            // Then
            assertThat(localDate).isEqualTo(LocalDate.of(2024, 12, 14));
        }
    }
}
