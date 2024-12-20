package com.ll.wiseSaying;

import java.util.*;

public class WiseSayingRepository {
    private final Map<Integer, WiseSaying> map = new LinkedHashMap<>();

    public int getMapSize() {
        return map.size();
    }
    public WiseSaying find(int num) {
        return map.get(num);
    }
    public void create(int n, String author, String wiseSaying) {
        map.put(n, new WiseSaying(n, author, wiseSaying));
    }

    public List<Integer> keySet() {
        return(new ArrayList<>(map.keySet()));
    }
    public void update(int contentNum, String author, String wiseSaying) {
        WiseSaying data = map.get(contentNum);
        data.setAuthor(author);
        data.setWiseSaying(wiseSaying);
    }
    public boolean delete(int contentNum) {
        if(map.get(contentNum) == null)
            return false;
        else {
            map.remove(contentNum);
            return true;
        }
    }
}
