package attendance.domain.crew;

import static attendance.exception.ErrorMessage.INVALID_ATTENDANCE_CAMPUS_OPERATION_TIME;
import static attendance.exception.ErrorMessage.INVALID_ATTENDANCE_DAY;
import static attendance.exception.ErrorMessage.INVALID_NICKNAME;
import static attendance.support.CustomAssert.assertIllegalArgument;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import attendance.domain.attendance.AttendanceResult;
import attendance.domain.attendance.AttendanceState;
import attendance.domain.attendance.AttendanceType;
import attendance.domain.campus.Campus;
import attendance.exception.ErrorPrefix;
import attendance.support.AttendanceTimes;
import attendance.util.TimeFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("크루들의 기록 테스트")
class CrewHistoriesTest {

    private static final int LATE_MINUTES = 6;
    private static final int ABSENT_MINUTES = 31;

    @Nested
    class 출석_기록_데이터_테스트 {

        @Test
        void 크루들의_기록이_없으면_기록을_생성하여_기록을_저장한다() {
            // Given
            CrewHistories crewHistories = new CrewHistories(new HashMap<>(), new Campus());
            LocalDateTime attendTime = LocalDateTime.of(2024, 12, 2, 9, 0);
            LocalDateTime today = LocalDate.of(2024, 12, 13).atStartOfDay();

            // When
            crewHistories.loadFromFile(attendTime, "빙봉", today);

            // Then
            assertThat(crewHistories.getCrewHistory("빙봉"))
                    .extracting("history")
                    .matches(history -> {
                        Map<?, ?> historyMap = (Map<?, ?>) history;
                        AttendanceState state = (AttendanceState) historyMap.get(2);
                        return historyMap.size() == 9 && state.attendanceType() == AttendanceType.출석;
                    });
        }

        @Test
        void 운영시간이_아닌_시간에_출석_기록이_존재하는_경우_예외가_발생한다() {
            // Given
            CrewHistories crewHistories = new CrewHistories(new HashMap<>(), new Campus());
            LocalDateTime attendTime = LocalDateTime.of(2024, 12, 2, 7, 0);
            LocalDateTime today = LocalDate.of(2024, 12, 13).atStartOfDay();

            // When & Then
            assertIllegalArgument(() -> crewHistories.loadFromFile(attendTime, "빙봉", today),
                    INVALID_ATTENDANCE_CAMPUS_OPERATION_TIME);
        }
    }

    @ParameterizedTest
    @CsvSource({
            "빙봉, false",
            "쿠키, true"
    })
    void 해당_크루가_저장되어_있지_않으면_true를_반환한다(final String nickname, final boolean expected) {
        // Given
        CrewHistories crewHistories = makeHistories();

        // When & Then
        assertThat(crewHistories.notContains(nickname)).isEqualTo(expected);
    }

    @Nested
    class 출석_기록_저장_테스트 {

        @Test
        void 출석_기록을_저장한다() {
            // Given
            CrewHistories crewHistories = makeHistories();
            LocalDateTime attendTime = LocalDateTime.of(2024, 12, 17, 9, 0);
            String nickname = "빙봉";

            // When
            crewHistories.addHistory(nickname, attendTime);

            // Then
            assertThat(crewHistories.getCrewHistory(nickname).findHistory(attendTime.toLocalDate()))
                    .extracting("attendanceType")
                    .isEqualTo(AttendanceType.출석);
        }

        @Test
        void 출석_시간이_운영시간이_아닐_경우_예외가_발생한다() {
            // Given
            CrewHistories crewHistories = makeHistories();
            LocalDateTime attendTime = LocalDateTime.of(2024, 12, 2, 7, 0);
            String nickname = "빙봉";

            // When & Then
            assertIllegalArgument(() -> crewHistories.addHistory(nickname, attendTime),
                    INVALID_ATTENDANCE_CAMPUS_OPERATION_TIME);
        }
    }

    @Nested
    class 출석_기록_수정_테스트 {

        @Test
        void 기록을_수정한다() {
            // Given
            CrewHistories crewHistories = makeHistories();
            LocalDateTime attendTime = LocalDateTime.of(2024, 12, 2, 9, 0);
            String nickname = "빙봉";

            // When
            crewHistories.modifyTime(nickname, attendTime);

            // Then
            assertThat(crewHistories.getCrewHistory(nickname).findHistory(attendTime.toLocalDate()))
                    .extracting("attendanceType")
                    .isEqualTo(AttendanceType.출석);
        }

        @Test
        void 수정할_시간이_운영시간이_아니면_예외가_발생한다() {
            // Given
            CrewHistories crewHistories = makeHistories();
            LocalDateTime attendTime = LocalDateTime.of(2024, 12, 2, 7, 0);
            String nickname = "빙봉";

            // When
            assertIllegalArgument(() -> crewHistories.modifyTime(nickname, attendTime),
                    INVALID_ATTENDANCE_CAMPUS_OPERATION_TIME);
        }
    }

    @Nested
    class 출석_기록_조회_테스트 {

        @Test
        void 기록을_조회한다() {
            // Given
            CrewHistories crewHistories = makeHistories();
            LocalDateTime attendTime = LocalDateTime.of(2024, 12, 2, 9, 0);
            String nickname = "빙봉";

            // When
            CrewHistory crewHistory = crewHistories.getCrewHistory(nickname);

            // Then
            assertThat(crewHistory.findHistory(attendTime.toLocalDate()))
                    .extracting("attendanceType")
                    .isEqualTo(AttendanceType.출석);
        }

        @Test
        void 해당_크루에_대한_기록이_없을_경우_예외가_발생한다() {
            // Given
            CrewHistories crewHistories = makeHistories();

            // When
            assertIllegalArgument(() -> crewHistories.getCrewHistory("쿠키"),
                    INVALID_NICKNAME);
        }
    }

    @Nested
    class 운영일_검증_테스트 {

        @Test
        void 운영일이_아닐_경우_예외가_발생한다() {
            // Given
            CrewHistories crewHistories = makeHistories();
            LocalDate today = LocalDate.of(2024, 12, 1);

            // When
            assertThatThrownBy(() -> crewHistories.checkDate(today))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith(ErrorPrefix.format(""))
                    .hasMessageContaining(INVALID_ATTENDANCE_DAY.getMessage(TimeFormatter.makeDateMessage(today)));
        }
    }

    @Test
    void 결과를_결석을_지각을_간주하고_이름_오름차순으로_정렬한다() {
        // Given
        CrewHistories crewHistories = new CrewHistories(new HashMap<>(), new Campus());
        AttendanceTimes times = new AttendanceTimes(LocalDate.of(2024, 12, 2));

        addBingBongHistory(crewHistories, times);
        addBingTiHistory(crewHistories, times);
        addCookieHistory(crewHistories, times);
        addEdenHistory(crewHistories, times);
        addJjangSuHistory(crewHistories, times);

        // When
        Map<String, AttendanceResult> results = crewHistories.sortResults(12);

        // Then
        assertThat(results).containsExactlyInAnyOrderEntriesOf(createExpectedResults());
    }

    private CrewHistories makeHistories() {
        CrewHistories crewHistories = new CrewHistories(new HashMap<>(), new Campus());
        LocalDateTime attendTime = LocalDateTime.of(2024, 12, 2, 13, 0);
        LocalDateTime today = LocalDate.of(2024, 12, 13).atStartOfDay();

        crewHistories.loadFromFile(attendTime, "빙봉", today);
        crewHistories.loadFromFile(attendTime.plusMinutes(6), "이든", today);
        return crewHistories;
    }

    private void addBingBongHistory(CrewHistories histories, AttendanceTimes times) {
        String name = "빙봉";
        histories.loadFromFile(times.getMonday().plusMinutes(LATE_MINUTES), name, times.getToday());
        addLateHistory(histories, name, times.getTuesday());
        addLateHistory(histories, name, times.getWednesday());
        addLateHistory(histories, name, times.getThursday());
        addLateHistory(histories, name, times.getFriday());
        addLateHistory(histories, name, times.getNextMonday());
        addAbsentHistory(histories, name, times.getNextTuesday());
    }

    private void addBingTiHistory(CrewHistories histories, AttendanceTimes times) {
        String name = "빙티";
        histories.loadFromFile(times.getMonday().plusMinutes(ABSENT_MINUTES), name, times.getToday());
        addLateHistory(histories, name, times.getTuesday());
        addLateHistory(histories, name, times.getWednesday());
        addLateHistory(histories, name, times.getThursday());
        addLateHistory(histories, name, times.getFriday());
        addAbsentHistory(histories, name, times.getNextMonday());
        addAbsentHistory(histories, name, times.getNextTuesday());
    }

    private void addCookieHistory(CrewHistories histories, AttendanceTimes times) {
        String name = "쿠키";
        histories.loadFromFile(times.getMonday().plusMinutes(ABSENT_MINUTES), name, times.getToday());
        addLateHistory(histories, name, times.getTuesday());
        addLateHistory(histories, name, times.getWednesday());
        addLateHistory(histories, name, times.getThursday());
        addAbsentHistory(histories, name, times.getFriday());
        histories.addHistory(name, times.getNextMonday().plusMinutes(1));
        histories.addHistory(name, times.getNextTuesday().plusMinutes(1));
    }

    private void addEdenHistory(CrewHistories histories, AttendanceTimes times) {
        String name = "이든";
        histories.loadFromFile(times.getMonday().plusMinutes(ABSENT_MINUTES), name, times.getToday());
        addLateHistory(histories, name, times.getTuesday());
        addLateHistory(histories, name, times.getWednesday());
        addLateHistory(histories, name, times.getThursday());
        addLateHistory(histories, name, times.getFriday());
        addLateHistory(histories, name, times.getNextMonday());
        addAbsentHistory(histories, name, times.getNextTuesday());
    }

    private void addJjangSuHistory(CrewHistories histories, AttendanceTimes times) {
        String name = "짱수";
        histories.loadFromFile(times.getMonday().plusMinutes(LATE_MINUTES), name, times.getToday());
        addLateHistory(histories, name, times.getTuesday());
        addLateHistory(histories, name, times.getWednesday());
        addLateHistory(histories, name, times.getThursday());
        addLateHistory(histories, name, times.getFriday());
        addLateHistory(histories, name, times.getNextMonday());
        histories.addHistory(name, times.getNextTuesday().plusMinutes(1));
    }

    private void addLateHistory(CrewHistories histories, String name, LocalDateTime time) {
        histories.addHistory(name, time.plusMinutes(LATE_MINUTES));
    }

    private void addAbsentHistory(CrewHistories histories, String name, LocalDateTime time) {
        histories.addHistory(name, time.plusMinutes(ABSENT_MINUTES));
    }

    private Map<String, AttendanceResult> createExpectedResults() {
        return Map.of(
                "빙티", new AttendanceResult(Map.of(AttendanceType.지각, 4, AttendanceType.결석, 3)),
                "이든", new AttendanceResult(Map.of(AttendanceType.지각, 5, AttendanceType.결석, 2)),
                "빙봉", new AttendanceResult(Map.of(AttendanceType.지각, 6, AttendanceType.결석, 1)),
                "쿠키", new AttendanceResult(Map.of(AttendanceType.출석, 2, AttendanceType.지각, 3, AttendanceType.결석, 2)),
                "짱수", new AttendanceResult(Map.of(AttendanceType.출석, 1, AttendanceType.지각, 6))
        );
    }
}
