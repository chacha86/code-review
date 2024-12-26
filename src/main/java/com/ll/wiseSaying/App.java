package com.ll.wiseSaying;

import java.util.ArrayList;
import java.util.Scanner;

public class App {
    private final Scanner scanner = new Scanner(System.in);
    private final ArrayList<WiseSaying> wiseSayingList = new ArrayList<>();
    private int lastId = 0;

    public void run() {
        add("꿈을 지녀라. 그러면 어려운 현실을 이길 수 있다.", "월트 디즈니");
        add("현재를 사랑하라", "작자 미상");

        System.out.println("== 명언 앱 ==");
        while (true) {
            System.out.print("명령) ");
            String command = scanner.nextLine();

            if (command.equals("종료")) {
                System.out.println("명언 앱을 종료합니다.");
                break;

            } else if (command.equals("등록")) {
                writeWiseSaying();
            } else if (command.equals("목록")) {
                printWiseSayingList();
            } else if(command.startsWith("삭제?id=")) {
                String strId = command.substring(6);
                int id = Integer.parseInt(strId);
                deleteWiseSaying(id);
            } else if(command.startsWith("수정?id=")) {
                String strId = command.substring(6);
                int id = Integer.parseInt(strId);
                updateWiseSaying(id);
            }
        }
    }

    private WiseSaying findWiseSaying(int targetId) {
        for(WiseSaying wiseSaying : wiseSayingList) {
            if(wiseSaying.getId() == targetId) {
                return wiseSaying;
            }
        }
        return null;
    }

    private void updateWiseSaying(int targetId) {
        WiseSaying wiseSaying = findWiseSaying(targetId);

        if(wiseSaying == null) {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(targetId));
        } else {
            System.out.println("명언(기존) : %s".formatted(wiseSaying.getContent()));
            System.out.print("명언 : ");
            String content = scanner.nextLine();
            wiseSaying.setContent(content);

            System.out.println("작가(기존) : %s".formatted(wiseSaying.getAuthor()));
            System.out.print("작가 : ");
            String author = scanner.nextLine();
            wiseSaying.setAuthor(author);

            System.out.println("%d번 명언이 수정되었습니다.".formatted(targetId));
        }
    }

    private void deleteWiseSaying(int targetId) {
        WiseSaying wiseSaying = findWiseSaying(targetId);

        if (wiseSaying == null) {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(targetId));
        } else {
            wiseSayingList.remove(wiseSaying);
            System.out.println("%d번 명언이 삭제되었습니다.".formatted(targetId));
        }
    }

    private void printWiseSayingList() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        for(WiseSaying wiseSaying : wiseSayingList.reversed()) {
            System.out.println("%d / %s / %s".formatted(wiseSaying.getId(), wiseSaying.getAuthor(), wiseSaying.getContent()));
        }
    }

    private void writeWiseSaying() {
        System.out.print("명언 : ");
        String content = scanner.nextLine();

        System.out.print("작가 : ");
        String author = scanner.nextLine();

        add(content, author);
        System.out.println("%d번 명언이 등록되었습니다.".formatted(lastId));
    }

    public void add(String content, String author) {
        WiseSaying wiseSaying = new WiseSaying(++lastId, content, author);
        wiseSayingList.add(wiseSaying);
    }
}
