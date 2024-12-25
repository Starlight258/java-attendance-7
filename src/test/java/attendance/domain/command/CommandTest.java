package attendance.domain.command;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("커맨드 테스트")
class CommandTest {

    @ParameterizedTest(name = "입력값 : {0}, 예상 커맨드 : {1}")
    @MethodSource
    void 커맨드를_생성한다(final String command, final Command expected) {
        // Given

        // When & Then
        assertThat(Command.from(command)).isEqualTo(expected);
    }

    private static Stream<Arguments> 커맨드를_생성한다() {
        return Stream.of(
                Arguments.of("1", Command.ATTENDANCE),
                Arguments.of("2", Command.ATTENDANCE_MODIFY),
                Arguments.of("3", Command.ATTENDANCE_CREW_HISTORY),
                Arguments.of("4", Command.ATTENDANCE_DANGER),
                Arguments.of("Q", Command.QUIT)
        );
    }
}
