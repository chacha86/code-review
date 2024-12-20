package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class AppTest {
    private ByteArrayOutputStream outputStream;
    @BeforeEach
    void setUp() throws IOException {
        try(Stream<Path> list = Files.list(Path.of("db/WiseSaying"))){
            list.forEach(file -> file.toFile().delete());
            outputStream = TestUtil.setOutToByteArray();
        }
    }
    @AfterEach
    void tearDown() {
        TestUtil.clearSetOutToByteArray(outputStream);
    }

    @Test
    void addTest(){
        prepare("""
                등록
                너 자신을 알라
                플라톤
                """);

        assertThat(outputStream.toString())
                .contains("명언 :")
                .contains("작가 :")
                .contains("1번 명언이 등록되었습니다.");
    }

    @Test
    void validationTest(){
        prepare("""
                등록
                너 자신을 알라?
                플라톤
                """);
        assertThat(outputStream.toString())
                .contains("특수문자가 포함되면 안됩니다.");
    }

    @Test
    void readAllTest(){
        String no1 = "1";
        String content1 = "\"너 자신을 알라\"";
        String author1 = "\"플라톤\"";
        prepare("등록\n너 자신을 알라\n플라톤\n목록\n");


        assertThat(outputStream.toString())
                .contains("명령) 명언 : 작가 : 1번 명언이 등록되었습니다.")
                .contains("번호 / 작가 / 명언")
                .contains("----------------------")
                .contains(no1 + " / " + author1 + " / " + content1);
    }

    @Test
    void deleteTest(){
        prepare("""
                등록
                너 자신을 알라
                플라톤
                삭제?id=1
                """);

        assertThat(outputStream.toString())
                .contains("1번 명언이 삭제되었습니다.");
    }

    @Test
    void deleteFailTest(){
        prepare("""
                등록
                너 자신을 알라
                플라톤
                삭제?id=2
                """);
        assertThat(outputStream.toString())
                .contains("2번 명언은 존재하지 않습니다.");
    }


    @Test
    void updateTest(){
        prepare("""
                등록
                너 자신을 알라
                플라톤
                수정?id=1
                너 자신을 몰라도 된다
                플라리스
                목록
                """);

        assertThat(outputStream.toString())
                .contains("번호 / 작가 / 명언")
                .contains("----------------------")
                .contains(1 + " / " + "\"플라리스\"" + " / " + "\"너 자신을 몰라도 된다\"");
    }

    @Test
    void updateFailTest(){
        prepare("""
                등록
                너 자신을 알라
                플라톤
                수정?id=2
                """);
        assertThat(outputStream.toString())
                .contains("2번 명언은 존재하지 않습니다.");
    }

    @Test
    void buildTest() throws IOException {
        prepare("""
                등록
                너 자신을 알라
                플라톤
                등록
                너 자신을 알라2
                플라톤2
                빌드
                """);

        assertThat(outputStream.toString())
                .contains("data.json 파일의 내용이 갱신되었습니다.");
        File file = new File("db/WiseSaying/build.json");
        assertThat(file.exists()).isTrue();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String s = reader.lines().collect(Collectors.joining("\n"));
        assertThat(s).contains("\"id\": 1")
                .contains("\"content\": \"너 자신을 알라\"")
                .contains("\"author\": \"플라톤\"")
                .contains("\"id\": 2")
                .contains("\"content\": \"너 자신을 알라2\"")
                .contains("\"author\": \"플라톤2\"");
    }

    @Test
    void paginationTest(){
        prepare("""
                등록
                명언1
                저자1
                등록
                명언2
                저자2
                등록
                명언3
                저자3
                등록
                명언4
                저자4
                등록
                명언5
                저자5
                등록
                명언6
                저자6
                등록
                명언7
                저자7
                등록
                명언8
                저자8
                등록
                명언9
                저자9
                등록
                명언10
                저자10
                목록?page=2
                """);

        assertThat(outputStream.toString())
                .contains("5 / \"저자5\" / \"명언5\"");
    }

    @Test
    void searchTest(){
        prepare("""
                등록
                명언검색어1
                저자검색어1
                등록
                명언검색어2
                저자검색어1
                등록
                명언검색어1
                저자검색어2
                등록
                명언검색어2
                저자검색어2
                목록?keywordType=content&keyword=명언검색어1
                목록?keywordType=author&keyword=저자검색어2
                """);

        assertThat(outputStream.toString())
                .contains("3 / \"저자검색어2\" / \"명언검색어1\"")
                .contains("1 / \"저자검색어1\" / \"명언검색어1\"");
    }

    private static void prepare(String input) {
        Scanner scanner = TestUtil.genScanner(input);
        WiseSayingRepository repository = new WiseSayingRepository();
        WiseSayingService service = new WiseSayingService(repository);
        WiseSayingController controller = new WiseSayingController(scanner, service);

        App app = new App(controller, scanner);
        app.run();
    }
}