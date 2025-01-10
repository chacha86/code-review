import java.util.ArrayList;
import java.util.Scanner;

public class App {

    Scanner sc = new Scanner(System.in);
    String content;
    String author;

    private int lastId = 0;
    private final ArrayList<WiseSaying> wiseSayingList = new ArrayList<>();

    public void run() {
        wiseSayingAdd("꿈을 지녀라. 그러면 어려운 현실을 이길 수 있다.", "월트 디즈니");
        wiseSayingAdd("현재를 사랑하라.", "작자 미상");

        System.out.println("== 명언 앱 ==");
        while (true) {
            System.out.print("명령) ");
            String command = sc.nextLine();

            switch (command.split("\\?")[0]) {
                case "등록":
                    wiseSayingRegister();
                    break;
                case "종료":
                    System.out.println("명언 앱을 종료합니다.");
                    return;
                case "목록":
                    wiseSayingInventory();
                    break;
                default:
                    if (command.startsWith("삭제?id=")) {
                        int id = Integer.parseInt(command.substring(6));
                        wiseSayingDelete(id);
                    } else if (command.startsWith("수정?id=")) {
                        int id = Integer.parseInt(command.substring(6));
                        wiseSayingUpdate(id);
                    } else {
                        System.out.println("다시 입력해 주세요.");
                    }
                    break;
            }
        }
    }

    private void wiseSayingUpdate(int targetId) {
        WiseSaying wiseSaying = wiseSayingFind(targetId);

        if (wiseSaying == null) {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(targetId));
            return;
        }
        System.out.println("명언(기존) : %s".formatted(wiseSaying.getContent()));
        System.out.print("명언 : ");
        String newContent = sc.nextLine();
        System.out.println("작가(기존) : %s".formatted(wiseSaying.getAuthor()));
        System.out.print("작가 : ");
        String newAuthor = sc.nextLine();

        wiseSaying.setContent(newContent);
        wiseSaying.setAuthor(newAuthor);

        System.out.println("%d번 명언이 수정되었습니다.".formatted(targetId));
    }

    private WiseSaying wiseSayingFind(int targetId) {
        for (WiseSaying wiseSaying : wiseSayingList) {
            if (wiseSaying.getId() == targetId) {
                return wiseSaying;
            }
        }
        return null;
    }

    private void wiseSayingDelete(int targetId) {
        WiseSaying wiseSaying = wiseSayingFind(targetId);
        if (wiseSaying == null) {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(targetId));
            return;
        }
        wiseSayingList.remove(wiseSaying);
        System.out.println("%d번 명언이 삭제되었습니다.".formatted(targetId));
    }

    private void wiseSayingInventory() { // 목록
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");
        for (WiseSaying wiseSaying : wiseSayingList.reversed()) {
            System.out.println("%d / %s / %s".formatted(wiseSaying.getId(), wiseSaying.getAuthor(), wiseSaying.getContent()));
        }
    }

    private void wiseSayingRegister() { // 등록
        System.out.print("명언 : ");
        content = sc.nextLine();

        System.out.print("작가 : ");
        author = sc.nextLine();

        wiseSayingAdd(content, author);

        System.out.println("%d번 명언이 종료되었습니다.".formatted(lastId));
    }

    public void wiseSayingAdd(String content, String author) { // 명언 등록
        WiseSaying wiseSaying = new WiseSaying(++lastId, content, author);
        wiseSayingList.add(wiseSaying);
    }
}