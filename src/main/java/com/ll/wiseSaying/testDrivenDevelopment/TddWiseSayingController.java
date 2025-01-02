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

    }

    public void delete(String command) {

    }

    public void build() {

    }
}
