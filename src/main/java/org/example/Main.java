package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        App app = new App();
        app.run();
    }
}

class App {
    private final List<Quote> quotes = new ArrayList<>();

    public void run() throws IOException {
        System.out.println("== 명언 앱 ==");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int cnt = 0;

        while (true) {
            System.out.print("명령) ");
            String command = br.readLine();

            if (command.equals("종료")) {
                System.out.println("명언 앱을 종료합니다.");
                break;

            } else if (command.equals("등록")) {
                System.out.print("명언: ");
                String content = br.readLine();
                System.out.print("작가: ");
                String author = br.readLine();
                cnt++;

                quotes.add(new Quote(cnt, author, content));
                System.out.println(cnt + "번 명언이 등록되었습니다.");

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

    public void printQuotes() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");
        for (int i = quotes.size() - 1; i >= 0; i--) { // 최신 순으로 출력
            Quote quote = quotes.get(i);
            System.out.printf("%d / %s / %s%n", quote.getId(), quote.getAuthor(), quote.getContent());
        }
    }

    public void deleteQuoteById(int id) {
        if (id == -1) return;

        for (int i = 0; i < quotes.size(); i++) {
            if (quotes.get(i).getId() == id) {
                quotes.remove(i);
                System.out.println(id + "번 명언이 삭제되었습니다.");
                return;
            }
        }
        System.out.println(id + "번 명언은 존재하지 않습니다.");
    }

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

                return;
            }
        }
        System.out.println(id + "번 명언은 존재하지 않습니다.");
    }
}

class Quote {
    private final int id;
    private String author;
    private String content;

    public Quote(int id, String author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }
}