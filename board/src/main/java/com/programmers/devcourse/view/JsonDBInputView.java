package com.programmers.devcourse.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.devcourse.model.Saying;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class JsonDBInputView {
    public static Map<Long, Saying> readSaying() throws IOException {
        String directoryPath = "db/wiseSaying/";
        File directory = new File(directoryPath);

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

                List<Map<String, Object>> rawMap = objectMapper.readValue(file, List.class);

                for (Map<String, Object> stringObjectMap : rawMap) {
                    String id = stringObjectMap.get("id").toString();
                    String author = (String) stringObjectMap.get("author");
                    String content = (String) stringObjectMap.get("content");

                    Saying saying = new Saying(author, content);

                    sayingMap.put(Long.parseLong(id), saying);
                }
            }
        }
        return sayingMap;
    }

    public static Long readLastId() throws IOException {
        String directoryPath = "db/wiseSaying/lastId.txt";
        File file = new File(directoryPath);

        if (!file.exists()) {
            return 1L;
        }

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
