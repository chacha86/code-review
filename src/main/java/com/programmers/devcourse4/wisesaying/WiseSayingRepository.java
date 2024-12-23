package com.programmers.devcourse4.wisesaying;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class WiseSayingRepository {

    //파일 저장
    public void save(String jsonData, String filePath) {

        // 파일에 JSON 데이터 쓰기
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(jsonData);
        } catch (IOException e) {
            System.err.println("파일 생성 중 오류 발생: " + e.getMessage());
        }
    }

    // 파일 삭제
    public boolean delete(String filePath) {
        File file = new File(filePath);

        // 파일이 존재하면 삭제
        if (file.exists()) {
            return file.delete(); // 파일 삭제 시 true
        }
        return false; // 파일이 존재하지 않으면 false
    }

    //JSON 데이터 1개 읽기
    public JSONObject findById(String filePath) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader(filePath);
        Object obj = parser.parse(reader);

        return (JSONObject)obj;
    }

    //JSON 데이터 모두 읽기
    public File[] findAll(String directoryPath){
        File folder = new File(directoryPath);
        return folder.listFiles((dir, name) -> name.endsWith(".json"));
    }

    //빌드 파일 저장
    public void saveBuild(String filePath, JSONArray jsonArray) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(jsonArray.toJSONString()); // JSONArray를 JSON 문자열로 변환 후 파일에 작성
            fileWriter.flush();
        } catch (IOException e) {
            System.err.println("JSON 파일 저장 중 오류가 발생했습니다: " + e.getMessage());
            throw e;
        }
    }

}
