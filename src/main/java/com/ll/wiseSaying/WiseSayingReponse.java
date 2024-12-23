package com.ll.wiseSaying;

import java.util.ArrayList;
import java.util.List;

public class WiseSayingReponse {
    private Status status;
    private List<WiseSaying> wiseSayingList;
    private Long recentID;
    private Long currentPage;
    private Long totalPage;

    public WiseSayingReponse() {
        status = Status.FAILED;
        wiseSayingList = new ArrayList<>();
        recentID = 0L;
    }

    public WiseSayingReponse status(final Status status) {
        this.status = status;
        return this;
    }

    public WiseSayingReponse addWiseSayingList(final List<WiseSaying> wiseSayingList) {
        this.wiseSayingList = wiseSayingList;
        return this;
    }

    public WiseSayingReponse recentID(final Long recentID) {
        this.recentID = recentID;
        return this;
    }

    public WiseSayingReponse currentPage(final Long currentPage) {
        this.currentPage = currentPage;
        return this;
    }

    public WiseSayingReponse totalPage(final Long totalPage) {
        this.totalPage = totalPage;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public List<WiseSaying> getWiseSayingList() {
        return wiseSayingList;
    }

    public Long getRecentID() {
        return recentID;
    }

    public Long getCurrentPage() {
        return currentPage;
    }

    public Long getTotalPage() {
        return totalPage;
    }
}
