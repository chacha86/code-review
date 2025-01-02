package com.ll.wiseSaying.testDrivenDevelopment;

import java.util.List;

public class TddPage<T> {

    private final List<T> wiseSayingList;
    private final int totalPage;
    private final int currentPage;

    public TddPage(List<T> wiseSayingList, int totalPage, int currentPage) {
        this.wiseSayingList = wiseSayingList;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("번호 / 작가 / 명언\n");
        sb.append("----------------------\n");

        if (wiseSayingList.isEmpty() || currentPage>totalPage) {
            sb.append("검색 결과가 존재하지 않습니다.");
            return sb.toString();
        }

        for (T wiseSaying : wiseSayingList) {
            sb.append(wiseSaying).append("\n");
        }

        sb.append("----------------------\n");
        sb.append("페이지 :");

        for (int i=1; i<=totalPage; i++) {
            if (i == 1) {
                sb.append(" ");
            } else {
                sb.append(" / ");
            }

            if (i == currentPage) {
                sb.append("[").append(i).append("]");
            } else {
                sb.append(i);
            }
        }

        return sb.toString();
    }
}
