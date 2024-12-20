package com.ll.wiseSaying;

import java.util.*;

public class WiseSayingController {
    private final WiseSayingService service;
    private final Scanner scanner;

    public WiseSayingController(WiseSayingService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    public void create() {
        System.out.print("명언 : ");
        String content = scanner.nextLine();

        System.out.print("작가 : ");
        String author = scanner.nextLine();

        WiseSaying wiseSaying = service.create(content, author);
        System.out.println(wiseSaying.getId() + "번 명언이 등록되었습니다.");
    }

    public void list(String command) {
        // 기본값 설정
        int page = 1;
        String keywordType = null;
        String keyword = null;

        // 명령어에 파라미터가 있는 경우 파싱
        if (command.contains("?")) {
            String[] params = command.split("\\?")[1].split("&");
            for (String param : params) {
                String[] keyValue = param.split("=");
                switch (keyValue[0]) {
                    case "page":
                        page = Integer.parseInt(keyValue[1]);
                        break;
                    case "keywordType":
                        keywordType = keyValue[1];
                        break;
                    case "keyword":
                        keyword = keyValue[1];
                        break;
                }
            }
        }

        // 검색 조건이 있는 경우 출력
        if (keywordType != null && keyword != null) {
            System.out.println("----------------------");
            System.out.println("검색타입 : " + keywordType);
            System.out.println("검색어 : " + keyword);
        }

        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        PageResponse<WiseSaying> pageResponse = service.getList(page, keywordType, keyword);

        // 아이템 출력
        for (WiseSaying wiseSaying : pageResponse.getItems()) {
            System.out.printf("%d / %s / %s\n",
                    wiseSaying.getId(),
                    wiseSaying.getAuthor(),
                    wiseSaying.getContent());
        }

        System.out.println("----------------------");
        // 페이지 정보 출력
        String pageInfo = String.format("페이지 : %s / %s",
                page == 1 ? "[1]" : "1",
                page == pageResponse.getTotalPages() ?
                        "[" + pageResponse.getTotalPages() + "]" :
                        pageResponse.getTotalPages());
        System.out.println(pageInfo);
    }

    public void delete(String command) {
        try {
            int id = Integer.parseInt(command.split("=")[1]);
            if (service.delete(id)) {
                System.out.println(id + "번 명언이 삭제되었습니다.");
            } else {
                System.out.println(id + "번 명언은 존재하지 않습니다.");
            }
        } catch (NumberFormatException e) {
            System.out.println("삭제 오류");
        }
    }

    public void modify(String command) {
        try {
            int id = Integer.parseInt(command.split("=")[1]);
            WiseSaying wiseSaying = service.modify(id, "", "");

            if (wiseSaying != null) {
                System.out.println("명언(기존) : " + wiseSaying.getContent());
                System.out.print("명언 : ");
                String content = scanner.nextLine();

                System.out.println("작가(기존) : " + wiseSaying.getAuthor());
                System.out.print("작가 : ");
                String author = scanner.nextLine();

                service.modify(id, content, author);
            } else {
                System.out.println(id + "번 명언은 존재하지 않습니다.");
            }
        } catch (NumberFormatException e) {
            System.out.println("수정 오류");
        }
    }

    public void build() {
        service.buildData();
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }
}