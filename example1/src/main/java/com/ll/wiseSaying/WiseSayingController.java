package com.ll.wiseSaying;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class WiseSayingController {

    private final WiseSayingService service;
    private String wiseSaying, author;
    private int num;
    private final String directoryPath = "db/wiseSaying";  // JSON 파일이 있는 디렉토리 경로
    private final String lastIdFilePath = directoryPath + "/lastId.txt"; // lastId.txt 파일 경로

    public WiseSayingController() {
        this.service = new WiseSayingService();
        startRead();
    }

    public void start(String command) {
        String[] split = null;
        if (command.matches(".*[^a-zA-Z0-9가-힣].*")) { // 특수문자 확인
            split = command.split("[^a-zA-Z0-9가-힣]"); // 특수문자로 split
            command = split[0];
        }
        switch (command) {
            case "등록" :
                create();
                break;
            case "목록" :
                if(split == null)
                    read(1);
                else if(split.length == 5)
                    if(split[1].equals("keywordType") && split[3].equals("keyword"))
                        readKeyword(split[2], split[4]);
                else if(split.length == 3)
                    if(split[1].equals("page"))
                        read(Integer.parseInt(split[2]));
                break;
            case "삭제" :
                if(split != null)
                    delete(Integer.parseInt(split[2]));
                else System.out.println("삭제?id=1과 같은 방식으로 입력해주세요!");
                break;
            case "수정" :
                if(split != null)
                    update(Integer.parseInt(split[2]));
                else System.out.println("수정?id=1과 같은 방식으로 입력해주세요!");
                break;
            case "빌드" :
                build();
                break;
        }
    }

    private void startRead() { // 시작할때 DB 읽기

        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs(); // 디렉터리 생성
        }

        File lastIdFile = new File(lastIdFilePath);
        if(lastIdFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(lastIdFilePath))) {
                String line = br.readLine();
                if (line != null) {
                    num = Integer.parseInt(line.trim()) + 1;  // lastId.txt에서 n 값을 읽어오기
                }
            } catch (IOException e) {
                System.out.println("lastId.txt 파일을 읽는 중 오류가 발생했습니다.");
                e.printStackTrace();
                return;  // 파일을 읽을 수 없으면 프로그램 종료
            }
        }

        // JSON 파일을 읽고 처리하는 부분
        for (int i = 1; i < num; i++) {
            String filePath = directoryPath + "/" + i + ".json";
            File file = new File(filePath);

            // 파일이 존재하는지 확인
            if (!file.exists())
                continue;

            try {
                // JSON 파일 읽기
                FileReader reader = new FileReader(filePath);
                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(reader);

                // JSON 파일에서 필요한 데이터 추출
                author = (String) jsonObject.get("author");
                int id = ((Long) jsonObject.get("id")).intValue();
                wiseSaying = (String) jsonObject.get("content");

                // 데이터 출력 (필요한 작업 수행)
                service.create(id, author, wiseSaying);

                reader.close();  // 리소스 닫기

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

   }

    private void create() {   // 등록
        Scanner sc = new Scanner(System.in);
        System.out.print("명언 : ");
        wiseSaying = sc.nextLine();
        System.out.print("작가 : ");
        author = sc.nextLine();
        service.create(num, author, wiseSaying);
        System.out.println(num + "번 명언이 등록되었습니다.");

        File file = new File(lastIdFilePath);
        try {
            file.createNewFile();

            // FileWriter는 기본적으로 파일을 덮어쓰므로 두 번째 인자 없이 사용하면 기존 내용을 지운 후 새로 작성
            FileWriter fw = new FileWriter(file, false);  // false로 설정하면 덮어쓰기 모드
            PrintWriter writer = new PrintWriter(fw);

            writer.print(num);  // n-1 값을 넣기

            // PrintWriter close
            writer.close();
            System.out.println("File written with Last value at lastId.txt: " + (num));

        } catch (IOException e) {
            e.printStackTrace();
        }

        saveToJson(num);
        num++;

    }

    private void saveToJson(int n) {
        WiseSaying jsonData = service.find(n);
        if(jsonData != null) {
            JSONObject obj = new JSONObject();
            obj.put("id", jsonData.getNumber());
            obj.put("content", jsonData.getWiseSaying());
            obj.put("author", jsonData.getAuthor());

            try {
                FileWriter file2 = new FileWriter(directoryPath + "/" + jsonData.getNumber() + ".json");
                file2.write(obj.toJSONString());
                file2.flush();
                file2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void read(int n) {   // 목록
        System.out.println("번호 / 작가 / 명언");
        System.out.println("------------------");
        // 키를 리스트로 변환하고 최신순으로 정렬
        List<Integer> keys = service.findAll();
        Collections.reverse(keys);

        // 시작 인덱스와 끝 인덱스 계산
        int startIndex = (n - 1) * 5;
        int endIndex = Math.min(startIndex + 5, keys.size());

        // 유효 범위 확인
        if (startIndex >= keys.size()) {
            System.out.println("더 이상 데이터가 없습니다.");
            return;
        }

        // 지정된 범위의 데이터 출력
        for (int i = startIndex; i < endIndex; i++) {
            Integer key = keys.get(i);
            System.out.println(service.find(key));
        }

        System.out.println("------------------");
        System.out.print("페이지 : ");

        int pageNum = service.pageSize();
        for(int i = 1; i <= pageNum; i++) {
            if(i == n) {
                System.out.print("[" + i + "]");
            }
            else System.out.print(i);
            if(i != pageNum) System.out.print(" / ");
        }
        System.out.println();

    }
    private void readKeyword(String keywordType, String keyword) {   // 키워드 목록
        System.out.println("------------------");
        System.out.println("검색 타입 : " + keywordType);
        System.out.println("검색어 : " + keyword);
        System.out.println("------------------");
        System.out.println("번호 / 작가 / 명언");
        System.out.println("------------------");

        List<Integer> keys = service.findAll();
        Collections.reverse(keys);

        if(keywordType.equals("author")) {
            for (Integer key : keys) {
                WiseSaying ws = service.find(key);
                if (ws.getAuthor().contains(keyword)) {
                    System.out.println(ws); // 찾은 항목 출력
                }
            }
        }
        else if(keywordType.equals("content")) {
            for (Integer key : keys) {
                WiseSaying ws = service.find(key);
                if (ws.getWiseSaying().contains(keyword)) {
                    System.out.println(ws); // 찾은 항목 출력
                }
            }
        }

    }
    private void update(int contentNum) {
        Scanner sc = new Scanner(System.in);
        WiseSaying data = service.find(contentNum);
        if(data == null)
            System.out.println(contentNum + "번 명언은 존재하지 않습니다.");
        else {
            System.out.println("명언(기존) : " + data.getWiseSaying());
            System.out.print("명언 : ");
            wiseSaying = sc.nextLine();
            System.out.println("작가(기존) : " + data.getAuthor());
            System.out.print("작가 : ");
            author = sc.nextLine();

            service.update(contentNum, author, wiseSaying);
            saveToJson(contentNum);
        }
    }
    private void delete(int contentNum) {
        if(!service.delete(contentNum)) {
            System.out.println(contentNum + "번 명언은 존재하지 않습니다.");
        }
        else {
            System.out.println(contentNum + "번 명언이 삭제되었습니다.");

            String deleteFilePath = directoryPath + "/" + contentNum + ".json";
            File file = new File(deleteFilePath);
            // 파일 존재 여부 확인 후 삭제
            if (file.exists()) {
                if (file.delete()) {
                    System.out.println(contentNum + ".json 파일이 삭제되었습니다.");
                } else {
                    System.out.println("파일 삭제에 실패했습니다.");
                }
            }
        }
    }

    private void build() {   // 빌드
        // JSONArray 객체 생성 (모든 데이터를 이 배열에 담을 것)
        JSONArray dataArray = new JSONArray();

        String buildFilePath = directoryPath + "/data.json";
        File buildFile = new File(buildFilePath);

        try {
            buildFile.createNewFile();

            // FileWriter는 기본적으로 파일을 덮어쓰므로 두 번째 인자 없이 사용하면 기존 내용을 지운 후 새로 작성
            FileWriter fw = new FileWriter(buildFile, false);  // false로 설정하면 덮어쓰기 모드
            PrintWriter writer = new PrintWriter(fw);

            for(int i = 1; i < num; i++) {
                WiseSaying buildData = service.find(i);
                if(buildData != null) {
                    JSONObject obj = new JSONObject();
                    obj.put("id", i);
                    obj.put("content", buildData.getWiseSaying());
                    obj.put("author", buildData.getAuthor());

                    dataArray.add(obj);

                }
            }

            writer.println(dataArray.toJSONString());
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }

}
