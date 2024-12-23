package com.programmers.devcourse4.wisesaying;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WiseSayingService {

    private final WiseSayingRepository wiseSayingRepository = new WiseSayingRepository();

    // 명언 등록
    public int save(String saying, String author) {
        WiseSaying wiseSaying = new WiseSaying(saying, author);

        // JSON 객체 생성
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", wiseSaying.getId());
        jsonObject.put("content", saying);
        jsonObject.put("author", author);

        // 파일 경로 설정
        String filePath = "/Users/hj/Desktop/devcourse/devcourse4/src/main/resources/db/wiseSaying/" + wiseSaying.getId() + ".json";
        wiseSayingRepository.save(jsonObject.toString(), filePath);

        return wiseSaying.getId();
    }


    //명언 삭제
    public boolean delete(int id) {
        String filePath = "/Users/hj/Desktop/devcourse/devcourse4/src/main/resources/db/wiseSaying/" + id + ".json";
        return wiseSayingRepository.delete(filePath);
    }

    //명언 수정
    public void update(int id, String saying, String author) {

        // JSON 객체 생성
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("content", saying);
        jsonObject.put("author", author);

        // 파일 경로 설정
        String filePath = "/Users/hj/Desktop/devcourse/devcourse4/src/main/resources/db/wiseSaying/" + id + ".json";
        wiseSayingRepository.save(jsonObject.toString(), filePath);
    }

    //명언 1개 조회
    public JSONObject findById(int id){
        String filePath = "/Users/hj/Desktop/devcourse/devcourse4/src/main/resources/db/wiseSaying/" + id + ".json";
        try {
            return wiseSayingRepository.findById(filePath);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    //명언 모두 조회
    public JSONArray findAll() throws IOException, ParseException {
        String directoryPath = "/Users/hj/Desktop/devcourse/devcourse4/src/main/resources/db/wiseSaying";
        File[] files = wiseSayingRepository.findAll(directoryPath);
        JSONArray jsonArray = new JSONArray();

        // 모든 JSON 파일을 읽어 JSONArray에 추가
        for (File file : files) {
            JSONObject wiseSaying = wiseSayingRepository.findById(directoryPath + "/" + file.getName());
            jsonArray.add(wiseSaying);
        }

        return jsonArray;
    }


    //빌드
    public void build() throws IOException, ParseException {
        String directoryPath = "/Users/hj/Desktop/devcourse/devcourse4/src/main/resources/db/wiseSaying";
        File[] files = wiseSayingRepository.findAll(directoryPath);
        JSONArray jsonArray = new JSONArray();

        // 모든 JSON 파일을 읽어 JSONArray에 추가
        for (File file : files) {
            JSONObject wiseSaying = wiseSayingRepository.findById(directoryPath + "/" + file.getName());
            jsonArray.add(wiseSaying);
        }

        wiseSayingRepository.saveBuild(directoryPath + "/" + "data.json", jsonArray);
    }
}
