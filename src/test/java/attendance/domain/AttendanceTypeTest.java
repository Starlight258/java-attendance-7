package attendance.domain;

import attendance.domain.crew.AttendanceType;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AttendanceTypeTest {

    @ParameterizedTest
    @MethodSource
    void getAttendanceType(final int hour, final int minute, final AttendanceType expected) {
        LocalDateTime today = LocalDateTime.of(2024, 12, 20, hour, minute);
        AttendanceType attendanceType = AttendanceType.getAttendanceType(today);
        Assertions.assertThat(attendanceType).isEqualTo(expected);
    }

    private static Stream<Arguments> getAttendanceType() {
        return Stream.of(
                Arguments.of(9, 0, AttendanceType.출석),
                Arguments.of(10, 0, AttendanceType.출석),
                Arguments.of(10, 5, AttendanceType.출석),
                Arguments.of(10, 6, AttendanceType.지각),
                Arguments.of(10, 30, AttendanceType.지각),
                Arguments.of(10, 31, AttendanceType.결석)
        );
    }
}
