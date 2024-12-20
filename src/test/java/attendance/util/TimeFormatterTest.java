package attendance.util;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TimeFormatterTest {

    @Test
    @DisplayName("")
    void test() {
        // Given
        // localDateTime: 2021년 1월 1일 0시 0분 0초
        // 12월 13일 금요일 09:59 (출석)
        LocalDateTime localDateTime
                = LocalDateTime.of(2021, 1, 1, 2, 10, 0);

        String localDateTimeFormat1
                = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));

        String localDateTimeFormat2
                = localDateTime.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS")
        );

        String localDateTimeFormat3
                = localDateTime.format(
                DateTimeFormatter.ofPattern("HH:mm")
        );
        String localDateTimeFormat4
                = localDateTime.format(
                DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm")
        );
        //    System.out.println(dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN));  // 토요일        System.out.println(dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.KOREAN));  // 토
        //출처: https://hianna.tistory.com/610 [어제 오늘 내일:티스토리]

        System.out.println("localDateTime = " + localDateTime);
        System.out.println("localDateTimeFormat1 = " + localDateTimeFormat1);
        System.out.println("localDateTimeFormat2 = " + localDateTimeFormat2);
        System.out.println("localDateTimeFormat3 = " + localDateTimeFormat3);
        System.out.println("localDateTimeFormat4 = " + localDateTimeFormat4);

        // When

        // Then
    }

    @Test
    @DisplayName("파싱")
    void 파싱() {
        // Given
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime endOfYear = LocalDateTime.parse("2019-08-19 12:31", pattern);

        // When
        System.out.println(endOfYear);

        // Then
    }

}
