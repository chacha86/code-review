package com.ll.wiseSaying;

import java.util.Scanner;

public class WiseSayingController {
    private final WiseSayingService service;

    public WiseSayingController() {
        this.service = new WiseSayingService();
    }

    public void handleCommand(Scanner scanner) {

        while(true) {
            System.out.print("명령) ");
            if(!scanner.hasNextLine()) break; // 테스트 때문에 만들었습니다.(이게 없으면 scanner가 계속 켜져있어서 오류가 나타남)

            String cmd = scanner.nextLine().trim();
            switch(getCommandType(cmd)) {
                case "등록" -> handleAdd(scanner);
                case "목록" -> handleList(cmd);
                case "삭제" -> handleDelete(cmd);
                case "수정" -> handleModify(scanner, cmd);
                case "빌드" -> handleBuild();
                case "종료" -> {
                    handleExit();
                    handleCommand(scanner);
                    return;
                }
            }
        }
    }

    private String getCommandType(String cmd) {
        if(cmd.contains("?")) {
            return cmd.split("\\?")[0];
        }
        return cmd;
    }

    private void handleAdd(Scanner scanner) {
        System.out.print("명언 : ");
        String saying = scanner.nextLine();
        System.out.print("작가 : ");
        String author = scanner.nextLine();
        int id = service.addWiseSaying(saying, author);
        System.out.println(id + "번 명언이 등록되었습니다.");

        WiseSaying wiseSaying = service.getWiseSayings().getWiseSaying(id);
        // 등록 직후 파일 저장
        service.getWiseSayings().saveSingleWiseSaying(wiseSaying);
    }

    void handleBuild() {
        service.getWiseSayings().totalJsonFileSave();
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }

    void handleExit() {
        System.out.println();
        CustomList wiseSayings = service.getWiseSayings();
        service.initWiseSayingService();
        System.out.println("프로그램 다시 시작...");
        System.out.println();
        System.out.print("==  명언 앱 ==");
        System.out.println();
    }

    private void handleModify(Scanner scanner, String cmd) {
        String[] splitStr = cmd.split("\\?id=");
        
        if(splitStr.length == 1) {
            System.out.println("잘못된 값 입니다. 다시 입력해 주세요");
            return;
        }
        
        int idx = Integer.parseInt(splitStr[1]);
        WiseSaying curWiseSaying = service.getWiseSayings().getWiseSaying(idx);

        if(curWiseSaying == null) {
            System.out.println(idx + "번 명언은 존재하지 않습니다.");
            return;
        }

        System.out.println("명언(기존) : " + curWiseSaying.getSaying());
        System.out.print("명언 : ");
        String modifySaying = scanner.nextLine();
        System.out.println("작가(기존) : " + curWiseSaying.getAuthor());
        System.out.print("작가 : ");
        String modifyAuthor = scanner.nextLine();

        service.getWiseSayings().modifyWiseSaying(idx, modifySaying, modifyAuthor);

        // 저장하면, 수정이되므로
        service.getWiseSayings().saveSingleWiseSaying(curWiseSaying);
    }

    private void handleDelete(String cmd) {
        String[] splitStr = cmd.split("\\?id=");
        int idx = Integer.parseInt(splitStr[1]);
        service.deleteWiseSaying(idx);

        service.getWiseSayings().deleteSingleWiseSaying(idx);
    }

    private void handleList(String cmd) {
        ListCommand listCommand = new ListCommand(cmd);
        CustomList wiseSayings = service.getWiseSayings();

        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        if(listCommand.hasSearch()) {
            handleSearchList(wiseSayings, listCommand);
        } else {
            handlePagedList(wiseSayings, listCommand.getPage());
        }
    }

    private void handlePagedList(CustomList wiseSayings, int page) {
        CustomList pageResults = wiseSayings.getPage(page);
        printWiseSayings(pageResults);
        wiseSayings.printPageInfo(page);
    }

    private void handleSearchList(CustomList wiseSayings, ListCommand cmd) {
        System.out.println("----------------------");
        System.out.println("검색타입 : " + cmd.getKeywordType());
        System.out.println("검색어 : " + cmd.getKeyword());
        System.out.println("----------------------");
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        CustomList searchResults = service.searchWiseSayings(
                cmd.getKeywordType(),
                cmd.getKeyword()
        );
        CustomList pageResults = searchResults.getPage(cmd.getPage());
        printWiseSayings(pageResults);
        wiseSayings.printPageInfo(cmd.getPage());
    }

    private void printWiseSayings(CustomList list) {
        for (int i = 0; i < list.size(); i++) {
            WiseSaying wiseSaying = list.get(i);
            System.out.println(wiseSaying.getIdx() + " / " +
                    wiseSaying.getAuthor() + " / " +
                    wiseSaying.getSaying());
        }
    }

    public void addSampleData(String saying, String author) {
        int id = service.addWiseSaying(saying, author);
        WiseSaying wiseSaying = service.getWiseSayings().getWiseSaying(id);
        // 등록 직후 파일 저장
        service.getWiseSayings().saveSingleWiseSaying(wiseSaying);
    }
}
