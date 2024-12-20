package wiseSaying;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


class WiseSayingControllerTest {
    private WiseSayingController controller;
    private WiseSayingService service;
    private WiseSayingRepository repository;

    @BeforeEach
    void before() {
        controller = WiseSayingController.getInstance();
        service = WiseSayingService.getInstance();
        repository = WiseSayingRepository.getInstance();

        for (int i = 1; i <= 10; i++) {
            controller.register("content" + i, "author" + i);
        }
    }

    @AfterEach
    void after() {
        repository.clear();
        TestUtil.eraseFile();
    }

    @Test
    @DisplayName("등록 후 전체 검색 - 성공")
    void registerSuccess() {
        // when
        List<WiseSaying> searchList = controller.search("목록");

        // then
        assertThat(searchList.get(0).getContent()).isEqualTo("content10");
        assertThat(searchList.get(0).getAuthor()).isEqualTo("author10");
    }

    @Test
    @DisplayName("등록 후 조건 검색 - 성공")
    void registerWithConditionSuccess() {
        // when
        List<WiseSaying> searchList = controller.search("목록?keywordType=content&keyword=1");

        // then
        assertThat(searchList.stream().findFirst().get().getContent()).isEqualTo("content10");
    }
    @Test
    @DisplayName("등록 후 조건 검색 - 실패")
    void registerWithConditionFail() {
        // when
        List<WiseSaying> searchList = controller.search("목록?keywordType=content&keyword=2");

        // then
        assertThat(searchList.get(0).getContent()).isEqualTo("content2");
    }

    @Test
    @DisplayName("등록 후 조건 없이 & 페이지 있을 때 - 성공 ")
    void registerWithPageSuccess() {
        // when
        List<WiseSaying> searchList = controller.search("목록?page=1");

        // then
        assertThat(searchList.get(0).getContent()).isEqualTo("content10");
    }

    @Test
    @DisplayName("등록 후 조건 있을 때 & 페이지 있을 때 - 성공 ")
    void registerWithConditionAndPageSuccess() {
        // when
        List<WiseSaying> searchList = controller.search("목록?keywordType=content&keyword=1&page=1");

        // then
        assertThat(searchList.get(0).getContent()).isEqualTo("content10");
    }

    @Test
    @DisplayName("등록 후 조건 있을 때 & 페이지 있을 때 - 실패 : page miss ")
    void registerWithConditionAndPageFail() {
        // when
        List<WiseSaying> searchList = controller.search("목록?keywordType=content&keyword=1&page=2");

        // then
        assertThat(searchList).isEqualTo(null);
    }

    @Test
    @DisplayName("수정 후 정상 변경 검증")
    void modifySuccess() {
        // when
        WiseSaying modified = service.modify(7L, "content77", "author77");
        // then
        assertThat(repository.findById(modified.getId()).orElseThrow().getContent()).isEqualTo("content77");
        assertThat(repository.findById(modified.getId()).orElseThrow().getAuthor()).isEqualTo("author77");
    }
}