package com.programmers.devcourse.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.devcourse.model.Saying;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class JsonDBInputView {
    public static Map<Long, Saying> readSaying() throws IOException {
        String directoryPath = "db/wiseSaying/";
        File directory = new File(directoryPath);

        // 디렉토리 내 모든 .json 파일 가져오기
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".json"));
        Map<Long, Saying> sayingMap = new TreeMap<>(new Comparator<Long>() {
            @Override
            public int compare(Long o1, Long o2) {
                return o2.compareTo(o1);
            }
        });

        if (files != null) {
            for (File file : files) {
                ObjectMapper objectMapper = new ObjectMapper();

                // JSON 파일을 Map<String, Object> 형태로 읽기
                Map<String, Object> rawMap = objectMapper.readValue(file, Map.class);

                // 파일에서 "id", "author", "content" 필드 읽기
                if (rawMap.containsKey("id") && rawMap.containsKey("author") && rawMap.containsKey("content")) {
                    String id = rawMap.get("id").toString(); // id는 문자열로 읽히므로 Long으로 변환
                    String author = (String) rawMap.get("author");
                    String content = (String) rawMap.get("content");

                    // Saying 객체 생성
                    Saying saying = new Saying(author, content);

                    // Long 타입으로 변환하여 맵에 저장
                    sayingMap.put(Long.parseLong(id), saying);
                }
            }
        }
        return sayingMap;
    }

    public static Long readLastId() throws IOException {
        String directoryPath = "db/wiseSaying/lastId.txt";
        File file = new File(directoryPath);

        // 파일이 존재하지 않으면 예외를 발생시킬 수 있습니다.
        if (!file.exists()) {
            return 1L;
        }

        // BufferedReader를 사용하여 파일 읽기
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine(); // 파일에서 첫 번째 줄 읽기
            if (line != null) {
                return Long.parseLong(line.trim()); // 읽은 값을 Long으로 변환
            } else {
                throw new IOException("File is empty: " + directoryPath);
            }
        }
    }
}
