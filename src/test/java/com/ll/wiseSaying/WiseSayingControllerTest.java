package com.ll.wiseSaying;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayOutputStream;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class WiseSayingControllerTest {

    private ByteArrayOutputStream outContent;
    private WiseSayingController controller;

    @BeforeEach
    void beforeEach() {
        AppTest.clear();
        controller = new WiseSayingController();
        outContent = TestUtil.setOutToByteArray();
    }

    @AfterEach
    void afterEach() {
        TestUtil.clearSetOutToByteArray(outContent);
    }

    @Test
    @DisplayName("등록")
    void testRegister() {
        // given
        String input = "현재를 사랑하라.\n작자미상\n";
        Scanner scanner = TestUtil.genScanner(input);

        // when
        controller.handleCommandLine("등록", scanner);

        // then
        String output = outContent.toString();
        assertThat(output)
                .contains("명언 :")
                .contains("작가 :")
                .contains("1번 명언이 등록되었습니다.");
    }

    @Test
    @DisplayName("목록")
    void testGetQuoteList() {
        // given
        String input = "현재를 사랑하라.\n작자미상\n";
        Scanner scanner = TestUtil.genScanner(input);
        controller.handleCommandLine("등록", scanner);

        // when
        controller.handleCommandLine("목록", null);

        // then
        String output = outContent.toString();
        assertThat(output)
                .contains("번호 / 작가 / 명언")
                .contains("1 / 작자미상 / 현재를 사랑하라.");
    }

    @Test
    @DisplayName("삭제")
    void testDeleteQuote() {
        // given
        String input = "현재를 사랑하라.\n작자미상\n";
        Scanner scanner = TestUtil.genScanner(input);
        controller.handleCommandLine("등록", scanner);

        // when
        controller.handleCommandLine("삭제?id=1", null);

        // then
        String output = outContent.toString();
        assertThat(output)
                .contains("1번 명언이 삭제되었습니다.");
    }

    @Test
    @DisplayName("수정")
    void testUpdateQuote() {
        // given
        String input = "현재를 사랑하라.\n작자미상\n";
        Scanner scanner = TestUtil.genScanner(input);
        controller.handleCommandLine("등록", scanner);

        // when
        String updateInput = "새로운 명언\n새로운 작자\n";
        scanner = TestUtil.genScanner(updateInput);
        controller.handleCommandLine("수정?id=1", scanner);
        controller.handleCommandLine("목록", null);

        // then
        String output = outContent.toString();
        assertThat(output)
                .contains("명언(기존) : 현재를 사랑하라.")
                .contains("명언 :")
                .contains("작가(기존) : 작자미상")
                .contains("작가 :")
                .contains("번호 / 작가 / 명언")
                .contains("1 / 새로운 작자 / 새로운 명언");
    }

    @Test
    @DisplayName("빌드 명령어 테스트")
    void testBuildData() {
        // when
        controller.handleCommandLine("빌드", null);

        // then
        String output = outContent.toString();
        assertThat(output)
                .contains("data.json 파일의 내용이 갱신되었습니다.");
    }
}
