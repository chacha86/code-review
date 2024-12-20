
package com.ll.wiseSaying;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class WiseSayingControllerTest {

    App app;

    @BeforeEach
    void beforeEach() {
        app = new App();
    }

    private void runTest(String input, String... expectedOutputs) {
        Scanner scanner = TestUtil.genScanner(input);
        ByteArrayOutputStream outputStream = TestUtil.setOutToByteArray(); // 이게 먼저 실행되야, scanner의 값을 가져올 수 있습니다.

        app.run(scanner);
        String output = outputStream.toString().trim();

        for (String expected : expectedOutputs) {
            assertThat(output).contains(expected);
        }

        TestUtil.clearSetOutToByteArray(outputStream);
    }

    @Test
    @DisplayName("등록 명령 처리")
    void testRegisterWiseSaying() {
        String input = """
            등록
            현재를 사랑하라.
            작자미상
            """;

        runTest(input, "== 명언 앱 ==", "명언 :", "작가 :", "1번 명언이 등록되었습니다.");
    }

    @Test
    @DisplayName("목록 명령 처리")
    void testListWiseSaying() {

        String input = """
            등록
            현재를 사랑하라.
            작자미상
            등록
            과거에 집착하지 마라.
            작자미상
            목록
            """;

        runTest(input, "번호 / 작가 / 명언"
                        , "----------------------"
                        , "2 / 작자미상 / 과거에 집착하지 마라."
                        , "1 / 작자미상 / 현재를 사랑하라.");
    }

    @Test
    @DisplayName("삭제 명령 처리")
    void testDeleteWiseSaying() {

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

        runTest(input, "1번 명언이 삭제되었습니다."
                , "1번 명언은 존재하지 않습니다.");
    }

    // 초기 10개의 임시데이터로 인한 숫자 수정
    @Test
    @DisplayName("수정 명령 처리")
    void testModifyWiseSaying() {

        String input = """
            등록
            현재를 사랑하라.
            작자미상
            등록
            과거에 집착하지 마라.
            작자미상
            목록
            삭제?id=11
            삭제?id=11
            수정?id=13
            수정?id=12
            현재와 자신을 사랑하라.
            홍길동
            목록
            """;

        runTest(input, "명언(기존) : 과거에 집착하지 마라."
                , "명언 : "
                , "작가(기존) : 작자미상"
                , "작가 : "
                , "번호 / 작가 / 명언"
                , "----------------------"
                , "12 / 홍길동 / 현재와 자신을 사랑하라.");
    }

    @Test
    @DisplayName("종료 명령 처리")
    void testEndWiseSaying() {

        String input = """
            등록
            현재를 사랑하라.
            작자미상
            등록
            과거에 집착하지 마라.
            작자미상
            종료
            """;

        runTest(input, "11번 명언이 등록되었습니다."
                , "12번 명언이 등록되었습니다."
                , "프로그램 다시 시작...");

        File lastIdFile = new File("db/wiseSaying/lastId.txt");
        assertThat(lastIdFile.exists()).isTrue();

        try {
            String lastIdContent = Files.readString(lastIdFile.toPath()).trim();
            assertThat(lastIdContent).isEqualTo("12");

            // 개별 명언 파일 확인
            File file11 = new File("db/wiseSaying/11.json");  // 1.json -> 11.json
            File file12 = new File("db/wiseSaying/12.json");  // 2.json -> 12.json
            assertThat(file11.exists()).isTrue();
            assertThat(file12.exists()).isTrue();

            // 11.json 파일 내용 확인
            String content11 = Files.readString(file11.toPath());
            assertThat(content11).contains("\"id\": 11")
                    .contains("\"content\": \"현재를 사랑하라.\"")
                    .contains("\"author\": \"작자미상\"");

            // 12.json 파일 내용 확인
            String content12 = Files.readString(file12.toPath());
            assertThat(content12).contains("\"id\": 12")
                    .contains("\"content\": \"과거에 집착하지 마라.\"")
                    .contains("\"author\": \"작자미상\"");

        } catch (IOException e) {
            fail("JSON파일을 읽는데 실패했습니다.");
        }
    }

    @Test
    @DisplayName("빌드 명령 처리")
    void testBuildWiseSaying() {
        String input = """
           등록
           현재를 사랑하라.
           작자미상
           등록
           과거에 집착하지 마라.
           작자미상
           빌드
           """;

        Scanner scanner = TestUtil.genScanner(input);
        ByteArrayOutputStream outputStream = TestUtil.setOutToByteArray();
        app.run(scanner);

        // data.json 파일 확인
        File dataFile = new File("data.json");
        assertThat(dataFile.exists()).isTrue();

        try {
            String content = Files.readString(dataFile.toPath());
            // JSON 배열 형식 확인
            assertThat(content)
                    .startsWith("[")
                    .endsWith("]")
                    // 초기 10개의 데이터와 추가된 2개의 데이터가 있어야 함
                    .contains("\"id\": 11")
                    .contains("\"content\": \"현재를 사랑하라.\"")
                    .contains("\"author\": \"작자미상\"")
                    .contains("\"id\": 12")
                    .contains("\"content\": \"과거에 집착하지 마라.\"")
                    .contains("\"author\": \"작자미상\"");

            // 출력 메시지 확인
            String output = outputStream.toString().trim();
            assertThat(output).contains("data.json 파일의 내용이 갱신되었습니다.");
        } catch (IOException e) {
            fail("data.json 파일을 읽는데 실패했습니다.");
        } finally {
            TestUtil.clearSetOutToByteArray(outputStream);
        }
    }

    @Test
    @DisplayName("명언 검색 테스트")
    void testSearchWiseSaying() {
        // 첫 번째 테스트: content 검색
        String input = """
           등록
           현재를 사랑하라.
           작자미상
           등록
           과거에 집착하지 마라.
           작자미상
           목록?keywordType=content&keyword=과거
           """;

        Scanner scanner = TestUtil.genScanner(input);
        ByteArrayOutputStream outputStream = TestUtil.setOutToByteArray();
        app.run(scanner);

        String output = outputStream.toString().trim();
        assertThat(output)
                .contains("검색타입 : content")
                .contains("검색어 : 과거")
                .contains("번호 / 작가 / 명언")
                .contains("2 / 작자미상 / 과거에 집착하지 마라.")
                .doesNotContain("1 / 작자미상 / 현재를 사랑하라.");
        TestUtil.clearSetOutToByteArray(outputStream);

        // 두 번째 테스트: author 검색
        input = """
           등록
           현재를 사랑하라.
           작자미상
           등록
           과거에 집착하지 마라.
           작자미상
           목록?keywordType=author&keyword=작자
           """;

        scanner = TestUtil.genScanner(input);
        outputStream = TestUtil.setOutToByteArray();
        app.run(scanner);

        output = outputStream.toString().trim();
        assertThat(output)
                .contains("검색타입 : author")
                .contains("검색어 : 작자")
                .contains("2 / 작자미상 / 과거에 집착하지 마라.")
                .contains("1 / 작자미상 / 현재를 사랑하라.");

        TestUtil.clearSetOutToByteArray(outputStream);
    }

    @Test
    @DisplayName("페이징 테스트")
    void testPaging() {
        String input = """
           목록
           목록?page=2
           목록?keywordType=content&keyword=명언&page=1
           목록?keywordType=author&keyword=작자&page=2
           """;

        Scanner scanner = TestUtil.genScanner(input);
        ByteArrayOutputStream outputStream = TestUtil.setOutToByteArray();
        app.run(scanner);

        String output = outputStream.toString().trim();

        // 기본 페이징 - 첫 페이지
        assertThat(output)
                .contains("10 / 작자미상 10 / 명언 10")
                .contains("9 / 작자미상 9 / 명언 9")
                .contains("8 / 작자미상 8 / 명언 8")
                .contains("7 / 작자미상 7 / 명언 7")
                .contains("6 / 작자미상 6 / 명언 6")
                .contains("페이지 : [1] / 2")
                // 기본 페이징 - 두 번째 페이지
                .contains("5 / 작자미상 5 / 명언 5")
                .contains("4 / 작자미상 4 / 명언 4")
                .contains("3 / 작자미상 3 / 명언 3")
                .contains("2 / 작자미상 2 / 명언 2")
                .contains("1 / 작자미상 1 / 명언 1")
                .contains("페이지 : 1 / [2]")
                // content 검색 결과
                .contains("검색타입 : content")
                .contains("검색어 : 명언")
                // author 검색 결과
                .contains("검색타입 : author")
                .contains("검색어 : 작자");

        TestUtil.clearSetOutToByteArray(outputStream);
    }
}
