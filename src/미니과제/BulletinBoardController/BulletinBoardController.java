package src.미니과제.BulletinBoardController;


import src.미니과제.BulletinBoardService.BulletinBoardService;

import java.util.Scanner;

public class BulletinBoardController {
    private final BulletinBoardService service;
    private final Scanner scanner;

    public BulletinBoardController() {
        this.service = new BulletinBoardService();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("== 명언 ==");

        while (true) {
            System.out.print("명령) ");
            String command = scanner.nextLine();

            if (command.equals("등록")) {
                registerProverb();
            } else if (command.equals("목록")) {
                listProverb();
            } else if (command.startsWith("검색")) {
                searchProverb(command);
            } else if (command.startsWith("삭제?id=")) {
                deleteProverb(command);
            } else if (command.startsWith("수정?id=")) {
                updateProverb(command);
            } else if (command.equals("빌드")) {
                buildDataFile(); // 빌드 명령 처리
            } else if (command.equals("종료")) {
                System.out.println("프로그램이 종료되었습니다.");
                break;
            } else {
                System.out.println("잘못된 명령어입니다.");
            }
        }
    }

    private void registerProverb() {
        System.out.print("명언 : ");
        String proverb = scanner.nextLine();

        System.out.print("작가 : ");
        String author = scanner.nextLine();

        if (proverb.trim().isEmpty() || author.trim().isEmpty()) {
            System.out.println("명언과 작가는 반드시 입력해야 합니다.");
            return;
        }

        service.addProverb(proverb, author);
    }

    private void listProverb() {
        System.out.print("페이지 번호 입력: ");
        int page = Integer.parseInt(scanner.nextLine());
        service.listProverbs(page);
    }

    private void searchProverb(String command) {
        String[] parts = command.split("\\?");

        if (parts.length < 2) {
            System.out.println("잘못된 검색 명령어입니다. 형식: 검색?keywordType=<검색타입>&keyword=<검색어>");
            return;
        }

        String[] keywords = parts[1].split("&");
        if (keywords.length < 2) {
            System.out.println("검색타입 또는 검색어가 누락되었습니다.");
            return;
        }

        String keywordType = keywords[0].split("=")[1];
        String keyword = keywords[1].split("=")[1];
        service.searchProverb(keywordType, keyword);
    }

    private void deleteProverb(String command) {
        int id = Integer.parseInt(command.split("=")[1]);
        service.deleteProverb(id);
    }

    private void updateProverb(String command) {
        int id = Integer.parseInt(command.split("=")[1]);
        service.updateProverb(id, scanner);
    }

    private void buildDataFile() {
        service.saveProverbsToData(); // 데이터 파일 빌드
        System.out.println("data.json 파일이 생성되었습니다.");
    }
}
