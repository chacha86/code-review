import wiseSaying.SystemController;
import wiseSaying.WiseSayingController;

import java.util.Scanner;

public class App {
    private final Scanner scanner;
    private final WiseSayingController controller;
    private final SystemController systemController;

    public App(Scanner scanner, WiseSayingController controller) {
        this.scanner = scanner;
        this.controller = controller;
        this.systemController = new SystemController();
    }

    public void run() {
        System.out.println("== 명언 앱 ==");
        System.out.print("명령) ");

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            if (command.equals("종료")) {
                systemController.exit();
                break;
            } else if (command.equals("등록")) {
                controller.register();
            } else if (command.equals("목록")) {
                controller.printList(1);
            } else if (command.startsWith("목록?")) {
                controller.queryList(command);
            } else if (command.startsWith("삭제")) {
                controller.delete(command);
            } else if (command.startsWith("수정")) {
                controller.update(command);
            } else if (command.equals("빌드")) {
                controller.build();
            } else {
                System.out.println("알 수 없는 명령입니다.");
            }
            System.out.print("명령) ");
        }
    }
}
