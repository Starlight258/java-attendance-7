package attendance.view;

import attendance.dto.DangerCrewsDto;
import attendance.dto.InformDto;
import attendance.dto.ModifyDto;
import attendance.dto.MonthTotalAttendanceDto;
import attendance.util.TimeFormatter;
import java.time.LocalDateTime;

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

    private static final String REQUEST_LOG_NICKNAME = "닉네임을 입력해 주세요.";
    private static final String TITLE_LOG = """
            이번 달 %s의 출석 기록입니다.
            
            """;
    private static final String INFORM_DAILY_LOG = "%s (%s)";
    private static final String INFORM_TOTAL_LOG = """
            
            출석: %d회
            지각: %d회
            결석: %d회
            
            """;
    private static final String INFORM_SUBJECT = "%s 대상자입니다.";

    // 기능 4
    private static final String TITLE_DANGER_SUBJECT = "제적 위험자 조회 결과";
    private static final String INFORM_DANGER_SUBJECT = "%s: 결석 %d회, 지각 %d회 (%s)";

    // 기능 1
    public void showTitleWelcome(final LocalDateTime today) {
        showln(format(TITLE_WELCOME, TimeFormatter.makeDateMessage(today)));
    }

    public void showRequestCheckNickname() {
        showln(REQUEST_CHECK_NICKNAME);
    }

    public void showRequestCheckAttendanceTime() {
        showln(REQUEST_CHECK_ATTENDANCE_TIME);
    }

    // "12월 %02d일 %s요일 %02d:%02d (%s)";
    public void showInformCheck(final InformDto dto) {
        showln(LINE + format(INFORM_CHECK, dto.time(), dto.attendanceType()));
    }

    // 기능 2
    // 오늘은 12월 13일 금요일입니다. 기능을 선택해 주세요.
    //1. 출석 확인
    //2. 출석 수정
    //3. 크루별 출석 기록 확인
    //4. 제적 위험자 확인
    //Q. 종료
    //2
    //
    //출석을 수정하려는 크루의 닉네임을 입력해 주세요.
    //빙티
    //수정하려는 날짜(일)를 입력해 주세요.
    //3
    //언제로 변경하겠습니까?
    //09:58
    //
    //12월 03일 화요일 10:07 (지각) -> 09:58 (출석) 수정 완료!
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

    // 기능 3
    // 닉네임을 입력해 주세요.
    //빙티
    //
    //이번 달 빙티의 출석 기록입니다.
    //
    //12월 02일 월요일 13:00 (출석)
    //12월 03일 화요일 09:58 (출석)
    //12월 04일 수요일 10:02 (출석)
    //12월 05일 목요일 10:06 (지각)
    //12월 06일 금요일 10:01 (출석)
    //12월 09일 월요일 --:-- (결석)
    //12월 10일 화요일 10:08 (지각)
    //12월 11일 수요일 --:-- (결석)
    //12월 12일 목요일 --:-- (결석)
    //
    //출석: 4회
    //지각: 2회
    //결석: 3회
    //
    //면담 대상자입니다.
    public void showRequestLogNickname() {
        showln(REQUEST_LOG_NICKNAME);
    }

    public void showTotalLog(final String name, final MonthTotalAttendanceDto dtos) {
        showln(format(TITLE_LOG, name));
        dtos.dtos().stream()
                .map(dto -> format(INFORM_DAILY_LOG, dto.time(), dto.attendanceType()))
                .forEach(this::showln);
        showln(format(INFORM_TOTAL_LOG, dtos.attendanceCount(), dtos.lateCount(),
                dtos.absentCount()));
        showln(format(INFORM_SUBJECT, dtos.subject()));
    }

    // 기능 4
    public void showTitleDangerSubject(final DangerCrewsDto dtos) {
        showln(TITLE_DANGER_SUBJECT);
        dtos.dtos().stream()
                .map(dto -> format(INFORM_DANGER_SUBJECT, dto.name(), dto.absentCount(), dto.lateCount(),
                        dto.subjectType()))
                .forEach(this::showln);
    }

    public void showException(Exception exception) {
        showln(exception.getMessage());
    }

    private String format(String format, Object... args) {
        return String.format(format, args);
    }

    private void showln(String message) {
        System.out.println(message);
    }

}
