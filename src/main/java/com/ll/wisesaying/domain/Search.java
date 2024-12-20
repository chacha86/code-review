package com.ll.wisesaying.domain;

public record Search(
    String keywordType,
    String keyword
) {
    public static class Builder {
        private String keywordType;
        private String keyword;

        public Builder keywordType(String keywordType) {
            this.keywordType = keywordType;
            return this;
        }

        public Builder keyword(String keyword) {
            this.keyword = keyword;
            return this;
        }

        public Search build() {
            return new Search(keywordType, keyword);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
