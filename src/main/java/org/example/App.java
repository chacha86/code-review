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

                String strId = command.substring(6);
                int id = Integer.parseInt(strId);

                boolean result = deleteWiseSaying(id);

                if(result) {
                    System.out.println("%d번 명언이 삭제되었습니다.".formatted(id));
                } else {
                    System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
                }
            }
        }
    }

    private boolean deleteWiseSaying(int targetId) {

        for(WiseSaying wiseSaying : wiseSayingList) {
            if(wiseSaying.getId() == targetId) {
                wiseSayingList.remove(wiseSaying);
                return true;
            }
        }

        return false;
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