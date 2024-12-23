import com.google.gson.*;
import java.io.*;
import java.util.*;

public class App {
    private Scanner scanner = new Scanner(System.in); // 사용자 입력을 받기 위한 Scanner 객체
    private Map<Integer, WiseSaying> sayings = new HashMap<>(); // 명언을 저장하는 Map

    public void run() {
        System.out.println("== 명언 앱 ==");
        while (true) {
            System.out.print("명령) ");
            String input = scanner.nextLine().trim(); // 사용자 명령어 입력

            boolean isDeleteOrUpdate = input.startsWith("삭제") || input.startsWith("수정");

            // "삭제" 또는 "수정" 명령어 처리
            if (isDeleteOrUpdate) {
                processModifyCommand(input);
                continue;
            }

            // 나머지 명령어 처리
            switch (input) {
                case "등록":
                    addSaying(); // 명언 등록
                    break;
                case "목록":
                    showSayings(); // 명언 목록 출력
                    break;
                case "종료":
                    System.exit(0); // 프로그램 종료
                    break;
                default:
                    System.out.println("알 수 없는 명령어입니다. 다시 입력해주세요.");
                    break;
            }
        }
    }

    // 삭제 및 수정 명령어 처리
    private void processModifyCommand(String command) {
        String action = command.startsWith("삭제") ? "삭제" : "수정";

        String[] parts = command.split("\\?"); // "삭제" 또는 "수정" 명령어와 매개변수 분리
        boolean isValidCommand = parts.length > 1 && parts[1].startsWith("id=");

        // 명령어 형식이 유효하지 않을 경우 처리
        if (!isValidCommand) {
            System.out.println("잘못된 명령어 형식입니다. 명령어 예시 : " + action + "?id=1");
            return;
        }

        int id = Integer.parseInt(parts[1].substring(3)); // ID 추출
        boolean isId = sayings.containsKey(id); // ID가 존재하는지 확인

        // 해당 ID가 존재하는지 확인
        if (!isId) {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
            return;
        }

        // "삭제"와 "수정"에 따른 처리 분기
        if (action.equals("삭제")) {
            sayings.remove(id);
            System.out.println(id + "번 명언이 삭제되었습니다.");
        } else {
            updateSaying(id);
        }
    }

    // 명언 수정 처리
    private void updateSaying(int id) {
        WiseSaying targetSaying = sayings.get(id); // 수정할 명언 조회

        System.out.println("명언(기존) : " + targetSaying.getQuote() + "\n명언 : ");
        targetSaying.setQuote(scanner.nextLine().trim()); // 새로운 명언 입력

        System.out.println("작가(기존) : " + targetSaying.getAuthor() + "\n작가 : ");
        targetSaying.setAuthor(scanner.nextLine().trim()); // 새로운 작가 입력
    }

    // 명언 등록 처리
    private void addSaying() {
        System.out.print("명언 : ");
        String quote = scanner.nextLine().trim(); // 명언 입력

        System.out.print("작가 : ");
        String author = scanner.nextLine().trim(); // 작가 입력

        WiseSaying newSaying = new WiseSaying(quote, author); // 새로운 명언 객체 생성
        sayings.put(newSaying.getId(), newSaying); // Map에 추가

        System.out.println(newSaying.getId() + "번 명언이 등록되었습니다.");
    }

    // 명언 목록 출력
    private void showSayings() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        if (sayings.isEmpty()) {
            System.out.println("등록된 명언이 없습니다."); // 등록된 명언이 없을 경우
        } else {
            for (WiseSaying saying : sayings.values()) {
                saying.display(); // 명언 출력
            }
        }
    }
}

// 명언 클래스
class WiseSaying {
    private static int idCounter = 0; // ID 자동 증가를 위한 정적 변수
    private int id; // 명언 ID
    private String quote; // 명언 텍스트
    private String author; // 작가 이름

    public WiseSaying(String quote, String author) {
        this.id = ++idCounter; // ID 자동 할당
        this.quote = quote;
        this.author = author;
    }

    // 명언 출력 메서드
    public void display() {
        System.out.println(id + " / " + quote + " / " + author);
    }

    // ID getter
    public int getId() {
        return id;
    }

    // 명언 getter/setter
    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    // 작가 getter/setter
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
