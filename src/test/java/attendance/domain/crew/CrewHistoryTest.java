package attendance.domain.crew;

import static attendance.exception.ErrorMessage.INVALID_DAY_FUTURE;
import static attendance.exception.ErrorMessage.INVALID_DUPLICATE_ATTENDANCE;
import static attendance.support.CustomAssert.assertIllegalArgument;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import attendance.domain.attendance.AttendanceResult;
import attendance.domain.attendance.AttendanceState;
import attendance.domain.attendance.AttendanceType;
import attendance.domain.campus.Campus;
import attendance.support.AttendanceTimes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("크루 기록 테스트")
class CrewHistoryTest {

    @Nested
    class 기록_추가_테스트 {

        @Test
        void 기록을_추가한다() {
            // Given
            CrewHistory crewHistory = makeCrewHistory();
            LocalDateTime now = makeAttendTime(5, 10, 0);

            // When
            crewHistory.add(now);

            // Then
            AttendanceState state = crewHistory.findHistory(now.toLocalDate());
            assertAll(
                    () -> assertThat(state.attendanceTime()).isEqualTo(now),
                    () -> assertThat(state.attendanceType()).isEqualTo(AttendanceType.출석)
            );
        }

        @Test
        void 이미_해당_날짜에_출석했다면_예외가_발생한다() {
            // Given
            CrewHistory crewHistory = makeCrewHistory();
            LocalDateTime now = makeAttendTime(2, 10, 0);

            // When
            assertIllegalArgument(() -> crewHistory.add(now), INVALID_DUPLICATE_ATTENDANCE);
        }
    }

    @Nested
    class 크루_기록_테스트 {

        @Test
        void 해당_날짜의_크루_기록을_조회한다() {
            // Given
            CrewHistory crewHistory = makeCrewHistory();

            // When
            LocalDate findDate = makeAttendDate(2);

            // Then
            AttendanceState state = crewHistory.findHistory(findDate);
            assertThat(state.attendanceTime().toLocalDate()).isEqualTo(findDate);
        }

        @Test
        void 크루_기록이_존재하지_않으면_예외가_발생한다() {
            // Given
            CrewHistory crewHistory = makeCrewHistory();
            LocalDate findDate = makeAttendDate(5);

            // When & Then
            assertIllegalArgument(() -> crewHistory.findHistory(findDate), INVALID_DAY_FUTURE);
        }
    }

    @Nested
    class 크루_기록_수정_테스트 {

        @Test
        void 해당_날짜의_크루_기록을_수정한다() {
            // Given
            CrewHistory crewHistory = makeCrewHistory();
            LocalDateTime attendTime = makeAttendTime(2, 10, 0);

            // When
            crewHistory.modify(attendTime);

            // Then
            AttendanceState state = crewHistory.findHistory(attendTime.toLocalDate());
            assertAll(
                    () -> assertThat(state.attendanceTime()).isEqualTo(attendTime),
                    () -> assertThat(state.attendanceType()).isEqualTo(AttendanceType.출석)
            );
        }
    }

    @Nested
    @DisplayName("결과 생성 테스트")
    class 결과_생성_테스트 {

        @Test
        void 출석_결과를_생성한다() {
            // Given
            CrewHistory crewHistory = makeCrewHistory();
            int today = 5;

            // When
            AttendanceResult attendanceResult = crewHistory.makeResult();
            System.out.println(attendanceResult);

            // Then
            assertAll(
                    () -> assertThat(attendanceResult.getAttendanceCount()).isEqualTo(1),
                    () -> assertThat(attendanceResult.getLateCount()).isEqualTo(1),
                    () -> assertThat(attendanceResult.getAbsentCount()).isEqualTo(1)
            );
        }
    }

    @Nested
    class 어제까지의_출석_상태_조회_테스트 {

        @Test
        void 어제까지의_출석_상태를_조회한다() {
            // Given
            CrewHistory crewHistory = makeCrewHistory();
            int today = 5;

            // When
            List<AttendanceState> states = crewHistory.getAttendanceStateUntilYesterday();

            // Then
            assertAll(
                    () -> assertThat(states).hasSize(3),
                    () -> {
                        LocalDateTime lastAttendTime = states.getLast().attendanceTime();
                        assertThat(lastAttendTime.toLocalDate()).isEqualTo(makeAttendDate(4));
                    }
            );
        }
    }

    private LocalDateTime makeAttendTime(int day, int hour, int minute) {
        return LocalDateTime.of(2024, 12, day, hour, minute);
    }

    private LocalDate makeAttendDate(int day) {
        return LocalDate.of(2024, 12, day);
    }

    private CrewHistory makeCrewHistory() {
        CrewHistories crewHistories = new CrewHistories(new HashMap<>(), new Campus());
        String name = "빙봉";

        AttendanceTimes attendanceTimes = new AttendanceTimes(makeAttendDate(2));
        addHistory(attendanceTimes.getMonday(), crewHistories, name);
        addHistory(attendanceTimes.getTuesday().plusMinutes(6), crewHistories, name);
        addHistory(attendanceTimes.getWednesday().plusMinutes(31), crewHistories, name);

        return crewHistories.getCrewHistory(name);
    }

    private void addHistory(final LocalDateTime attendanceTimes, final CrewHistories crewHistories, final String name) {
        crewHistories.loadFromFile(attendanceTimes, name, attendanceTimes);
    }
}
