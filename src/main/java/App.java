import com.google.gson.Gson;

import java.io.*;
import java.util.*;

class App {
    private final Scanner sc = new Scanner(System.in);
    private final Map<Integer, WiseSaying> map = new HashMap<>();
    private int lastId = 0;
    private final String DB_PATH = "db/wiseSaying/";
    private final String LAST_ID_FILE = DB_PATH + "lastId.txt";

    public void run() {
        dbInit();
        loadWiseSaying();

        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");
            String command = sc.nextLine();

            switch (command.split("\\?")[0]) {
                case "종료":
                    saveLastId();
                    return;
                case "등록":
                    registerWiseSaying();
                    break;
                case "목록":
                    listingWiseSaying();
                    break;
                case "삭제":
                    int deleteId = Integer.parseInt(command.split("=")[1]);
                    deleteWiseSaying(deleteId);
                    break;
                case "수정":
                    int updateId = Integer.parseInt(command.split("=")[1]);
                    updateWiseSaying(updateId);
                    break;
                case "빌드":
                    buildWiseSaying();
                    break;
                default:
                    System.out.println("알 수 없는 명령입니다.");
            }
        }
    }

    private void dbInit() {
        File dbDir = new File(DB_PATH);
        if (!dbDir.exists()) {
            dbDir.mkdirs();
        }

        File lastIdFile = new File(LAST_ID_FILE);
        if (!lastIdFile.exists()) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(lastIdFile))) {
                bw.write("0");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadWiseSaying() {
        File dbDir = new File(DB_PATH);
        File[] files = dbDir.listFiles((d, name) -> name.endsWith(".json"));

        if (files != null) {
            for (File file : files) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    WiseSaying wiseSaying = new Gson().fromJson(br, WiseSaying.class);
                    map.put(wiseSaying.getId(), wiseSaying);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader(LAST_ID_FILE))) {
            lastId = Integer.parseInt(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveLastId() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(LAST_ID_FILE))) {
            bw.write(lastId + "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registerWiseSaying() {
        System.out.print("명언 : ");
        String content = sc.nextLine();
        System.out.print("작가 : ");
        String author = sc.nextLine();
        WiseSaying wiseSaying = new WiseSaying(++lastId, content, author);
        map.put(lastId, wiseSaying);
        saveWiseSaying(wiseSaying);
        System.out.printf("%d번 명언이 등록되었습니다.\n", lastId);
    }

    private void listingWiseSaying() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");
        ArrayList<WiseSaying> wiseSayingList = new ArrayList<>(map.values());
        Collections.reverse(wiseSayingList);
        for (WiseSaying wiseSaying : wiseSayingList) {
            System.out.println(wiseSaying);
        }
    }

    private void deleteWiseSaying(int deleteId) {
        try {
            if (map.containsKey(deleteId)) {
                map.remove(deleteId);
                deleteWiseSayingFile(deleteId);
                System.out.println(deleteId + "번 명언이 삭제되었습니다.");
            } else {
                System.out.println(deleteId + "번 명언은 존재하지 않습니다.");
            }
        } catch (Exception e) {
            System.out.println("잘못된 명령입니다. 삭제 명령은 \"삭제?id=번호\" 형식으로 입력하세요.");
        }
    }

    private void deleteWiseSayingFile(int deleteId) {
        File file = new File(DB_PATH + deleteId + ".json");
        if (file.exists()) {
            file.delete();
        }
    }

    private void updateWiseSaying(int updateId) {
        try {
            if (map.containsKey(updateId)) {
                WiseSaying wiseSaying = map.get(updateId);
                System.out.println("명언(기존) : " + wiseSaying.getContent());
                System.out.print("명언 : ");
                String content = sc.nextLine();
                System.out.println("작가(기존) : " + wiseSaying.getAuthor());
                System.out.print("작가 : ");
                String author = sc.nextLine();

                wiseSaying.setContent(content);
                wiseSaying.setAuthor(author);
                saveWiseSaying(wiseSaying);
            } else {
                System.out.println(updateId + "번 명언은 존재하지 않습니다.");
            }
        } catch (Exception e) {
            System.out.println("잘못된 명령입니다. 수정 명령은 \"수정?id=번호\" 형식으로 입력하세요.");
        }
    }

    private void saveWiseSaying(WiseSaying wiseSaying) {
        String filename = DB_PATH + wiseSaying.getId() + ".json";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            String json = new Gson().toJson(wiseSaying);
            bw.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buildWiseSaying() {
        if (map.isEmpty()) {
            System.out.println("등록된 명언이 없습니다.");
            return;
        }

        String filename = DB_PATH + "data.json";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write("[");
            List<WiseSaying> list = new ArrayList<>(map.values());
            for (int i = 0; i < list.size(); i++) {
                String json = new Gson().toJson(list.get(i));
                bw.write(json);
                if (i != list.size() - 1) {
                    bw.write(",\n");
                }
            }
            bw.write("]");

            System.out.println("data.json 파일의 내용이 갱신되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}