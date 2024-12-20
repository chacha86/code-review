package com.ll.wiseSaying;

import java.util.List;

public class PageResponse<T> {
    private List<T> items;
    private int currentPage;
    private int totalPages;
    private static final int PAGE_SIZE = 5;

    public PageResponse(List<T> items, int currentPage, int totalPages) {
        this.items = items;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }

    public List<T> getItems() {
        return items;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public static int getPageSize() {
        return PAGE_SIZE;
    }
}