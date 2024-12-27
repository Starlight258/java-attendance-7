package attendance.service;

import static attendance.exception.ErrorMessage.INVALID_DAY_FUTURE;
import static attendance.support.CustomAssert.assertIllegalArgument;

import attendance.domain.campus.Campus;
import attendance.domain.crew.CrewHistories;
import java.time.LocalDate;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("출석 서비스 테스트")
class AttendanceServiceTest {

    @Test
    void 미래_날짜일_경우_예외가_발생한다() {
        // Given
        CrewHistories crewHistories = new CrewHistories(new HashMap<>(), new Campus());
        AttendanceService attendanceService = new AttendanceService(crewHistories);
        LocalDate today = LocalDate.of(2024, 12, 10);
        LocalDate modifyDate = LocalDate.of(2024, 12, 11);

        // When & Then
        assertIllegalArgument(() -> attendanceService.checkModifyDate(today, modifyDate), INVALID_DAY_FUTURE);
    }
}
