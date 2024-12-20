package com.ll.wiseSaying.basicDevelopment;

import java.util.*;

public class WiseSayingController {

    private final static WiseSayingController instance = new WiseSayingController();

    private WiseSayingController() {
    }

    public static synchronized WiseSayingController getInstance() {
        return instance;
    }

    private final WiseSayingService wiseSayingService = WiseSayingService.getInstance();

    private enum searchKeyword {
        KEYWORD_TYPE("keywordType="),
        KEYWORD("keyword="),
        PAGE("page="),
        AND("&"),
        ID("id=");

        final String word;

        searchKeyword(String word) {
            this.word = word;
        }
    }

    public void registerWiseSaying(Scanner scanner) {
        System.out.print("명언 : ");
        String content = scanner.nextLine();
        System.out.print("작가 : ");
        String author = scanner.nextLine();

        WiseSaying wiseSaying = wiseSayingService.register(content, author);
        System.out.println(wiseSaying.getId() + "번 명언이 등록되었습니다.");
    }

    public void searchWiseSaying(String cmd) {
        List<WiseSaying> wiseSayingList = new ArrayList<>();

        if (cmd.contains(searchKeyword.KEYWORD.word) && cmd.contains(searchKeyword.KEYWORD_TYPE.word)) {
            String keywordType = cmd.split(searchKeyword.KEYWORD_TYPE.word)[1].split(searchKeyword.AND.word)[0];
            String keyword = cmd.split(searchKeyword.KEYWORD.word)[1].split(searchKeyword.AND.word)[0];

            System.out.println("----------------------");
            System.out.println("검색타입 : " + keywordType);
            System.out.println("검색어 : " + keyword);
            System.out.println("----------------------");

            if (keywordType.equals("content")) {
                wiseSayingList = wiseSayingService.findAllByContent(keyword);
            } else if (keywordType.equals("author")) {
                wiseSayingList = wiseSayingService.findAllByAuthor(keyword);
            }
        } else {
            wiseSayingList = wiseSayingService.findAll();
        }

        printPagedList(cmd, wiseSayingList);
    }

    public void printPagedList(String cmd, List<WiseSaying> wiseSayingList) {
        int pageSize = 5;
        int page = cmd.contains(searchKeyword.PAGE.word)
                ? Integer.parseInt(cmd.split(searchKeyword.PAGE.word)[1].split(searchKeyword.AND.word)[0]) : 1;

        List<WiseSaying> resultList = wiseSayingService.getPagedWiseSayings(page, pageSize, wiseSayingList);
        int totalPages = (int) Math.ceil((double) wiseSayingList.size() / pageSize);

        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");
        for (WiseSaying wiseSaying : resultList) {
            System.out.printf("%d / %s / %s%n", wiseSaying.getId(), wiseSaying.getAuthor(), wiseSaying.getContent());
        }
        System.out.println("----------------------");
        System.out.print("페이지 : ");
        for (int i = 1; i <= totalPages; i++) {
            if (i != 1) {
                System.out.print("/ ");
            }

            if (i == page) {
                System.out.printf("[%d] ", i);
            } else {
                System.out.printf("%d ", i);
            }
        }
        System.out.println();
    }

    public void deleteWiseSaying(String cmd) {
        int id = Integer.parseInt(cmd.split(searchKeyword.ID.word)[1]);
        if (wiseSayingService.deleteById(id)) {
            System.out.println(id + "번 명언이 삭제되었습니다.");
        } else {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
        }
    }

    public void updateWiseSaying(Scanner scanner, String cmd) {
        int id = Integer.parseInt(cmd.split(searchKeyword.ID.word)[1]);
        WiseSaying wiseSaying = wiseSayingService.findById(id);

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

        wiseSayingService.update(id, newContent, newAuthor);
    }

    public void buildData() {
        wiseSayingService.saveAll();
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }
}
