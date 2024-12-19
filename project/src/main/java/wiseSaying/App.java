package wiseSaying;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import java.io.BufferedReader;
import java.io.InputStreamReader;


@Slf4j
public class App {

    private WiseSayingController controller = WiseSayingController.getInstance();

    @Getter
    private String condition;
    @Getter
    private String commandType;

    public void app() {
        System.out.println("== 명언 앱 ==");
        while (true) {
            inputCommand();
        }
    }

    public void inputCommand() {

        readInputString();

        selectMenu();
    }

    public void selectMenu() {
        if (Command.containsCommand(commandType)) {
            //등록 목록 삭제 수정 빌드 종료
            switch (commandType) {
                case "등록":
                    controller.register();
                    break;
                case "목록":
                    if (condition.contains("?")) {
                        controller.search(condition);
                    } else {
                        controller.showList();
                    }
                    break;
                case "삭제" :
                    controller.remove(condition);
                    break;
                case "수정" :
                    controller.modify(condition);
                    break;
                case "빌드":
                    controller.build();
                    break;
                case "종료":
                    controller.exit();
                    break;
            }
        } else {
            System.out.println("다시 입력해 주세요");
        }
    }

    public void readInputString() {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("명령) ");
            findRealCommand(bf.readLine());
        } catch (Exception e) {
            log.error("잘못된 명령 요청");
        }
    }

    public void findRealCommand(String input) {

        commandType = input;
        condition = input;

        int questionMarkIndex = condition.indexOf('?');

        if (questionMarkIndex != -1) {
            // ?가 포함된 경우
            commandType = condition.substring(0, questionMarkIndex);
        }
    }
}
