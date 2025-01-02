package com.ll.wisesaying.domain;

public record Search(
    String keywordType,
    String keyword,
    long page
) {
    public static Search of(String keyWordType, String keyword, long page) {
        return new Search(keyWordType, keyword, page);
    }

    public static Search of(String keyWordType, String keyword) {
        return new Search(keyWordType, keyword, 1L);
    }

    public static Search of(long page) {
        return new Search(null, null, page);
    }

    public static Search of() {
        return new Search(null, null, 1L);
    }
}
