package com.ll.wiseSaying;

public class ListCommand {
    private final String keywordType;
    private final String keyword;
    private final int page;

    // keyword 검색타입, author = 검색어(13단계), page = 페이징(14단계 페이징)
    public ListCommand(String cmd) {
        String[] params = parseParams(cmd);
        this.keywordType = findParam(params, "keywordType");
        this.keyword = findParam(params, "keyword");
        String pageStr = findParam(params, "page");
        this.page = pageStr.isEmpty() ? 1 : Integer.parseInt(pageStr); // 페이지 없을때 1페이지고정
    }

    private String[] parseParams(String cmd) {
        if(cmd.contains("?")) {
            // url에서 파라미터 나누기와 동일한 과정입니다.(명령어 파싱 ex 목록?keywordType=author&keyword=name)

            return cmd.substring(cmd.indexOf("?") + 1).split("&");
        }
        return new String[0];
    }

    private String findParam(String[] params, String paramName) {
        for(String param : params) {
            String[] keyValue = param.split("=");
            if(keyValue[0].equals(paramName)) {
                return keyValue[1];
            }
        }
        return "";
    }

    public boolean hasSearch() {
        return !keywordType.isEmpty() && !keyword.isEmpty();
    }

    public String getKeywordType() { return keywordType; }
    public String getKeyword() { return keyword; }
    public int getPage() { return page; }
}
