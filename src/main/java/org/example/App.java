package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class App {
    private int lastId = 0;
    private final ArrayList<WiseSaying> wiseSayingList = new ArrayList<>();

    Scanner scanner = new Scanner(System.in);

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
                deleteWiseSaying();
            }
        }
    }

    private void deleteWiseSaying() {
        System.out.println("삭제");
        // wiseSayingList.remove(1); // 1번째 명언 삭제 -> 명언의 번호와 배열의 index가 아무 상관이 없다.

        // 1. 아이디가 1인 명언의 index를 얻는다.
        // 2. 아이디가 1인 명언의 값 자체를 얻는다.
        for(WiseSaying wiseSaying : wiseSayingList) {
            if(wiseSaying.getId() == 1) {
                wiseSayingList.remove(wiseSaying);
                break;
            }
        }

    }

    private void printWiseSayingList() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        for(int i = wiseSayingList.size() - 1; i >= 0; i--) {
            WiseSaying wiseSaying = wiseSayingList.get(i);
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