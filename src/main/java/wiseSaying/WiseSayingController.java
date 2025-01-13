package wiseSaying;

import common.global.Command;

import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

public class WiseSayingController {

    private final Scanner sc;
    private final WiseSayingService wiseSayingService;
    private int itemsPerPage;

    public WiseSayingController(Scanner sc) {
        this.sc = sc;
        wiseSayingService = new WiseSayingService();
        itemsPerPage = 5;
    }

    public void actionWrite() {
        System.out.println("명언 : ");
        String content = sc.nextLine();
        System.out.println("작가 : ");
        String author = sc.nextLine();

        WiseSaying wiseSaying = wiseSayingService.write(content, author);
        System.out.println("%d번 명언이 등록되었습니다.".formatted(wiseSaying.getId()));
    }

    public void actionPrint(Command command) {

        int page = command.getParamAsInt("page", 1);
        Page<WiseSaying> pageContent;

        if(command.isSearchCommand()) {

            String ktype = command.getParam("keywordType");
            String kw = command.getParam("keyword");

            pageContent = wiseSayingService.search(ktype, kw, itemsPerPage, page);


        } else {
            pageContent = wiseSayingService.getAllItems(itemsPerPage, page);
        }

        printWiseSayings(pageContent, command);
    }

    private void printWiseSayings(Page<WiseSaying> pageContent, Command cmd) {

        if(pageContent.getContent().isEmpty()) {
            System.out.println("등록된 명언이 없습니다.");
            return;
        }

        if(cmd.isSearchCommand()) {

            String kwtype = cmd.getParam("keywordType");
            String kw = cmd.getParam("keyword");

            System.out.println("----------------------");
            System.out.println("검색타입 : %s".formatted(kwtype));
            System.out.println("검색어 : %s".formatted(kw));
            System.out.println("----------------------");

        }

        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        pageContent.getContent().forEach(w -> {
            System.out.printf("%d / %s / %s\n", w.getId(), w.getAuthor(), w.getContent());
        });

        printPage(pageContent.getPage(), pageContent.getTotalPages());
    }

    private void printPage(int page, int totalPages) {

        for(int i = 1; i <= totalPages; i++) {
            if(i == page) {
                System.out.print("[%d]".formatted(i));
            } else {
                System.out.print("%d".formatted(i));
            }

            if(i == totalPages) {
                System.out.println();
                break;
            }
            System.out.print(" / ");
        }
    }


    public void actionDelete(Command cmd) {

        int id = cmd.getParamAsInt("id", -1);
        boolean result = wiseSayingService.delete(id);

        if(!result) {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
        }
    }

    public void actionModify(Command cmd) {
        int id = cmd.getParamAsInt("id", -1);

        Optional<WiseSaying> opWiseSaying = wiseSayingService.getItem(id);
        WiseSaying wiseSaying = opWiseSaying.orElse(null);

        if(wiseSaying == null) {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
            return;
        }

        System.out.printf("명언(기존) : %s\n", wiseSaying.getContent());
        System.out.println("명언 : ");
        String newContent = sc.nextLine();

        System.out.printf("작가(기존) : %s\n", wiseSaying.getAuthor());
        System.out.println("작가 : ");
        String newAuthor = sc.nextLine();

        wiseSayingService.modify(wiseSaying, newContent, newAuthor);

        System.out.println("%d번 명언이 수정되었습니다.".formatted(id));

    }

    public void actionBuild() {
        wiseSayingService.build();
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }

    public void makeSampleData(int cnt) {
        wiseSayingService.makeSampleData(cnt);
        System.out.println("샘플 데이터가 생성되었습니다.");
    }
}