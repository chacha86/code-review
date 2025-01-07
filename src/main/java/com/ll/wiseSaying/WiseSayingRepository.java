package com.ll.wiseSaying;

public interface WiseSayingRepository {
    WiseSaying save (WiseSaying wiseSaying);
    WiseSaying findById(Long id) throws Exception;
    long delete(Long wiseSayingID);
    WiseSaying update(Long wiseSayingID, WiseSaying wiseSaying);
    WiseSayingPage findByAuthorName(String searchAuthorKeyword, Long page, int pageSize);
    WiseSayingPage findByContent(String searchContentKeyword, Long page, int pageSize);
    WiseSayingPage findPage(Long page, int pageSize);
    String build();
}
