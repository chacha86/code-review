package com.ll.wisesaying.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class InputUtil {
    public static final BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    public static final String END = "종료";
    public static final String ENROLL = "등록";
    public static final String LIST = "목록";
    public static final String DELETE = "삭제";
    public static final String EDIT = "수정";
    public static final String BUILD = "빌드";
    public static final String CMD_SEPARATOR = "\\?|id|=";

    public static String inputMessage() throws IOException {
        return bf.readLine();
    }

    public static int getCmdIdx(String cmd) {
        String[] tokens = cmd.split(CMD_SEPARATOR);
        tokens = Arrays.stream(tokens)
            .filter(token -> !token.isEmpty())
            .toArray(String[]::new);

        Validator.validateCmd(tokens);

        int idx = Integer.valueOf(tokens[tokens.length - 1]);
        return idx;
    }
}
