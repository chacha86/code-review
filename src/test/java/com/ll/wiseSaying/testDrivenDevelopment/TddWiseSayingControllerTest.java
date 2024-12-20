package com.ll.wiseSaying.testDrivenDevelopment;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.io.*;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class TddWiseSayingControllerTest {

    private ByteArrayOutputStream outContent;
    private TddWiseSayingController controller;

    @BeforeEach
    void beforeEach() {
        AppTest.clear();
        controller = TddWiseSayingController.getInstance();
        outContent = com.ll.wiseSaying.basicDevelopment.TestUtil.setOutToByteArray();
    }

    @AfterEach
    void afterEach() {
        TestUtil.clearSetOutToByteArray(outContent);
    }

    @Test
    @DisplayName("등록")
    void testRegistration() {
        String input = "현재를 사랑하라.\n작자미상";
        Scanner scanner = TestUtil.genScanner(input);

        controller.register(scanner);

        String output = outContent.toString();
        assertThat(output)
                .contains("명언 :")
                .contains("작가 :")
                .contains("1번 명언이 등록되었습니다.");
    }

    @Test
    @DisplayName("단순 검색")
    void testSearchAll() {
        String input = "현재를 사랑하라.\n작자미상";
        Scanner scanner = TestUtil.genScanner(input);
        controller.register(scanner);

        controller.search(scanner, "목록");

        String output = outContent.toString();
        assertThat(output)
                .contains("번호 / 작가 / 명언")
                .contains("1 / 작자미상 / 현재를 사랑하라.")
                .contains("페이지 :");
    }

    @Test
    @DisplayName("페이지 검색")
    void testSearch2ndPage() {
        StringBuilder input = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            input.append("content").append(i + 1).append("\nauthor").append(i + 1).append("\n");
        }
        Scanner scanner = TestUtil.genScanner(input.toString());
        controller.register(scanner);

        controller.search(scanner, "목록?page=2");

        String output = outContent.toString();
        assertThat(output)
                .contains("번호 / 작가 / 명언")
                .contains("3 / author3 / content3")
                .contains("페이지 : 1 / [2]");
    }

    @Test
    @DisplayName("명언 키워드 검색")
    void testSearchContentKeyword() {
        StringBuilder input = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            input.append("content").append(i / 2).append("\nauthor").append(i + 1).append("\n");
        }
        Scanner scanner = TestUtil.genScanner(input.toString());
        controller.register(scanner);

        controller.search(scanner, "목록?keywordType=content&keyword=1");

        String output = outContent.toString();
        assertThat(output)
                .contains("번호 / 작가 / 명언")
                .contains("1 / author1 / content1")
                .doesNotContain("2 / author2 / content0")
                .contains("페이지 :");
    }

    @Test
    @DisplayName("작가 키워드 검색")
    void testSearchAuthorKeyword() {
        StringBuilder input = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            input.append("content").append(i + 1).append("\nauthor").append(i / 2).append("\n");
        }
        Scanner scanner = TestUtil.genScanner(input.toString());
        controller.register(scanner);

        controller.search(scanner, "목록?keywordType=author&keyword=1");

        String output = outContent.toString();
        assertThat(output)
                .contains("번호 / 작가 / 명언")
                .doesNotContain("1 / author1 / content1")
                .contains("2 / author2 / content0")
                .contains("페이지 :");
    }

    @Test
    @DisplayName("키워드 전체 검색")
    void testSearchComplexKeyword() {
        StringBuilder input = new StringBuilder();
        for (int i = 0; i < 18; i++) {
            input.append("content").append(i + 1).append("\nauthor").append(i / 2).append("\n");
        }
        Scanner scanner = TestUtil.genScanner(input.toString());
        controller.register(scanner);

        controller.search(scanner, "목록?page=2&keywordType=author&keyword=1");

        String output = outContent.toString();
        assertThat(output)
                .contains("번호 / 작가 / 명언")
                .doesNotContain("1 / author1 / content1")
                .contains("2 / author2 / content0")
                .contains("페이지 :")
                .contains("[2]");
    }

    @Test
    @DisplayName("수정")
    void testModify() {
        String existInput = "현재를 사랑하라.\n작자미상";
        Scanner scanner = TestUtil.genScanner(existInput);
        controller.register(scanner);

        String newInput = "현재와 자신을 사랑하라.\n홍길동";
        scanner = TestUtil.genScanner(newInput);

        controller.modify(scanner, "수정?id=1");
        controller.search(scanner, "목록");

        String output = outContent.toString();
        assertThat(output)
                .contains("명언(기존) : 현재를 사랑하라.")
                .contains("작가(기존) : 작자미상")
                .contains("1 / 홍길동 / 현재와 자신을 사랑하라");
    }

    @Test
    @DisplayName("삭제 성공")
    void testDelete_Success() {
        String input = "현재를 사랑하라.\n작자미상";
        Scanner scanner = TestUtil.genScanner(input);
        controller.register(scanner);

        controller.delete("삭제?id=1");

        String output = outContent.toString();
        assertThat(output)
                .contains("1번 명언이 삭제되었습니다.");
    }

    @Test
    @DisplayName("삭제 실패")
    void testDelete_Failure() {
        controller.delete("삭제?id=1");

        String output = outContent.toString();
        assertThat(output)
                .contains("1번 명언은 존재하지 않습니다.");
    }

    @Test
    @DisplayName("빌드")
    void testBuild() throws IOException {
        String input = "현재를 사랑하라.\n작자미상";
        Scanner scanner = TestUtil.genScanner(input);
        controller.register(scanner);

        controller.build();

        String output = outContent.toString();
        assertThat(output)
                .contains("data.json 파일의 내용이 갱신되었습니다.");

        File dataFile = new File("db/wiseSaying/data.json");
        assertThat(dataFile.exists()).isTrue();
        String fileContent = Files.readString(dataFile.toPath());
        String expectedContent = """
        [
          {
            "id": 1,
            "author": "작자미상",
            "content": "현재를 사랑하라."
          }
        ]
        """;
        assertThat(fileContent).isEqualToIgnoringWhitespace(expectedContent);
    }
}
