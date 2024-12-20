import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class WiseSayingControllerTest {
    @BeforeEach
    void beforeEach() {
        AppTest.clear();
    }

    @AfterEach
    void afterEach() {
        AppTest.close();
    }

    @Test
    @DisplayName("프로그램을 종료할 수 있다.")
    void 종료() {
        String out = AppTest.run("""
                종료
                """);

        assertThat(out).isEqualTo("== 명언 앱 ==\n명령) ");
    }

    @Test
    @DisplayName("종료 명령어 입력 전까진 프로그램이 종료되지 않는다.")
    void 미종료() {
        String out = AppTest.run("""
                테스트
                미종료
                """);
        assertThat(out).endsWith("명령) ");
    }

    @Test
    @DisplayName("명언을 등록할 수 있다.")
    void 등록() {
        String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                """);

        assertThat(out)
                .startsWith("== 명언 앱 ==")
                .contains("명령) ")
                .contains("명언 : ")
                .contains("작가 : ")
                .contains("번 명언이 등록되었습니다.");
    }

    @Test
    @DisplayName("명언을 여러 번 등록할 수 있다.")
    void 등록2() {
        String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                미래를 사랑하라.
                홍길동
                """);

        assertThat(out)
                .contains("1번 명언이 등록되었습니다.")
                .contains("2번 명언이 등록되었습니다.");
    }

    @Test
    @DisplayName("명언 목록을 확인할 수 있다.")
    void 목록() {
        String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                미래를 사랑하라.
                홍길동
                목록
                """);

        assertThat(out)
            .contains("번호 / 작가 / 명언")
            .contains("----------------------")
            .contains("1 / 작자미상 / 현재를 사랑하라.")
            .contains("2 / 홍길동 / 미래를 사랑하라.");
    }

    @Test
    @DisplayName("명언을 id로 삭제할 수 있다.")
    void 삭제() {
        String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                삭제?id=1
                """);

        assertThat(out)
            .contains("1번 명언이 삭제되었습니다.");
    }

    @Test
    @DisplayName("존재하지 않은 명언은 삭제할 수 없다.")
    void 삭제2() {
        String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                삭제?id=2
                """);

        assertThat(out)
            .contains("2번 명언은 존재하지 않습니다.");
    }

    @Test
    @DisplayName("명언을 수정할 수 있다.")
    void 수정() {
        String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                수정?id=1
                자신을 사랑하라.
                이순신
                """);

        assertThat(out)
                .contains("명언(기존) : 현재를 사랑하라.")
                .contains("명언 : ")
                .contains("작가(기존) : 작자미상")
                .contains("작가 : ");
    }

    @Test
    @DisplayName("존재하지 않은 명언은 수정할 수 없다.")
    void 수정2() {
        String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                수정?id=5
                자신을 사랑하라.
                이순신
                """);

        assertThat(out)
            .contains("5번 명언은 존재하지 않습니다.");
    }

    @Test
    @DisplayName("data.json 파일을 빌드할 수 있다.")
    void 빌드() throws IOException {
        String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                미래를 사랑하라.
                홍길동
                빌드
                """);
        Path dataPath = Path.of("db/testWiseSaying/data.json");
        String dataContent = Files.readString(dataPath);
        assertThat(dataContent).isEqualTo("[\n\t" +
                "{\n\t\t\"id\": 1," +
                "\n\t\t\"content\": \"현재를 사랑하라.\"," +
                "\n\t\t\"author\": \"작자미상\"\n\t},\n\t" +
                "{\n\t\t\"id\": 2," +
                "\n\t\t\"content\": \"미래를 사랑하라.\"," +
                "\n\t\t\"author\": \"홍길동\"\n\t}\n]");
        assertThat(out)
            .contains("data.json 파일의 내용이 갱신되었습니다.");
    }

}