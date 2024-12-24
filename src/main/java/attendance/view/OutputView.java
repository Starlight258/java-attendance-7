package attendance.view;

import attendance.dto.CrewDto;
import attendance.dto.AttendanceDto;
import attendance.dto.ModifyDto;
import attendance.dto.TotalAttendanceDto;
import attendance.util.TimeFormatter;
import java.time.LocalDateTime;
import java.util.List;

public class OutputView {

    private static final String LINE = System.lineSeparator();

    private static final String TITLE_WELCOME = """
            오늘은 %s입니다. 기능을 선택해 주세요.
            1. 출석 확인
            2. 출석 수정
            3. 크루별 출석 기록 확인
            4. 제적 위험자 확인
            Q. 종료""";
    private static final String REQUEST_CHECK_NICKNAME = "닉네임을 입력해 주세요.";
    private static final String REQUEST_CHECK_ATTENDANCE_TIME = "등교 시간을 입력해 주세요.";
    private static final String INFORM_CHECK = "%s (%s)";

    private static final String REQUEST_MODIFY_NICKNAME = "출석을 수정하려는 크루의 닉네임을 입력해 주세요.";
    private static final String REQUEST_MODIFY_DAY = "수정하려는 날짜(일)를 입력해 주세요.";
    private static final String REQUEST_MODIFY_TIME = "언제로 변경하겠습니까?";
    private static final String INFORM_MODIFY = "%s (%s) -> %s (%s) 수정 완료!";

    private static final String REQUEST_HISTORY_NICKNAME = "닉네임을 입력해 주세요.";
    private static final String TITLE_HISTORY = """
            이번 달 %s의 출석 기록입니다.
            """;
    private static final String INFORM_DAILY_HISTORY = "%s (%s)";
    private static final String INFORM_TOTAL_HISTORY = """
            
            출석: %d회
            지각: %d회
            결석: %d회
            """;
    private static final String INFORM_SUBJECT = "%s 대상자입니다.";

    private static final String TITLE_DANGER_SUBJECT = "제적 위험자 조회 결과";
    private static final String INFORM_DANGER_SUBJECT = "- %s: 결석 %d회, 지각 %d회 (%s)";
    private static final String NONE = "NONE";

    public void showTitleWelcome(final LocalDateTime today) {
        showln(format(TITLE_WELCOME, TimeFormatter.makeDateMessage(today)));
    }

    public void showRequestCheckNickname() {
        showln(REQUEST_CHECK_NICKNAME);
    }

    public void showRequestCheckAttendanceTime() {
        showln(REQUEST_CHECK_ATTENDANCE_TIME);
    }

    public void showInformCheck(final AttendanceDto dto) {
        showln(LINE + format(INFORM_CHECK, dto.time(), dto.attendanceType()));
    }

    public void showRequestModifyNickname() {
        showln(LINE + REQUEST_MODIFY_NICKNAME);
    }

    public void showRequestModifyDay() {
        showln(REQUEST_MODIFY_DAY);
    }

    public void showRequestModifyTime() {
        showln(REQUEST_MODIFY_TIME);
    }

    public void showInformModify(final ModifyDto dto) {
        showln(format(INFORM_MODIFY, dto.previousTime(), dto.previousType(),
                dto.currentTime(), dto.currentType()));
    }

    public void showRequestHistoryNickname() {
        showln(LINE + REQUEST_HISTORY_NICKNAME);
    }

    public void showTotalHistories(final String name, final TotalAttendanceDto dtos) {
        showln(format(TITLE_HISTORY, name));
        dtos.dtos().stream()
                .map(dto -> format(INFORM_DAILY_HISTORY, dto.time(), dto.attendanceType()))
                .forEach(this::showln);
        showln(format(INFORM_TOTAL_HISTORY, dtos.attendanceCount(), dtos.lateCount(),
                dtos.absentCount()));
        if (dtos.subject().equals(NONE)) {
            return;
        }
        showln(format(INFORM_SUBJECT, dtos.subject()));
    }

    public void showTitleDangerSubject(final List<CrewDto> dtos) {
        showln(LINE + TITLE_DANGER_SUBJECT);
        dtos.stream()
                .map(dto -> format(INFORM_DANGER_SUBJECT, dto.name(), dto.absentCount(), dto.lateCount(),
                        dto.subjectType()))
                .forEach(this::showln);
    }

    private String format(String format, Object... args) {
        return String.format(format, args);
    }

    private void showln(String message) {
        System.out.println(message);
    }

    public void showBlank() {
        showln("");
    }
}
