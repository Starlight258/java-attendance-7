package attendance.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("시간 포맷팅 테스트")
class TimeFormatterTest {

    @Test
    void 시간_포맷팅_메세지를_생성한다() {
        // Given
        LocalTime time = LocalTime.of(9, 0);

        // When
        String message = TimeFormatter.makeTimeMessage(time);

        // Then
        assertThat(message).isEqualTo("09:00");
    }

    @Test
    void 날짜_포맷팅_메세지를_생성한다() {
        // Given
        LocalDate date = LocalDate.of(2024, 12, 13);

        // When
        String message = TimeFormatter.makeDateMessage(date);

        // Then
        assertThat(message).isEqualTo("12월 13일 금요일");
    }

    @Test
    void 날짜_시간_포맷팅_메세지를_생성한다() {
        // Given
        LocalDate date = LocalDate.of(2024, 12, 13);
        LocalTime time = LocalTime.of(9, 0);
        LocalDateTime today = LocalDateTime.of(date, time);

        // When
        String message = TimeFormatter.makeDateTimeMessage(today);

        // Then
        assertThat(message).isEqualTo("12월 13일 금요일 09:00");
    }
}
