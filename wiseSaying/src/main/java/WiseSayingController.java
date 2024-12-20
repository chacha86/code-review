import java.util.Scanner;

public class WiseSayingController {
    private final Scanner scanner;
    private final WiseSayingService service;

    public WiseSayingController(Scanner scanner, WiseSayingService service) {
        this.scanner = scanner;
        this.service = service;
    }

    public void register() {
        System.out.print("명언 : ");

        String content = scanner.nextLine();
        System.out.print("작가 : ");
        String author = scanner.nextLine();

        int id = service.addOne(content, author);
        System.out.println(id + "번 명언이 등록되었습니다.");
    }

    public void getList() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");
        service.getAll().forEach(System.out::println);
    }

    public void delete(String command) {
        String[] commandParts = command.split("=");
        if (commandParts.length == 2 && commandParts[0].equals("삭제?id")) {
            try {
                int id = Integer.parseInt(commandParts[1]);
                boolean deleted = service.deleteOne(id);
                if (deleted) {
                    System.out.println(id + "번 명언이 삭제되었습니다.");
                } else {
                    System.out.println(id + "번 명언은 존재하지 않습니다.");
                }
            } catch (NumberFormatException e){
                System.out.println("잘못된 id 입니다. 정수를 입력해주세요.");
            }
        } else {
            System.out.println("삭제 명령 형식이 잘못되었습니다. 예시: 삭제?id=1");
        }
    }

    public void update(String command) {
        String[] commandParts = command.split("=");
        if (commandParts.length == 2 && commandParts[0].equals("수정?id")) {
            try {
                int id = Integer.parseInt(commandParts[1]);
                WiseSaying wiseSaying = service.getOne(id);
                if (wiseSaying == null) {
                    System.out.println(id + "번 명언은 존재하지 않습니다.");
                } else {
                    System.out.println("명언(기존) : "+wiseSaying.getContent());
                    System.out.print("명언 : ");
                    String newContent = scanner.nextLine();
                    wiseSaying.setContent(newContent);

                    System.out.println("작가(기존) : "+wiseSaying.getAuthor());
                    System.out.print("작가 : ");
                    String newAuthor = scanner.nextLine();
                    wiseSaying.setAuthor(newAuthor);

                    service.updateOne(wiseSaying);
                }
            } catch (NumberFormatException e){
                System.out.println("잘못된 id 입니다. 정수를 입력해주세요.");
            }
        } else {
            System.out.println("수정 명령 형식이 잘못되었습니다. 예시: 수정?id=1");
        }
    }

    public void build() {
        service.build();
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }
}
