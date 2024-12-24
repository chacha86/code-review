package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class App {
    private final List<Quote> quotes = new ArrayList<>();
    private final String dbPath = "db/wiseSaying";
    private int lastId = 0;

    public void run() throws IOException {
        System.out.println("== 명언 앱 ==");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        initDatabase();

        while (true) {
            System.out.print("명령) ");
            String command = br.readLine();

            if (command.equals("종료")) {
                System.out.println("명언 앱을 종료합니다.");
                break;
            } else if (command.equals("등록")) {
                createQuote(br);
            } else if (command.equals("목록")) {
                printQuotes();
            } else if (command.startsWith("삭제?id=")) {
                int id = Integer.parseInt(command.split("=")[1]);
                deleteQuoteById(id);
            } else if (command.startsWith("수정?id=")) {
                int id = Integer.parseInt(command.split("=")[1]);
                updateQuoteById(id, br);
            }
        }
    }

    /// 파일 영속성
    public void initDatabase() throws IOException {
        Files.createDirectories(Paths.get(dbPath));

        File lastIdFile = new File(dbPath, "lastId.txt");
        if (lastIdFile.exists()) {
            lastId = Integer.parseInt(Files.readString(lastIdFile.toPath()).trim());
        }

        File[] files = new File(dbPath).listFiles(((dir, name) -> name.endsWith(".json")));
        if (files != null) {
            for (File file : files) {
                String q = Files.readString(file.toPath());
                Quote quote = parseQuote(q);
                if (quote != null) {
                    quotes.add(quote);
                }
            }
        }
    }

    /// JSON -> Quote
    public Quote parseQuote(String q) {
        q = q.trim();
        String[] lines = q.split("\n");

        int id = Integer.parseInt(lines[1].split(":")[1].trim().replace(",", ""));
        String author = lines[2].split(":")[1].trim().replace("\"", "").replace(",", "");
        String content = lines[3].split(":")[1].trim().replace("\"", "").replace("}", "");

        return new Quote(id, author, content);
    }

    /// 명언 등록
    public void createQuote(BufferedReader br) throws IOException {
        System.out.print("명언: ");
        String content = br.readLine();
        System.out.print("작가: ");
        String author = br.readLine();
        lastId++;
        Quote quote = new Quote(lastId, author, content);
        quotes.add(quote);
        saveQuoteToFile(quote);
        saveLastId();

        System.out.println(lastId + "번 명언이 등록되었습니다.");
    }

    /// Quote -> File
    public void saveQuoteToFile(Quote quote) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("\"id\": ").append(quote.getId()).append(",\n");
        sb.append("\"author\": \"").append(quote.getAuthor()).append("\",\n");
        sb.append("\"content\": \"").append(quote.getContent()).append("\"\n");
        sb.append("}");

        File file = new File(dbPath, quote.getId() + ".json");
        Files.writeString(file.toPath(), sb.toString());
    }

    /// LastId -> File
    public void saveLastId() throws IOException {
        File lastIdFile = new File(dbPath, "lastId.txt");
        Files.writeString(lastIdFile.toPath(), String.valueOf(lastId));
    }

    /// 명언 조회
    public void printQuotes() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");
        for (int i = quotes.size() - 1; i >= 0; i--) { // 최신 순으로 출력
            Quote quote = quotes.get(i);
            System.out.printf("%d / %s / %s%n", quote.getId(), quote.getAuthor(), quote.getContent());
        }
    }

    /// 명언 수정
    public void updateQuoteById(int id, BufferedReader br) throws IOException {
        if (id == -1) return;

        for (Quote quote : quotes) {
            if (quote.getId() == id) {
                System.out.println("명언(기존): " + quote.getContent());
                System.out.print("명언: ");
                String newContent = br.readLine();
                System.out.println("작가(기존): " + quote.getAuthor());
                System.out.print("작가: ");
                String newAuthor = br.readLine();

                quote.setContent(newContent);
                quote.setAuthor(newAuthor);
                saveQuoteToFile(quote);

                System.out.println(id + "번 명언이 수정되었습니다.");
                return;
            }
        }
        System.out.println(id + "번 명언은 존재하지 않습니다.");
    }

    /// 명언 삭제
    public void deleteQuoteById(int id) {
        if (id == -1) return;

        for (int i = 0; i < quotes.size(); i++) {
            if (quotes.get(i).getId() == id) {
                quotes.remove(i);
                File file = new File(dbPath, id + ".json");
                if (file.exists()) {
                    file.delete();
                }

                System.out.println(id + "번 명언이 삭제되었습니다.");
                return;
            }
        }
        System.out.println(id + "번 명언은 존재하지 않습니다.");
    }
}
