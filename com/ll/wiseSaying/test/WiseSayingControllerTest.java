package com.ll.wiseSaying.test;

import com.ll.wiseSaying.src.App;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.*;
import java.io.ByteArrayOutputStream;

public class WiseSayingControllerTest {
    App app;
    @BeforeEach
    void beforeEach() {
        app = new App();
    }

    @Test
    @DisplayName("등록 명령 처리")
    void addWiseSaying() {
        String input = """
            등록
            현재를 사랑하라.
            작자미상
            종료
            """;

        Scanner scanner = TestUtil.genScanner(input);

        ByteArrayOutputStream outputStream = TestUtil.setOutToByteArray();

        app.run(scanner);
        String output = outputStream.toString().trim();
        assertThat(output)
                .contains("== 명언 앱 ==")
                .contains("명언 :")
                .contains("작가 :")
                .contains("1번 명언이 등록되었습니다.");
        TestUtil.clearSetOutToByteArray(outputStream);
    }

    @Test
    @DisplayName("목록 명령 처리")
    void showWiseSaying() {
        String input = """
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록
                삭제?id=1
                삭제?id=1
                """;

        Scanner scanner = TestUtil.genScanner(input);

        ByteArrayOutputStream outputStream = TestUtil.setOutToByteArray();

        app.run(scanner);
        String output = outputStream.toString().trim();

        assertThat(output)
                .contains("== 명언 앱 ==")
                .contains("1 / 작자미상 / 현재를 사랑하라.")
                .contains("2 / 작자미상 / 과거에 집착하지 마라.")
                .contains("1번 명언이 삭제되었습니다.")
                .contains("1번 명언은 존재하지 않습니다.");

        TestUtil.clearSetOutToByteArray(outputStream);
    }

}
