import java.util.List;
import java.util.Scanner;

public class WiseSayingController {

    private final Scanner sc;
    private final WiseSayingService wiseSayingService;

    public WiseSayingController(Scanner sc) {
        this.sc = sc;
        this.wiseSayingService = new WiseSayingService();
    }

    public void create() {

        System.out.print("명언 : ");
        String content = sc.nextLine();
        System.out.print("작가 : ");
        String author = sc.nextLine();

        WiseSaying wiseSaying = wiseSayingService.create(content, author);
        System.out.println(wiseSaying.getId() + "번 명언이 등록되었습니다.");
    }

    public void list() {

        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        List<WiseSaying> wiseSayings = wiseSayingService.findAll();
        for (WiseSaying wiseSaying : wiseSayings) {
            System.out.println(wiseSaying.getId() + " / " + wiseSaying.getAuthor() + " / " + wiseSaying.getContent());
        }
    }

    public void list(String command) {

        String[] split = command.split("\\?");
        String[] params = split[1].split("&");
        String keywordType = "";
        String keyword = "";

        for (String param : params) {

            String[] keyValue = param.split("=");
            if (keyValue[0].equals("keywordType")) {
                keywordType = keyValue[1];
            } else if (keyValue[0].equals("keyword")) {
                keyword = keyValue[1];
            }
        }

        System.out.println("----------------------");
        System.out.println("검색타입 : " + keywordType);
        System.out.println("검색어 : " + keyword);
        System.out.println("----------------------");
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        List<WiseSaying> wiseSayings = wiseSayingService.search(keywordType, keyword);
        for (WiseSaying wiseSaying : wiseSayings) {
            System.out.println(wiseSaying.getId() + " / " + wiseSaying.getAuthor() + " / " + wiseSaying.getContent());
        }
    }

    public void remove(String command) {

        int id = Integer.parseInt(command.substring(6));

        WiseSaying wiseSaying = wiseSayingService.findById(id);
        if (wiseSaying == null) {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
            return ;
        }

        wiseSayingService.remove(id);
        System.out.println(id + "번 명언이 삭제되었습니다.");
    }

    public void modify(String command) {

        int id = Integer.parseInt(command.substring(6));

        WiseSaying wiseSaying = wiseSayingService.findById(id);
        if (wiseSaying == null) {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
            return ;
        }

        System.out.println("명언(기존) : " + wiseSaying.getContent());
        System.out.print("명언 : ");
        String newContent = sc.nextLine();

        System.out.println("작가(기존) : " + wiseSaying.getAuthor());
        System.out.print("명언 : ");
        String newAuthor = sc.nextLine();

        wiseSayingService.modify(id, newContent, newAuthor);
    }

    public void build() {

        wiseSayingService.build();
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }
}
