package com.ll.wiseSaying.src;
import java.util.HashMap;
import java.util.Map;

public class WiseSayingRepository {
    public Map<Integer, WiseSaying> wiseSayingList = new HashMap<>();
    public int lastId = 0;

    public void addWiseSaying(WiseSaying wiseSaying) {
        wiseSayingList.put(wiseSaying.id, wiseSaying);
    }

    public void removeWiseSaying(int id) {
        wiseSayingList.remove(id);
    }

    public WiseSaying getWiseSaying(int id) {
        return wiseSayingList.get(id);
    }

    public Map<Integer, WiseSaying> getAllWiseSayings() {
        return wiseSayingList;
    }

    public void updateWiseSaying(int id, String quote, String author) {
        wiseSayingList.put(id, new WiseSaying(id, quote, author));
    }

    public int getLastId() {
        return lastId;
    }
}
