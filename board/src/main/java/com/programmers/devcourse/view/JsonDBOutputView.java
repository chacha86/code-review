package com.programmers.devcourse.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.devcourse.model.Board;
import com.programmers.devcourse.model.Saying;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class JsonDBOutputView {
    public static void saveSaying(Board board) throws IOException {
        String folderPath = "db/wiseSaying/";
        String filePath = folderPath + "data.json";

        // 디렉토리 확인 및 생성
        File directory = new File(folderPath);
        if (!directory.exists()) {
            boolean dirCreated = directory.mkdirs(); // 디렉토리 생성
            if (!dirCreated) {
                throw new IOException("디렉토리 생성에 실패했습니다.");
            }
        }

        // JSON 데이터 생성
        LinkedList<Map<String, Object>> list = new LinkedList<>();
        Map<Long, Saying> elementsMap = board.getElementsMap();

        // Map<Long, Saying>을 LinkedList로 변환
        for (Map.Entry<Long, Saying> sayingEntry : elementsMap.entrySet()) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", sayingEntry.getKey());
            map.put("content", sayingEntry.getValue().getContents());
            map.put("author", sayingEntry.getValue().getAuthor());
            list.addLast(map); // 순서를 그대로 유지하려면 addLast 사용
        }

        // ObjectMapper로 LinkedList를 JSON 문자열로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(list);

        // 파일에 JSON 저장 (덮어쓰기 처리)
        try (FileWriter fileWriter = new FileWriter(filePath, false)) { // 두 번째 인자를 false로 설정하여 덮어쓰기
            fileWriter.write(json);
            fileWriter.flush(); // 데이터 강제 저장
        } catch (IOException e) {
            throw new IOException("파일 저장 중 오류 발생: " + e.getMessage());
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
