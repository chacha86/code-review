package com.ll.wisesaying.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import com.ll.wisesaying.domain.Search;

public class InputUtil {
    public static final BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    public static final String END = "종료";
    public static final String ENROLL = "등록";
    public static final String LIST = "목록";
    public static final String DELETE = "삭제";
    public static final String EDIT = "수정";
    public static final String BUILD = "빌드";
    public static final String KEYWORD_TYPE = "검색타입 : ";
    public static final String KEYWORD = "검색어 : ";
    public static final String ENG_CONTENT = "content";
    public static final String ENG_AUTHOR = "author";
    public static final String CMD_SEPARATOR = "\\?|id|=";
    public static final String SEARCH_SEPARATOR = "\\?|keywordType|&|keyword|=";

    public static String inputMessage() throws IOException {
        return bf.readLine();
    }

    public static int getCmdIdx(String cmd) {
        String[] tokens = getTokens(cmd, CMD_SEPARATOR);

        Validator.validateCmd(tokens);

        int idx = Integer.valueOf(tokens[tokens.length - 1]);
        return idx;
    }

    public static Search getSearch(String cmd) {
        String[] tokens = getTokens(cmd, SEARCH_SEPARATOR);

        Validator.validateSearch(tokens);

        return Search.builder()
            .keywordType(tokens[1])
            .keyword(tokens[2])
            .build();
    }

    private static String[] getTokens(String cmd, String cmdSeparator) {
        String[] tokens = cmd.split(cmdSeparator);
        tokens = Arrays.stream(tokens)
            .filter(token -> !token.isEmpty())
            .toArray(String[]::new);
        return tokens;
    }
}
