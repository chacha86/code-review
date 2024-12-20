package com.ll.wiseSaying.src;

import java.util.Scanner;

public class WiseSayingController {
    WiseSayingService wiseSayingService = new WiseSayingService();
    Scanner sc;

    public WiseSayingController(Scanner sc) {
        this.sc = sc;
    }
    public void addWiseSaying() {
        System.out.printf("명언 : ");
        String content = sc.nextLine();
        System.out.printf("작가 : ");
        String author = sc.nextLine();
        int id = wiseSayingService.addWiseSaying(content, author);
        System.out.println(id + "번 명언이 등록되었습니다.");
    }

    public void removeWiseSaying(int id) {
        try {
            wiseSayingService.removeWiseSaying(id);
            System.out.println(id + "번째 명언이 삭제되었습니다.");
        }catch (Exception e){
            System.out.println(e);
        }

    }

    public void updateWiseSaying(int id) {
        try {
            System.out.printf("명언 : ");
            String content = sc.nextLine();
            System.out.printf("작가 : ");
            String author = sc.nextLine();
            wiseSayingService.updateWiseSaying(id, content, author);
            System.out.println(id + "번 명언이 수정되었습니다.");
        } catch (Exception e){
            System.out.println(e);
        }

    }

    public void showWiseSaying() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");
        System.out.println(wiseSayingService.getAllWiseSaying());
    }

    public void buildJson() {
        wiseSayingService.buildJson();
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }

    public void terminate(){
        wiseSayingService.saveLastId();
    }
}
