package com.programmers.devcourse.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.devcourse.model.Saying;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class JsonDBOutputView {

    public static void saveSaying(long boardNumber, Saying saying) throws IOException {
        String folderPath = "db/wiseSaying/";
        String filePath = folderPath + boardNumber + ".json";

        // 디렉토리 확인 및 생성
        File directory = new File(folderPath);
        if (!directory.exists()) {
            directory.mkdirs(); // 디렉토리 생성
        }

        // JSON 데이터 생성
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", boardNumber);
        map.put("content", saying.getContents());
        map.put("author", saying.getAuthor());
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(map);

        // 파일에 JSON 저장
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(json);
            fileWriter.flush(); // 데이터 강제 저장
        }
    }
    public static void lastSayingNumber(long lastSayingNumber) throws IOException {
        // Map -> JSON 변환
        String folderPath = "db/wiseSaying/";
        String filePath = folderPath + "lastId.txt";
        ObjectMapper objectMapper = new ObjectMapper();

        // 디렉토리 확인 및 생성
        File directory = new File(folderPath);
        if (!directory.exists()) {
            directory.mkdirs(); // 디렉토리 생성
        }

        // JSON 데이터 생성
        String json = objectMapper.writeValueAsString(lastSayingNumber);

        // 파일에 JSON 저장
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(json);
            fileWriter.flush(); // 데이터 강제 저장
        }
    }
}
