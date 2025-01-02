package com.ll.wisesaying.repository;

import static com.ll.wisesaying.constant.Constant.*;
import static com.ll.wisesaying.util.InputUtil.*;
import static com.ll.wisesaying.util.OutputUtil.*;
import static java.util.stream.Collectors.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.ll.wisesaying.domain.Search;
import com.ll.wisesaying.domain.WiseSaying;
import com.ll.wisesaying.exception.IndexException;
import com.ll.wisesaying.exception.InputException;

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
        Files.writeString(getPath(wiseSaying.getId()), content);
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
            throw new IndexException(idx + IDX_NOT_EXIST);
        }
    }

    public List<WiseSaying> findWiseSayings(Search search) {
        List<WiseSaying> wiseSayings = null;

        if(search.keywordType() == null && search.keyword() == null) {
            wiseSayings = new ArrayList<>(db.values());

            return getSubWiseSayings(wiseSayings, search.page());
        }

        if (search.keywordType().equals(ENG_CONTENT)) {
            wiseSayings = db.values().stream()
                .filter(wiseSaying -> wiseSaying.getContent().contains(search.keyword()))
                .collect(toList());
        }

        if (search.keywordType().equals(ENG_AUTHOR)) {
            wiseSayings = db.values().stream()
                .filter(wiseSaying -> wiseSaying.getAuthor().contains(search.keyword()))
                .collect(toList());
        }

        if (wiseSayings == null || wiseSayings.isEmpty()) {
            throw new InputException(NOT_EXIST);
        }

        return getSubWiseSayings(wiseSayings, search.page());
    }

    public List<WiseSaying> getSubWiseSayings(List<WiseSaying> wiseSayings, long page) {
        int start = (int) ((page - 1) * PAGE_SIZE);
        int end = Math.min(start + PAGE_SIZE - 1, db.size() - 1);

        return new ArrayList<>(wiseSayings).subList(start, end + 1);
    }
}
