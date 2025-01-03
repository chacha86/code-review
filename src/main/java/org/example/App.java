package org.example.wiseSaying;

import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {
    private final WiseSayingRepository wiseSayingRepository = new WiseSayingRepository();
    private final WiseSayingService wiseSayingService = new WiseSayingService(wiseSayingRepository);
    private final WiseSayingController wiseSayingController = new WiseSayingController(wiseSayingService);

    public void run() throws IOException, ParseException {
        System.out.println("== 명언 앱 ==");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        wiseSayingRepository.initDatabase();

        while (true) {
            System.out.print("명령) ");
            String command = br.readLine();

            if (command.equals("종료")) {
                System.out.println("명언 앱을 종료합니다.");
                break;
            }

            handleCommand(command, br);
        }
    }

    /// 명령어 핸들러
    public void handleCommand(String command, BufferedReader br) throws IOException {
        if (command.equals("등록")) {
            wiseSayingController.createWiseSaying(br);
        } else if (command.startsWith("목록?page=") || command.equals("목록")) {
            wiseSayingController.printWiseSayings(command);
        } else if (command.startsWith("삭제?id=")) {
            int id = Integer.parseInt(command.split("=")[1]);
            wiseSayingController.deleteWiseSayingById(id);
        } else if (command.startsWith("수정?id=")) {
            int id = Integer.parseInt(command.split("=")[1]);
            wiseSayingController.updateWiseSayingById(id, br);
        } else if (command.equals("빌드")) {
            wiseSayingController.buildWiseSaying();
        } else if (command.startsWith("목록?keywordType=")) {
            String keywordType = command.split("=")[1].split("&")[0];
            String keyword = command.split("=")[2];
            wiseSayingController.searchWiseSaying(keywordType, keyword);
        }else {
            System.out.println("알 수 없는 명령입니다.");
        }
    }
}