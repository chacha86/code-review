package com.ll.wiseSaying;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

class WiseSayingServiceTest {
    SoftAssertions softAssertions;
    private WiseSayingService wiseSayingService;

    private final String SAMPLE_AUTHOR1 = "작자미상";
    private final String SAMPLE_CONTENT1 = "과거에 집착하지 마라";
    private final String SAMPLE_AUTHOR2 = "키케로";
    private final String SAMPLE_CONTENT2 = "삶이 있는 한 희망은 있다";
    private final String SAMPLE_AUTHOR3 = "로버트 엘리엇";
    private final String SAMPLE_CONTENT3 = "피할 수 없으면 즐겨라";
    private final String SAMPLE_AUTHOR4 = "괴테";
    private final String SAMPLE_CONTENT4 = "꿈을 계속 간직하고 있으면 반드시 실현할 때가 온다";
    private final String SAMPLE_AUTHOR5 = "윈스턴 처칠";
    private final String SAMPLE_CONTENT5 = "계속해서 꾸준한 노력으로 잠재력을 실현하라";
    private final String SAMPLE_AUTHOR6 = "윈스턴 처칠";
    private final String SAMPLE_CONTENT6 = "성공은 결론이 아니며 실패는 치명적인 것이 아니다. 중요한 것은 그 과정을 지속하는 용기다";

    @BeforeEach
    void setUp() {
        softAssertions = new SoftAssertions();
        wiseSayingService = new WiseSayingService(new WiseSayingMapRepository());
    }

    void setUpSampleWiseSaying() {
        addNewWiseSaying(SAMPLE_AUTHOR1, SAMPLE_CONTENT1);
        addNewWiseSaying(SAMPLE_AUTHOR2, SAMPLE_CONTENT2);
        addNewWiseSaying(SAMPLE_AUTHOR3, SAMPLE_CONTENT3);
        addNewWiseSaying(SAMPLE_AUTHOR4, SAMPLE_CONTENT4);
        addNewWiseSaying(SAMPLE_AUTHOR5, SAMPLE_CONTENT5);
        addNewWiseSaying(SAMPLE_AUTHOR6, SAMPLE_CONTENT6);
    }

    private WiseSaying addNewWiseSaying(final String author, final String content) {
        return wiseSayingService.addWiseSaying(craeteWiseSaying(author, content));
    }

    private WiseSaying craeteWiseSaying(final String author, final String content) {
        WiseSaying wiseSaying = new WiseSaying();
        wiseSaying.setAuthor(author);
        wiseSaying.setContent(content);
        return wiseSaying;
    }

    @Test
    @DisplayName("명언을 등록할 수 있다")
    void addSayingTest() {
        WiseSaying givenWiseSaying = addNewWiseSaying(SAMPLE_AUTHOR1, SAMPLE_CONTENT1);

        WiseSaying foundWiseSaying = wiseSayingService.addWiseSaying(givenWiseSaying);
        softAssertions.assertThat(foundWiseSaying.getAuthor()).isEqualTo(givenWiseSaying.getAuthor());
        softAssertions.assertThat(foundWiseSaying.getContent()).isEqualTo(givenWiseSaying.getContent());
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("명언을 ID로 검색할 수 있다")
    void findSayingTest() throws Exception {
        WiseSaying givenWiseSaying = addNewWiseSaying(SAMPLE_AUTHOR1, SAMPLE_CONTENT1);
        WiseSaying savedWiseSaying = wiseSayingService.addWiseSaying(givenWiseSaying);

        WiseSaying foundWiseSaying = wiseSayingService.findWiseSayingByID(savedWiseSaying.getId());
        softAssertions.assertThat(foundWiseSaying.getAuthor()).isEqualTo(givenWiseSaying.getAuthor());
        softAssertions.assertThat(foundWiseSaying.getContent()).isEqualTo(givenWiseSaying.getContent());
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("명언을 ID로 삭제할 수 있다")
    void deleteSayingTest() {
        WiseSaying givenWiseSaying = addNewWiseSaying(SAMPLE_AUTHOR1, SAMPLE_CONTENT1);
        WiseSaying savedWiseSaying = wiseSayingService.addWiseSaying(givenWiseSaying);

        wiseSayingService.deleteWiseSaying(savedWiseSaying.getId());
        softAssertions.assertThatThrownBy(() -> wiseSayingService.findWiseSayingByID(savedWiseSaying.getId()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("존재하지 않는 명언 ID를 조회했습니다.");
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("명언을 ID로 수정할 수 있다")
    void modifySayingTest() throws Exception {
        WiseSaying givenWiseSaying = addNewWiseSaying(SAMPLE_AUTHOR1, SAMPLE_CONTENT1);

        String expectedAuthor = "홍길동";
        String expectedContent = "현재와 자신을 사랑하라.";
        WiseSaying expectedWiseSaying = craeteWiseSaying(expectedAuthor, expectedContent);

        wiseSayingService.updateWiseSaying(givenWiseSaying.getId(), expectedWiseSaying);
        softAssertions.assertThat(wiseSayingService.findWiseSayingByID(givenWiseSaying.getId()))
                .usingRecursiveComparison().
                isEqualTo(expectedWiseSaying);
    }

    @Test
    @DisplayName("작가명으로 명언을 검색할 수 있다")
    void searchByAuthorTest() throws Exception {
        setUpSampleWiseSaying();
        String searchAuthorKeyword = "처칠";
        List<WiseSaying> expectedWiseSayingList = List.of(
                wiseSayingService.findWiseSayingByID(6L),
                wiseSayingService.findWiseSayingByID(5L)
        );

        List<WiseSaying> foundWiseSayingList = wiseSayingService.findWiseSayingByAuthor(searchAuthorKeyword, 1L)
                .getWiseSayingList();

        softAssertions.assertThat(foundWiseSayingList)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(expectedWiseSayingList);
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("명언의 내용으로 명언을 검색할 수 있다")
    void searchByContentTest() throws Exception {
        setUpSampleWiseSaying();
        String searchContentKeyword = "실현";
        List<WiseSaying> expectedWiseSayingList = List.of(
                wiseSayingService.findWiseSayingByID(5L),
                wiseSayingService.findWiseSayingByID(4L)
        );

        List<WiseSaying> foundWiseSayingList = wiseSayingService.findWiseSayingByContent(searchContentKeyword, 1L)
                .getWiseSayingList();

        softAssertions.assertThat(foundWiseSayingList)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(expectedWiseSayingList);
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("페이지 번호로 명언을 조회할 수 있다")
    void findPageWiseSayingTest() throws Exception {
        // GIVEN
        setUpSampleWiseSaying();
        List<WiseSaying> expectedWiseSayingList = new ArrayList<>();
        for (Long id = 6L; id > 1L; id--) {
            expectedWiseSayingList.add(wiseSayingService.findWiseSayingByID(id));
        }

        softAssertions.assertThat(wiseSayingService.findWiseSayingPage(1L).getWiseSayingList())
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(expectedWiseSayingList);
        softAssertions.assertAll();
    }
}