package org.example;

import java.util.Scanner;

public class WiseSayingController {
    private final Scanner scanner;
    private final WiseSayingService service;

    public WiseSayingController(Scanner scanner, WiseSayingService service) {
        this.scanner = scanner;
        this.service = service;
    }

    public void exit() {
        scanner.close();
        System.exit(0);
    }

    public void create() {
        System.out.print("명언 : ");
        String content = scanner.nextLine();

        System.out.print("작가 : ");
        String author = scanner.nextLine();

        WiseSaying saved = service.create(content, author);
        System.out.println(saved.getId() + "번 명언이 등록되었습니다.");
    }

    public void readAll(int pageNum) {
        Page all = service.findAllByPage(pageNum);
        printResult(all);
    }

    public void delete(int id) {
        service.remove(id);
        System.out.println(id+"번 명언이 삭제되었습니다.");
    }

    public void update(int id) {
        //get quote
        WiseSaying wiseSaying = service.find(id);
        System.out.println("명언(기존) : "+wiseSaying.getContent() );
        System.out.print("명언 : ");
        String changedContent = scanner.nextLine();

        System.out.println("작가(기존) : "+wiseSaying.getAuthor() );
        System.out.print("작가 : ");
        String changedAuthor = scanner.nextLine();

        wiseSaying.update(changedContent, changedAuthor);
        service.update(id, wiseSaying);
    }

    public void build() {
        service.build();
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }

    public void search(String keywordType, String keyword, int pageNum) {
            System.out.println("----------------");
            System.out.println("검색타입 : " + keywordType);
            System.out.println("검색어 : " + keyword);
            System.out.println("----------------");
            Page founded = service.findByPage(keywordType, keyword, pageNum);
            printResult(founded);
        }
    private static void printResult(Page page) {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        for (WiseSaying ws : page.getContents())
            System.out.println(ws.getId()+" / "+ws.getAuthor()+" / "+ws.getContent());
        System.out.println(page);
    }
}
