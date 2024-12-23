import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WiseSayingControllerPagingTest {
    @BeforeEach
    void beforeEach() {
        AppTest.clear();
    }

    @AfterEach
    void afterEach() {
        AppTest.close();
    }

    @Test
    @DisplayName("명언이 5개가 넘어가면 페이징이 된다.")
    void 페이징() {
        String out = AppTest.run("""
                등록\n1번\n작자미상
                등록\n2번\n작자미상
                등록\n3번\n작자미상
                등록\n4번\n작자미상
                등록\n5번\n작자미상
                등록\n6번\n작자미상
                목록
                """);

        assertThat(out)
                .containsSubsequence(
                        "6 / 작자미상 / 6번",
                        "5 / 작자미상 / 5번",
                        "4 / 작자미상 / 4번",
                        "3 / 작자미상 / 3번",
                        "2 / 작자미상 / 2번",
                        "----------------------",
                        "페이지 : [1] / 2"
                        )
                .doesNotContain("1 / 작자미상 / 1번");
    }

    @Test
    @DisplayName("페이지 번호를 입력하면 해당 페이지가 출력된다.")
    void 페이징2() {
        String out = AppTest.run("""
                등록\n1번\n작자미상
                등록\n2번\n작자미상
                등록\n3번\n작자미상
                등록\n4번\n작자미상
                등록\n5번\n작자미상
                등록\n6번\n작자미상
                목록?page=2
                """);

        assertThat(out)
                .containsSubsequence(
                        "1 / 작자미상 / 1번",
                        "----------------------",
                        "페이지 : 1 / [2]"
                        )
                .doesNotContain("2 / 작자미상 / 2번");
    }

    @Test
    @DisplayName("페이지 번호가 범위를 넘어서면 안 된다.")
    void 페이징3() {
        String out = AppTest.run("""
                등록\n1번\n작자미상
                등록\n2번\n작자미상
                등록\n3번\n작자미상
                등록\n4번\n작자미상
                등록\n5번\n작자미상
                등록\n6번\n작자미상
                목록?page=3
                """);

        assertThat(out)
                .contains("요청하신 페이지에 데이터가 없습니다.");
    }


    @Test
    @DisplayName("검색과 페이징은 동시에 이루어질 수 있다.")
    void 검색페이징() {
        String out = AppTest.run("""
                등록\n1번\n작자미상
                등록\n2번\n홍길동
                등록\n3번\n작자미상
                등록\n4번\n작자미상
                등록\n5번\n홍길동
                등록\n6번\n작자미상
                등록\n7번\n작자미상
                등록\n8번\n홍길동
                등록\n9번\n작자미상
                등록\n10번\n작자미상
                목록?page=1&keywordType=author&keyword=작자미상
                """);

        assertThat(out)
                .containsSubsequence(
                        "10 / 작자미상 / 10번",
                        "9 / 작자미상 / 9번",
                        "7 / 작자미상 / 7번",
                        "6 / 작자미상 / 6번",
                        "4 / 작자미상 / 4번",
                        "----------------------",
                        "페이지 : [1] / 2"
                        )
                .doesNotContain("8 / 작자미상 / 8번")
                .doesNotContain("5 / 작자미상 / 5번");


        String out2 = AppTest.run("""
                목록?page=2&keyword=작자미상&keywordType=author
                """);

        assertThat(out2)
                .containsSubsequence(
                        "3 / 작자미상 / 3번",
                        "1 / 작자미상 / 1번",
                        "----------------------",
                        "페이지 : 1 / [2]"
                        )
                .doesNotContain("2 / 홍길동 / 2번");
    }
}