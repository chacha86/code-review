import java.util.Scanner;

public class App {

    private final Scanner sc;
    private final WiseSayingController wiseSayingController;

    public App() {
        this.sc = new Scanner(System.in);
        this.wiseSayingController = new WiseSayingController(sc);
    }

    public void run() {

        System.out.println("== 명언 앱 ==");

        while (true) {

            System.out.print("명령) ");
            String command = sc.nextLine().trim();

            if (command.equals("종료")) {
                System.out.println("프로그램을 종료합니다.");
                break;
            }

            if (command.equals("등록")) {
                wiseSayingController.create();
            } else if (command.startsWith("목록")) {
                if (command.contains("?")) wiseSayingController.list(command);
                else wiseSayingController.list();
            } else if (command.startsWith("삭제?id=")) {
                wiseSayingController.remove(command);
            } else if (command.startsWith("수정?id=")) {
                wiseSayingController.modify(command);
            } else if (command.equals("빌드")) {
                wiseSayingController.build();
            }
        }
    }
}
