package com.ll.wisesaying.constant;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Constant {

    public static final ObjectMapper objectMapper = new ObjectMapper();
    public static final String initIdx = "0";
    public static final String SEPARATOR = " / ";
    public static final String LEFT_BRACKET = "[";
    public static final String RIGHT_BRACKET = "]";
    public static final String TXT = ".txt";
    public static final String JSON = ".json";
    public static final String DB_FOLDER = "db/wiseSaying/";
    public static final String LAST_ID_FILE = "lastId" + TXT;
    public static final String ALL_FILE = "data" + JSON;
    public static final int PAGE_SIZE = 5;
    public static final int PAGE_NUM = 2;

    public static Path getPath(int idx) {
        String path = DB_FOLDER + idx + JSON;
        return Paths.get(path);
    }

    public static Path getPath(String path) {
        return Paths.get(path);
    }
}
