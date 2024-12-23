package com.programmers.devcourse4.wisesaying;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class WiseSayingController {

    private final WiseSayingService wiseSayingService = new WiseSayingService();

    public void mapping(String command) throws IOException, ParseException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        if (command.equals("등록")) {

            System.out.print("명언: ");
            String saying = bufferedReader.readLine();

            System.out.print("작가: ");
            String author = bufferedReader.readLine();

            int number = wiseSayingService.save(saying, author);

            System.out.println(number + "번 명언이 등록되었습니다.");
        }


        if (command.contains("삭제")) {
            int id = command.charAt(command.length() - 1) - '0';
            if(wiseSayingService.delete(id)){
                System.out.println(id + "번 명언이 삭제되었습니다.");
            } else
                System.out.println(id + "번 명언은 존재하지 않습니다.");
        }

        if(command.equals("종료")) {
            // 마지막id 저장하는 .txt파일 저장
            try (FileWriter fileWriter = new FileWriter("/Users/hj/Desktop/devcourse/devcourse4/src/main/resources/db/wiseSaying/lastId.txt")) {
                fileWriter.write(String.valueOf(WiseSaying.getCount()));
            } catch (IOException e) {
                System.err.println("파일 생성 중 오류 발생: " + e.getMessage());
            }

            System.out.println();
            System.out.println("프로그램 다시 시작...");
            System.out.println();
            System.out.println("== 명언 앱 ==");
        }

        if (command.contains("수정")) {
            int id = command.charAt(command.length() - 1) - '0';

            // 기존 명언 정보 읽기
            JSONObject oldJsonObject = wiseSayingService.findById(id);

            if (oldJsonObject != null) {
                //이전 JSON
                String oldSaying = (String)oldJsonObject.get("content");
                String oldAuthor = (String)oldJsonObject.get("author");

                System.out.println("명언(기존): " + oldSaying);
                System.out.print("명언: ");
                String newSaying = bufferedReader.readLine();

                System.out.println("작가(기존): " + oldAuthor);
                System.out.print("작가: ");
                String newAuthor = bufferedReader.readLine();

                wiseSayingService.update(id, newSaying, newAuthor);

            } else {
                System.out.println(id + "번 명언은 존재하지 않습니다.");
            }
        }

        if (command.equals("목록")) {
            JSONArray jsonArray = wiseSayingService.findAll();

            // JSON 배열을 내림차순 정렬
            jsonArray.sort((o1, o2) -> {
                int id1 = Integer.parseInt(((JSONObject) o1).get("id").toString());
                int id2 = Integer.parseInt(((JSONObject) o2).get("id").toString());
                return Integer.compare(id2, id1); // 내림차순 정렬
            });

            System.out.println("번호 / 작가 / 명언");
            System.out.println("----------------------");

            // 정렬된 JSON 데이터 출력
            for (Object obj : jsonArray) {
                JSONObject wiseSaying = (JSONObject) obj;
                int id = Integer.parseInt(wiseSaying.get("id").toString());
                String author = (String) wiseSaying.get("author");
                String content = (String) wiseSaying.get("content");
                System.out.println(id + " / " + author + " / " + content);
            }
        }

        if (command.equals("빌드")) {
            wiseSayingService.build();
            System.out.println("data.json 파일의 내용이 갱신되었습니다.");
        }

    }
}
