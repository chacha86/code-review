package com.ll.wiseSaying.testDrivenDevelopment;

import java.util.*;

public class TddWiseSayingController {

    private final TddWiseSayingService service;

    public TddWiseSayingController(TddWiseSayingService service) {
        this.service = service;
    }

    public void register(Scanner scanner) {

        System.out.print("명언 : ");
        String content = scanner.nextLine();
        System.out.print("작가 : ");
        String author = scanner.nextLine();

        if (content.isEmpty() || author.isEmpty()) {
            throw new IllegalArgumentException("명언과 작가를 모두 입력해주세요.");
        }

        int id = service.registerWiseSaying(author, content);
        System.out.println(id + "번 명언이 등록되었습니다.");
    }

    public void search(String command) {

        int pageSize = 5;
        int pageNum = 1;
        TddPage<TddWiseSaying> page;

        Map<String, String> keywordList = service.parseSearchKeyword(command);
        if (!service.validateSearchKeyword(keywordList)) {
            throw new IllegalArgumentException("명령을 다시 확인해주세요.");
        }

        if (!keywordList.isEmpty()) {
            if (keywordList.containsKey("page")) {
                pageNum = Integer.parseInt(keywordList.get("page"));
            }

            if (keywordList.size()==1) {
                page = service.findAll(pageNum, pageSize);
            } else {

                String keywordType = command.split("keywordType=")[1].split("&")[0];
                String keyword = command.split("keyword=")[1].split("&")[0];

                System.out.println("----------------------");
                System.out.println("검색타입 : " + keywordType);
                System.out.println("검색어 : " + keyword);
                System.out.println("----------------------");

                page = service.findDetail(pageNum, pageSize, keywordType, keyword);
            }
        } else {
            page = service.findAll(pageNum, pageSize);
        }

        System.out.println(page);
    }

    public void modify(Scanner scanner, String command) {
        int id = service.parseId(command);
        TddWiseSaying wiseSaying = service.findById(id);

        if (wiseSaying == null) {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
            return;
        }

        System.out.println("명언(기존) : " + wiseSaying.getContent());
        System.out.print("명언 : ");
        String newContent = scanner.nextLine();
        System.out.println("작가(기존) : " + wiseSaying.getAuthor());
        System.out.print("작가 : ");
        String newAuthor = scanner.nextLine();

        if (newAuthor.isBlank() || newContent.isBlank()) {
            throw new IllegalArgumentException("정보를 모두 입력해주세요.");
        }

        service.modifyWiseSaying(id, newAuthor, newContent);
    }

    public void delete(String command) {
        int id = service.parseId(command);

        if (service.deleteById(id)) {
            System.out.println(id + "번 명언이 삭제되었습니다.");
        } else {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
        }
    }

    public void build() {
        if (service.saveAll()) {
            System.out.println("data.json 파일의 내용이 갱신되었습니다.");
        } else {
            throw new IllegalArgumentException("빌드에 실패했습니다.");
        }
    }
}
