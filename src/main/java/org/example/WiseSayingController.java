package org.example.wiseSaying;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 *  컨트롤러
 */

public class WiseSayingController {
    private final WiseSayingService wiseSayingService;
    private final String DB_PATH = "db/wiseSaying";
    private int lastId = 0;

    public WiseSayingController(WiseSayingService wiseSayingService) {
        this.wiseSayingService = wiseSayingService;
    }

    /// 빌드 명령어
    public void buildWiseSaying() throws IOException {
        List<WiseSaying> wiseSayings = wiseSayingService.findAllWiseSayings();

        JSONArray jsonArray = new JSONArray();

        for (WiseSaying wiseSaying : wiseSayings) {
            JSONObject json = new JSONObject();
            json.put("id", wiseSaying.getId());
            json.put("author", wiseSaying.getAuthor());
            json.put("content", wiseSaying.getContent());
            jsonArray.add(json);
        }

        File file = new File(DB_PATH, "data.json");
        Files.writeString(file.toPath(), jsonArray.toJSONString());
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }

    /// 명언 등록
    public void createWiseSaying(BufferedReader br) throws IOException {
        System.out.print("명언: ");
        String content = br.readLine();
        System.out.print("작가: ");
        String author = br.readLine();
        lastId++;

        WiseSaying wiseSaying = new WiseSaying(lastId, author, content);
        wiseSayingService.addWiseSaying(wiseSaying);

        System.out.println(lastId + "번 명언이 등록되었습니다.");
    }


    /// 명언 조회
    public void printWiseSayings() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        wiseSayingService.printWiseSaying();
    }

    /// 명언 수정
    public void updateWiseSayingById(int id, BufferedReader br) throws IOException {
        if (id == -1) return;

        WiseSaying wiseSaying = wiseSayingService.findWiseSayingById(id);

        if (wiseSaying == null) {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
        }

        System.out.println("명언(기존): " + wiseSaying.getContent());
        System.out.print("명언: ");
        String newContent = br.readLine();
        System.out.println("작가(기존): " + wiseSaying.getAuthor());
        System.out.print("작가: ");
        String newAuthor = br.readLine();

        wiseSayingService.updateWiseSaying(wiseSaying, newContent, newAuthor);

        System.out.println(id + "번 명언이 수정되었습니다.");
    }

    /// 명언 삭제
    public void deleteWiseSayingById(int id) throws IOException {
        if (wiseSayingService.deleteWiseSaying(id)) {
            System.out.println(id + "번 명언이 삭제되었습니다.");
        } else {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
        }
    }

    /// 명언 검색
    public void searchWiseSaying(String keywordType, String keyword) throws IOException {
        System.out.println("----------------------");
        System.out.println("검색타입 : " + keywordType);
        System.out.println("검색어 : " + keyword);
        System.out.println("----------------------");
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        wiseSayingService.printWiseSayingByKeyword(keywordType, keyword);
    }
}