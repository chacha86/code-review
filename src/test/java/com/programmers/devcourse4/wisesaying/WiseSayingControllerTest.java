package com.programmers.devcourse4.wisesaying;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class WiseSayingControllerTest {

    private ByteArrayOutputStream output;

    private final AppTest AppTest = new AppTest();

    @BeforeEach
    void beforeEach() {
        // TestUtil.clear();  // 필요시 초기화 함수 호출 (여기서는 이미 AppTest.clear() 메서드로 처리)
        output = TestUtil.setOutToByteArray(); // 출력 캡처를 위한 설정
    }

    @Test
    @DisplayName("명언 등록 테스트")
    void t3() throws IOException {
        // 등록 명령을 테스트
        final String input = """
                등록
                현재를 사랑하라.
                작자미상
                """;
        TestUtil.genScanner(input); // 입력값을 Scanner로 처리

        // run() 호출 시 "등록" 명령을 처리한 후 출력을 캡처
        AppTest.run();

        String result = output.toString(); // 캡처된 출력

        assertThat(result)
                .contains("명언 :")
                .contains("작가 :")
                .contains("1번 명언이 등록되었습니다.");
    }

    @Test
    @DisplayName("명언 목록 테스트")
    void t4() throws IOException {
        // 명언 등록 후 목록 조회
        final String input1 = """
                등록
                현재를 사랑하라.
                작자미상
                """;
        final String input2 = """
                등록
                과거를 돌아보지 마라.
                윈스턴 처칠
                """;

        TestUtil.genScanner(input1);
        AppTest.run(); // 첫 번째 등록
        TestUtil.genScanner(input2);
        AppTest.run(); // 두 번째 등록

        // 목록 명령을 입력
        final String inputList = "목록";
        TestUtil.genScanner(inputList);
        AppTest.run(); // 목록 출력

        String result = output.toString(); // 캡처된 출력

        assertThat(result)
                .contains("1 / 작자미상 / 현재를 사랑하라.")
                .contains("2 / 윈스턴 처칠 / 과거를 돌아보지 마라.");
    }

    @Test
    @DisplayName("명언 수정 테스트")
    void t5() throws IOException {
        // 명언 등록 후 수정
        final String input1 = """
                등록
                현재를 사랑하라.
                작자미상
                """;
        TestUtil.genScanner(input1);
        AppTest.run(); // 첫 번째 등록

        // 수정 명령 입력
        final String inputUpdate = """
                수정 1
                오늘을 살아라.
                알버트 아인슈타인
                """;
        TestUtil.genScanner(inputUpdate);
        AppTest.run(); // 수정 실행

        String result = output.toString(); // 캡처된 출력

        assertThat(result)
                .contains("명언(기존): 현재를 사랑하라.")
                .contains("작가(기존): 작자미상")
                .contains("명언: 오늘을 살아라.")
                .contains("작가: 알버트 아인슈타인");
    }

    @Test
    @DisplayName("명언 삭제 테스트")
    void t6() throws IOException, ParseException {
        // 명언 등록 후 삭제
        final String input1 = """
                등록
                현재를 사랑하라.
                작자미상
                """;
        TestUtil.genScanner(input1);
        AppTest.run(); // 첫 번째 등록

        // 삭제 명령 입력
        final String inputDelete = "삭제 1";
        TestUtil.genScanner(inputDelete);
        AppTest.run(); // 삭제 실행

        String result = output.toString(); // 캡처된 출력

        assertThat(result)
                .contains("1번 명언이 삭제되었습니다.");

        // 존재하지 않는 명언 삭제 시나리오
        final String inputNotFound = "삭제 1";
        TestUtil.genScanner(inputNotFound);
        App app = new App();
        app.run(); // 삭제 실행

        String notFoundResult = output.toString(); // 캡처된 출력

        assertThat(notFoundResult)
                .contains("1번 명언은 존재하지 않습니다.");
    }
}
