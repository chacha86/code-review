package com.ll.wiseSaying;

import java.util.ArrayList;
import java.util.List;

public class WiseSayingPage {
    private List<WiseSaying> wiseSayingList;
    private Long currentPage;
    private Long totalPage;

    public WiseSayingPage() {
        wiseSayingList = new ArrayList<>();
        currentPage = 0L;
        totalPage = 0L;
    }

    public List<WiseSaying> getWiseSayingList() {
        return wiseSayingList;
    }

    public WiseSayingPage addWiseSayingList(List<WiseSaying> wiseSayingList) {
        this.wiseSayingList = wiseSayingList;
        return this;
    }

    public Long getCurrentPage() {
        return currentPage;
    }

    public WiseSayingPage currentPage(final long currentPage) {
        this.currentPage = currentPage;
        return this;
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public WiseSayingPage totalPage(final long maxPage) {
        this.totalPage = maxPage;
        return this;
    }
}
