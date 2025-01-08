import java.util.*;

public class App {
    List<WiseSaying> wiseSayingList = new ArrayList<>();
    private final Scanner sc;
    int LastId;

    public App() {
        this.sc = new Scanner(System.in);
    }

    public void run() {
        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");

            String inputStr = sc.nextLine();
            String command = splitCommand(inputStr);

            switch (command) {
                case "등록" -> actionSave();
                case "목록" -> actionList();
                case "종료" -> actionExit();
                case "삭제" -> actionDelete(inputStr);
                case "수정" -> actionEdit(inputStr);
            }
        }
    }

    // input 처리 함수
    private String splitCommand(String input) {
        // 삭제?id=1
        // 삭제? | id=1
        String[] inputBits = input.split("\\?");
        return inputBits[0];
    }

    private String splitCommandAsId(String input) {
        // 삭제?id=1
        // 삭제? | id=1
        String[] inputBits = input.split("=");
        return inputBits[1];
    }

    private int stringToInt (String s) {
        return Integer.parseInt(splitCommandAsId(s));
    }

    // 명언 수정
    private void actionEdit(String input) {
        int strId = stringToInt(input);

        Optional<WiseSaying> result = wiseSayingList.stream()
                .filter(w -> w.getId() == strId)
                .findFirst();

        result.ifPresentOrElse(
                w -> {
                    System.out.println("명언(기존) : %s".formatted(w.getContent()));
                    System.out.print("명언 : ");
                    w.setContent(sc.nextLine());

                    System.out.println("작가(기존) : %s".formatted(w.getAuthor()));
                    System.out.print("작가 : ");
                    w.setAuthor(sc.nextLine());
                },
                () -> System.out.println("%d번 명언은 존재하지 않습니다.".formatted(strId))
        );
    }

    // 명언 삭제
    private void actionDelete(String input) {
        int strId = stringToInt(input);

        boolean removed = wiseSayingList.removeIf(w -> w.getId() == strId);

        if(removed) {
            System.out.println("%d번 명언이 삭제되었습니다.".formatted(strId));
        }
        else {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(strId));
        }

    }

    // 명언 목록
    private void actionList() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        for(int i = wiseSayingList.size()-1 ; i >= 0; i--) {
            WiseSaying wiseSaying = wiseSayingList.get(i);
            System.out.println("%d / %s / %s".formatted(wiseSaying.getId(), wiseSaying.getAuthor(), wiseSaying.getContent()));
        }
    }

    // 명언 등록
    private void actionSave() {
        LastId++;

        System.out.print("명언 : ");
        String content = sc.nextLine();
        System.out.print("작가 : ");
        String author = sc.nextLine();

        WiseSaying wiseSaying = new WiseSaying(LastId, content, author);
        wiseSayingList.add(wiseSaying);

        System.out.println("%d번 명언이 등록되었습니다.".formatted(wiseSaying.getId()));
    }

    // 앱 종료
    private void actionExit() {
        System.exit(0);
    }


}

class WiseSaying {
    private int id;
    private String content;
    private String author;

    public WiseSaying(int id, String content, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
