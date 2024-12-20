package com.ll.wisesaying.repository;

import static com.ll.wisesaying.constant.Constant.*;
import static com.ll.wisesaying.util.OutputUtil.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.ll.wisesaying.domain.WiseSaying;
import com.ll.wisesaying.exception.IndexException;

public class WiseSayingRepository {

    public final Map<Integer, WiseSaying> db = new TreeMap<>(Comparator.reverseOrder());
    private int idx;

    public WiseSayingRepository() throws IOException {
        init();
    }

    public void init() throws IOException {
        checkFileState();
        idx = getLastIdx();

        for (int i = 1; i <= idx; i++) {
            db.put(i, getWiseSaying(i));
        }
    }

    private void checkFileState() throws IOException {
        Path path = getPath(DB_FOLDER);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        Path lastPath = getPath(DB_FOLDER + LAST_ID_FILE);
        if (!Files.exists(lastPath)) {
            Files.writeString(lastPath, initIdx);
        }
    }

    public void saveAllWiseSayings(List<WiseSaying> wiseSayings) throws IOException {
        String content = objectMapper.writeValueAsString(wiseSayings);
        Files.writeString(getPath(DB_FOLDER + ALL_FILE), content);
    }

    public void saveWiseSaying(WiseSaying wiseSaying) throws IOException {
        String content = objectMapper.writeValueAsString(wiseSaying);
        Files.writeString(getPath(wiseSaying.getIdx()), content);
    }

    public WiseSaying getWiseSaying(int idx) throws IOException {
        if (!Files.exists(getPath(idx))) {
            throw new FileNotFoundException();
        }

        String json = Files.readString(getPath(idx));
        return objectMapper.readValue(json, WiseSaying.class);
    }

    public int getLastIdx() throws IOException {
        String lastId = Files.readString(getPath(DB_FOLDER + LAST_ID_FILE)).trim();
        return lastId.isEmpty() ? 0 : Integer.parseInt(lastId);
    }

    public void saveLastIdx(int lastId) throws IOException {
        Files.writeString(Paths.get(DB_FOLDER + LAST_ID_FILE), String.valueOf(lastId));
    }

    public int getNextIdx() {
        return ++idx;
    }

    public int getIdx() {
        return idx;
    }

    public Map<Integer, WiseSaying> getDB() {
        return db;
    }

    public void containsWiseSaying(int idx) {
        if (!db.containsKey(idx)) {
            throw new IndexException(idx + NOT_EXIST);
        }
    }
}
