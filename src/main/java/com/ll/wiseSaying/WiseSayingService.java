package com.ll.wiseSaying;

public class WiseSayingService {
    private static final int DEFAULT_PAGE_SIZE = 5;

    private WiseSayingRepository wiseSayingRepository;

    public WiseSayingService(final WiseSayingRepository wiseSayingRepository) {
        this.wiseSayingRepository = wiseSayingRepository;
    }

    public WiseSaying addWiseSaying(final WiseSaying wiseSaying) {
        return wiseSayingRepository.save(wiseSaying);
    }

    public WiseSaying findWiseSayingByID(final Long wiseSayingID) throws Exception {
        return wiseSayingRepository.findById(wiseSayingID);
    }

    public long deleteWiseSaying(final Long wiseSayingID) {
        wiseSayingRepository.delete(wiseSayingID);
        return wiseSayingID;
    }

    public WiseSaying updateWiseSaying(final Long wiseSayingID, final WiseSaying wiseSaying) {
        return wiseSayingRepository.update(wiseSayingID, wiseSaying);
    }

    public WiseSayingPage findWiseSayingByAuthor(final String searchAuthorKeyword, final Long page) {
        return findWiseSayingByAuthor(searchAuthorKeyword, page, DEFAULT_PAGE_SIZE);
    }

    public WiseSayingPage findWiseSayingByAuthor(final String searchAuthorKeyword, final Long page, final int pageSize) {
        return wiseSayingRepository.findByAuthorName(searchAuthorKeyword, page, pageSize);
    }

    public WiseSayingPage findWiseSayingByContent(final String searchContent, final Long page) {
        return findWiseSayingByContent(searchContent, page, DEFAULT_PAGE_SIZE);
    }

    public WiseSayingPage findWiseSayingByContent(final String searchContent, final Long page, final int pageSize) {
        return wiseSayingRepository.findByContent(searchContent, page, pageSize);
    }

    public WiseSayingPage findWiseSayingPage(final Long page) {
        return findWiseSayingPage(page, DEFAULT_PAGE_SIZE);
    }

    public WiseSayingPage findWiseSayingPage(final Long page, final int pageSize) {
        return wiseSayingRepository.findPage(page, pageSize);
    }

    public String build() {
        return wiseSayingRepository.build();
    }
}
