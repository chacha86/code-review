package com.ll.WiseSaying.Controller;


import com.ll.WiseSaying.Domain.WiseSaying;
import com.ll.WiseSaying.Service.WiseSayingService;

import java.util.Scanner;

public class WiseSayingController {
    private final WiseSayingService service = new WiseSayingService();
    private final Scanner sc = new Scanner(System.in);

    public void commandHandler(String command) {
        if (command.startsWith("등록")) {
            handleRegistration();
        } else if (command.startsWith("목록")) {
            handleList();
        } else if (command.startsWith("삭제")) {
            handleDeletion(command);
        } else if (command.startsWith("수정")) {
            handleModification(command);
        } else if (command.startsWith("빌드")) {
            service.buildDataToJson();
        }
    }

    public void handleRegistration() {
        System.out.print("명언 : ");
        String content = sc.nextLine();
        System.out.print("작가 : ");
        String author = sc.nextLine();
        ;

        int id = service.registerWiseSaying(content, author);
        System.out.println(String.format("%d번 명언이 등록되었습니다.", id));
    }

    public void handleList() {
        service.listWiseSayings().forEach((w) ->
                System.out.println(String.format("%d / %s / %s"
                        , w.getId(), w.getAuthor(), w.getContent())));
    }

    public void handleDeletion(String command) {
        int id = Integer.parseInt(command.split("=")[1]);
        if (service.deleteWiseSaying(id)) {
            System.out.println(String.format("%d번 명언이 삭제되었습니다.", id));
        } else {
            System.out.println(String.format("%d번 명언이 존재하지 않습니다.", id));
        }
    }

    public void handleModification(String command) {
        int id = Integer.parseInt(command.split("=")[1]);
        WiseSaying wiseSaying = service.getWiseSaying(id);

        System.out.println(String.format("작가(기존) : %s", wiseSaying.content));
        System.out.print("명언 : ");
        String newContent = sc.nextLine();
        System.out.println(String.format("명언(기존) : %s", wiseSaying.author));
        System.out.print("작가 : ");
        String newAuthor = sc.nextLine();

        service.modifyWiseSaying(id, newContent, newAuthor);
    }
}
