import common.global.Command;
import wiseSaying.SystemController;
import wiseSaying.WiseSayingController;

import java.util.Scanner;

public class App {

    private final Scanner sc;
    private final WiseSayingController wiseSayingController;
    private final SystemController systemController;

    public App(Scanner sc) {
        this.sc = sc;
        wiseSayingController = new WiseSayingController(sc);
        systemController = new SystemController();
    }

    public void run() {
        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.println("명령 ) ");
            String cmd = sc.nextLine();

            Command command = new Command(cmd);
            String actionName = command.getActionName();

            switch (actionName) {
                case "종료" -> systemController.exit();
                case "등록" -> wiseSayingController.actionWrite();
                case "목록" -> wiseSayingController.actionPrint(command);
                case "삭제" -> wiseSayingController.actionDelete(command);
                case "수정" -> wiseSayingController.actionModify(command);
                case "빌드" -> wiseSayingController.actionBuild();
                default -> System.out.println("올바른 명령이 아닙니다.");
            }
            if(cmd.equals("종료")) break;
        }
    }

    public void makeSampleData(int cnt) {
        wiseSayingController.makeSampleData(cnt);
    }
}