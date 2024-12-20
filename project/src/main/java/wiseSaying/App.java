package wiseSaying;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


@Slf4j
public class App {

    private WiseSayingController controller = WiseSayingController.getInstance();

    private String condition;
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
                    register();
                    break;
                case "목록":
                    controller.search(condition);
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

    private void register() {
        System.out.print("명언 : ");
        String content = reader();
        System.out.print("작가 : ");
        String author = reader();
        Long registeredId = controller.register(content, author);
        System.out.println(registeredId + "번 명언이 등록되었습니다.");
    }

    public void readInputString() {
        try {
            System.out.print("명령) ");
            findRealCommand(reader());
        } catch (Exception e) {
            log.error("잘못된 명령 요청");
        }
    }

    public String reader() {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        try {
            return bf.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
