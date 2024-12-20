package com.ll.wisesaying.util;

import static com.ll.wisesaying.util.OutputUtil.*;
import static com.ll.wisesaying.util.Validator.*;
import static java.lang.Long.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import com.ll.wisesaying.domain.Search;
import com.ll.wisesaying.exception.InputException;

public class InputUtil {
    public static final BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    public static final String END = "종료";
    public static final String ENROLL = "등록";
    public static final String LIST = "목록";
    public static final String DELETE = "삭제";
    public static final String EDIT = "수정";
    public static final String BUILD = "빌드";
    public static final String ENG_CONTENT = "content";
    public static final String ENG_AUTHOR = "author";
    public static final String CMD_SEPARATOR = "\\?|id|=";
    public static final String SEARCH_SEPARATOR = "\\?|keywordType|&|keyword|page|=";

    public static String inputMessage() throws IOException {
        return bf.readLine();
    }

    public static int getCmdIdx(String cmd) {
        String[] tokens = getTokens(cmd, CMD_SEPARATOR);

        validateCmd(tokens);

        int idx = Integer.valueOf(tokens[tokens.length - 1]);
        return idx;
    }

    public static Search getSearch(String cmd) {
        String[] tokens = getTokens(cmd, SEARCH_SEPARATOR);

        validateCmd(tokens);
        validatePage(tokens);

        if (tokens.length == 2) {
            return Search.of(valueOf(tokens[1]));
        }

        if (tokens.length == 3) {
            return Search.of(tokens[1], tokens[2]);
        }

        if (tokens.length == 4) {
            return Search.of(tokens[1], tokens[2], valueOf(tokens[3]));
        }

        throw new InputException(ILLEGAL_CMD);
    }

    private static String[] getTokens(String cmd, String cmdSeparator) {
        String[] tokens = cmd.split(cmdSeparator);
        tokens = Arrays.stream(tokens)
            .filter(token -> !token.isEmpty())
            .toArray(String[]::new);
        return tokens;
    }
}
