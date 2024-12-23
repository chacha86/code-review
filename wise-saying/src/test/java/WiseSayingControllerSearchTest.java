import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class WiseSayingControllerSearchTest {
    @BeforeEach
    void beforeEach() {
        AppTest.clear();
    }

    @AfterEach
    void afterEach() {
        AppTest.close();
    }

    @Test
    @DisplayName("목록에서 keywordType과 keyword로 검색할 수 있다.")
    void 검색() {
        String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                미래를 사랑하라.
                작자미상
                목록?keywordType=content&keyword=현재
                """);

        assertThat(out)
                .contains("검색타입 : content")
                .contains("검색어 : 현재")
                .contains("번호 / 작가 / 명언")
                .contains("1 / 작자미상 / 현재를 사랑하라.")
                .doesNotContain("2 / 작자미상 / 미래를 사랑하라.");

        String out2 = AppTest.run("""
                목록?keyword=작자&keywordType=author
                """);

        assertThat(out2)
                .contains("검색타입 : author")
                .contains("검색어 : 작자")
                .contains("번호 / 작가 / 명언")
                .contains("1 / 작자미상 / 현재를 사랑하라.")
                .contains("2 / 작자미상 / 미래를 사랑하라.");
    }

    @Test
    @DisplayName("목록에서 검색 시 keywordType과 keyword 모두 필요하다.")
    void 검색조건() {

        String out2 = AppTest.run("""
                목록?keyword=content
                """);

        assertThat(out2)
                .contains("검색 명령 형식이 잘못되었습니다. 예시: 목록?keywordType=content&keyword=검색어");


        String out3 = AppTest.run("""
                목록?keywordType=검색어
                """);

        assertThat(out3)
                .contains("검색 명령 형식이 잘못되었습니다. 예시: 목록?keywordType=content&keyword=검색어");
    }

    @Test
    @DisplayName("목록에서 검색 시 keywordType은 content 또는 author이다.")
    void 검색키워드타입() {
        String out = AppTest.run("""
                목록?keywordType=error&keyword=현재
                """);

        assertThat(out)
                .contains("keywordType은 content 또는 author 입니다. 예시: 목록?keywordType=content&keyword=검색어");
    }
}