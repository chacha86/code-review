import java.util.*;

class App {
    private List<Wise> wiseList = new ArrayList<>(); // 모든 명언을 저장할 리스트
    private Map<Integer, Wise> wiseMap = new HashMap<>(); // ID로 명언을 빠르게 찾기 위한 맵

    public void run() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");
            String command = scanner.nextLine();

            switch (command.split("\\?")[0]) {
                case "종료":
                    System.out.println("명언 앱을 종료합니다.");
                    return;

                case "등록":
                    registerWise(scanner);
                    break;

                case "목록":
                    printWiseList();
                    break;

                case "삭제":
                    handleCommand(command, this::deleteWiseById);
                    break;

                case "수정":
                    handleCommand(command,id -> updateWiseById(scanner, id));
                    break;

                default:
                    System.out.println("알 수 없는 명령어입니다.");
            }
        }
    }

    private void registerWise(Scanner scanner) {    // 등록 메뉴
        String content = "";
        String author = "";

        System.out.print("명언 : ");
        content = scanner.nextLine(); // 입력값 가져옴. 입력값이 없으면 기다린다. 엔터를 쳐야 입력이 완료됨. 그래야 넘어감

        System.out.print("작가 : ");
        author = scanner.nextLine();

        Wise wise = new Wise(content, author);
        wiseList.add(wise); // 리스트에 명언 저장
        wiseMap.put(wise.getId(), wise); // 맵에 명언 id 저장

        System.out.println(wise.getId() + "번 명언이 등록되었습니다.");
    }

    private void printWiseList() {  // 목록 메뉴
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        // 최신 등록된 명언부터 출력
        for (int i = wiseList.size() - 1; i >= 0; i--) {
            Wise wise = wiseList.get(i);
            System.out.println(wise.getId() + " / " + wise.getAuthor() + " / " + wise.getWise());
        }
        if (wiseList.isEmpty()) {
            System.out.println("등록된 명언이 없습니다.");
        }
    }

    private void deleteWiseById(int id) {   // 삭제 메뉴
        Wise wise = wiseMap.remove(id);
        if (wise != null) { // 명언이 존재할 경우
            wiseList.remove(wise);
            wise = null; // 객체 버림, gc가 메모리 회수
            System.out.println("%d번 명언이 삭제되었습니다.".formatted(id));
        }
        else {  // 명언이 존재하지 않을 경우
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
        }
    }

    private void updateWiseById(Scanner scanner, int id) {   // 수정 메뉴
        Wise wise = wiseMap.get(id);
        if (wise != null) { // 명언이 존재할 경우
            System.out.println("명언(기존) : " + wise.getWise());
            System.out.print("명언 : ");
            String newWise = scanner.nextLine();
            wise.setWise(newWise);

            System.out.println("작가(기존) : " + wise.getAuthor());
            System.out.print("작가 : ");
            String newAuthor = scanner.nextLine();
            wise.setAuthor(newAuthor);
        }
        else {  // 명언이 존재하지 않을 경우
            System.out.println(id + "번 명언은 존재하지 않습니다.");
        }
    }

    private void handleCommand(String command, java.util.function.Consumer<Integer> action) { // 명령어 예외 처리
        try {
            int id = Integer.parseInt(command.split("=")[1]);
            action.accept(id);
        }
        catch (NumberFormatException e) {
            System.out.println("숫자를 입력해주세요.");
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("명령어의 번호를 입력해주세요.");
        }
    }
}