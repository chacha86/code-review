package wiseSaying;

import java.util.Objects;
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

        int newId = service.addOne(content, author).getId();
        System.out.printf("%d번 명언이 등록되었습니다.%n", newId);
    }

    public void printList(int page) {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");
        service.getAll(page);
    }

    public void delete(String command) {
        if (!command.startsWith("삭제?id="))  {
            System.out.println("삭제 명령 형식이 잘못되었습니다. 예시: 삭제?id=1");
            return;
        }
        try {
            int id = Integer.parseInt(command.substring(6));
            boolean isDeleted = service.deleteOne(id);
            if (isDeleted) {
                System.out.println(id + "번 명언이 삭제되었습니다.");
            } else {
                System.out.println(id + "번 명언은 존재하지 않습니다.");
            }
        } catch (NumberFormatException e){
            System.out.println("잘못된 id 입니다. 정수를 입력해주세요.");
        }
    }

    public void update(String command) {
        if (!command.startsWith("수정?id")) {
            System.out.println("수정 명령 형식이 잘못되었습니다. 예시: 수정?id=1");
            return;
        }
        try {
            int id = Integer.parseInt(command.substring(6));
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
    }

    public void build() {
        service.build();
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }

    public void queryList(String command) {
        try {
            String[] queries = command.substring(3).split("&");
            String keyword = null;
            String keywordType = null;
            int page = 1;
            for (String query : queries) {
                String key = query.split("=")[0];
                String value = query.split("=")[1];
                if (key.equals("keyword")) {
                    keyword = value;
                } else if (key.equals("keywordType")) {
                    keywordType = value;
                } else if (key.equals("page")) {
                    page = Integer.parseInt(value);
                }
            }
            if (Objects.isNull(keywordType) && Objects.isNull(keyword)) {
              printList(page);
            } else if (Objects.isNull(keywordType) || Objects.isNull(keyword)) {
                System.out.println("검색 명령 형식이 잘못되었습니다. 예시: 목록?keywordType=content&keyword=검색어");
            } else if (!keywordType.equals("content") && !keywordType.equals("author")) {
                System.out.println("keywordType은 content 또는 author 입니다. 예시: 목록?keywordType=content&keyword=검색어");
            } else {
                System.out.println("----------------------");
                System.out.println("검색타입 : " + keywordType);
                System.out.println("검색어 : " + keyword);
                System.out.println("----------------------");
                System.out.println("번호 / 작가 / 명언");
                System.out.println("----------------------");
                if (keywordType.equals("content")) {
                    service.searchWithContent(keyword, page);
                } else {
                    service.searchWithAuthor(keyword, page);
                }
            }
        } catch (Exception e) {
            System.out.println("파라미터가 누락되었습니다. 예시: 목록?keywordType=content&keyword=검색어");
        }
    }
}
