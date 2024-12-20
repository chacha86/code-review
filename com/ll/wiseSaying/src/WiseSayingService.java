package com.ll.wiseSaying.src;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.json.simple.JSONArray;
public class WiseSayingService {
    WiseSayingRepository wiseSayingRepository = new WiseSayingRepository();
    static String dirName = "db/wiseSaying/";

    /*
    * @return 생성된 명언의 id
    * */
    public int addWiseSaying(String content, String author) {
        WiseSaying wiseSaying = new WiseSaying(++wiseSayingRepository.lastId, content, author);
        wiseSayingRepository.addWiseSaying(wiseSaying);
        saveWiseSayingAsFile(wiseSaying);
        return wiseSaying.id;
    }

    public void removeWiseSaying(int id) {
        if(wiseSayingRepository.getWiseSaying(id) != null){
            wiseSayingRepository.removeWiseSaying(id);
        } else {
            throw new IllegalArgumentException(String.format("%d번 명언은 존재하지 않습니다.", id));
        }
    }

    public void updateWiseSaying(int id, String content, String author) {
        if(wiseSayingRepository.getWiseSaying(id) != null){
            wiseSayingRepository.updateWiseSaying(id, content, author);
        } else {
            throw new IllegalArgumentException(String.format("%d번 명언은 존재하지 않습니다.", id));
        }
    }

    public String getAllWiseSaying() {
        StringBuilder sb = new StringBuilder();
        Map<Integer, WiseSaying> wiseSayingList = wiseSayingRepository.wiseSayingList;
        String result = "";
        for (int i : wiseSayingList.keySet()) {
            WiseSaying ws = wiseSayingList.get(i);
            result = sb.append(ws.id).append(" / ").append(ws.author).append(" / ").append(ws.content).append("\n").toString();
        }
        return result;
    }

    public void buildJson() {
        String fileName = dirName + "data.json";
        Map<Integer, WiseSaying> wiseSayingList = wiseSayingRepository.wiseSayingList;
        JSONArray jsonList = new JSONArray();
        for (int i : wiseSayingList.keySet()) {
            jsonList.add(wiseSayingList.get(i).toJson());
        }
        saveFile(fileName, jsonList.toJSONString());
    }



    public void saveWiseSayingAsFile(WiseSaying wiseSaying) {
        String fileName = String.format("%s%d.json", dirName, wiseSaying.id);
        saveFile(fileName, wiseSaying.toJson().toJSONString());
    }
    public void saveFile(String fileName, String content) {
        try {
            FileWriter file = new FileWriter(fileName);
            file.write(content);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveLastId() {
        String fileName = dirName + "lastId.txt";
        saveFile(fileName, String.valueOf(wiseSayingRepository.lastId));
    }
}
