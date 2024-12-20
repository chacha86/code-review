package wiseSaying10;

import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;

public class Main {
    static Map<Integer, Quote> quotes = new HashMap<>();
    static int n = 0;
    static Scanner sc = new Scanner(System.in);
    static StringBuilder sb = new StringBuilder();

    static String dirName = "db/wiseSaying/";
    public static void main(String[] args) {
        System.out.println("== 명언 앱 ==");
        boolean continueFlag = true;

        while(continueFlag) {
            System.out.printf("명령) ");
            String[] command = sc.nextLine().split("\\?");
            int id=0;
            if(command.length > 1){
                id = Integer.parseInt(command[1].split("=")[1]);
            }
            switch (command[0]) {
                case "등록":
                    addQuote();
                    break;
                case "목록":
                    showQuote();
                    break;
                case "삭제":
                    deleteQuote(id);
                    break;
                case "수정":
                    modifyQuote(id);
                    break;
                case "종료":
                    sc.close();
                    continueFlag = false;
                    saveLastId();
                    break;
                case "빌드":
                    buildJson();
                    break;
                default:
                    System.out.println("알 수 없는 명령어입니다.");
            }
        }
    }

    public static void addQuote() {
        n++;
        System.out.printf("명언: ");
        String quote = sc.nextLine();
        System.out.printf("작가: ");
        String author = sc.nextLine();
        Quote q = new Quote(n, quote, author);
        quotes.put(n, q);
        saveQuoteAsFile(q);
        System.out.println(n + "번 명언이 등록되었습니다.");
    }
    public static void saveQuoteAsFile(Quote q) {
        String fileName = String.format("%s%d.json", dirName, q.id);
        saveFile(fileName, q.toJson().toJSONString());
    }
    public static void deleteQuote(int id) {
        if (quotes.get(id) == null) {
            System.out.printf("%d번 명언은 존재하지 않습니다. %n", id);
        } else {
            quotes.remove(id);
            System.out.println(id + "번째 명언이 삭제되었습니다.");
        }
    }
    public static void showQuote() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");
        for (int i : quotes.keySet()) {
            String result = sb.append(quotes.get(i).id).append(" / ").append(quotes.get(i).getAuthor()).append(" / ").append(quotes.get(i).getQuote()).toString();
            System.out.println(result);
            sb.setLength(0);
        }
    }

    public static void modifyQuote(int id) {
        if(quotes.get(id) == null) {
            System.out.printf("%d번 명언은 존재하지 않습니다. %n", id);
            return;
        }

        System.out.printf("명언(기존): %s%n", quotes.get(id).getQuote());
        System.out.printf("명언: ");
        String quote2 = sc.nextLine();
        System.out.printf("작가(기존): %s%n", quotes.get(id).getAuthor());
        System.out.printf("작가: ");
        String author2 = sc.nextLine();

        quotes.put(id, new Quote(id, quote2, author2));
        System.out.println(id + "번 명언이 수정되었습니다.");
    }

    public static void saveLastId() {
        String fileName = dirName + "lastId.txt";
        saveFile(fileName, String.valueOf(n));
    }

    public static void  buildJson() {
        String fileName = dirName + "data.json";
            JSONArray jsonList = new JSONArray();
            for (int i : quotes.keySet()) {
                jsonList.add(quotes.get(i).toJson());
            }
            saveFile(fileName, jsonList.toJSONString());
            System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }

    public static void saveFile(String fileName, String content) {
        try {
            FileWriter file = new FileWriter(fileName);
            file.write(content);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
