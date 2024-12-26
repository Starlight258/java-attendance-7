package attendance.util;

import static attendance.support.CustomAssert.assertIllegalArgument;
import static org.assertj.core.api.Assertions.assertThat;

import attendance.exception.ErrorMessage;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("문자열 파싱 테스트")
class StringParserTest {

    @Nested
    class 문자열을_정수로_변환하는_테스트 {

        @Test
        void 정상적인_숫자_문자열을_정수로_변환한다() {
            assertThat(StringParser.parseToInteger("123", ErrorMessage.INVALID_FORMAT))
                    .isEqualTo(123);
        }

        @Test
        void 앞뒤_공백이_있는_숫자_문자열을_정수로_변환한다() {
            assertThat(StringParser.parseToInteger(" 123 ", ErrorMessage.INVALID_FORMAT)).isEqualTo(123);
        }

        @ParameterizedTest
        @ValueSource(strings = {"abc", "12.34", "123a", ""})
        void 숫자가_아닌_문자열은_예외가_발생한다(String input) {
            assertIllegalArgument(() -> StringParser.parseToInteger(input, ErrorMessage.INVALID_FORMAT),
                    ErrorMessage.INVALID_FORMAT);
        }
    }

    @Nested
    class 문자열을_구분자로_분리하는_테스트 {

        @Test
        void 쉼표로_구분된_문자열을_분리한다() {
            String input = "apple,banana,orange";
            List<String> result = StringParser.parseByDelimiter(input, ",");
            assertThat(result).containsExactly("apple", "banana", "orange");
        }

        @Test
        void 공백이_포함된_문자열을_분리하고_trim_처리한다() {
            String input = "apple , banana , orange";
            List<String> result = StringParser.parseByDelimiter(input, ",");
            assertThat(result).containsExactly("apple", "banana", "orange");
        }

        @Test
        void 빈_값을_포함한_문자열을_분리한다() {
            String input = "apple,,orange";
            List<String> result = StringParser.parseByDelimiter(input, ",");
            assertThat(result).containsExactly("apple", "", "orange");
        }

        @Test
        void 구분자가_없는_문자열은_단일_요소_리스트를_반환한다() {
            String input = "apple";
            List<String> result = StringParser.parseByDelimiter(input, ",");
            assertThat(result).containsExactly("apple");
        }
    }
}
