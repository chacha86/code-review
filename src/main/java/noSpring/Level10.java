package noSpring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Level10 {
    private static final String DB_PATH = "db/wiseSaying/";
    private static final String LAST_ID_FILE = DB_PATH + "lastId.txt";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("== 명언 앱 ==");

        List<Quote> quotes = loadQuotes();
        int cnt = loadLastId();

        while (true) {
            System.out.print("명령) ");
            String command = sc.nextLine().trim();

            switch (command.split("\\?")[0]) {
                case "종료": {
                    saveLastId(cnt);
                    return;
                }
                case "등록": {
                    System.out.print("명언 : ");
                    String quote = sc.nextLine().trim();

                    System.out.print("작가 : ");
                    String author = sc.nextLine().trim();

                    Quote newQuote = new Quote(cnt++, quote, author);
                    quotes.add(newQuote);
                    saveQuoteToFile(newQuote);

                    System.out.printf("%d번 명언이 등록되었습니다.\n", newQuote.getId());
                    break;
                }
                case "목록": {
                    System.out.println("번호 / 작가 / 명언");
                    System.out.println("----------------------");
                    quotes.stream()
                            .sorted(Comparator.comparingInt(Quote::getId).reversed())
                            .forEach(System.out::println);
                    break;
                }
                case "삭제": {
                    String data = command.split("\\?")[1]; // id=1
                    int findId = Integer.parseInt(data.split("=")[1]);

                    boolean flag = false;

                    for (int i = 0; i < quotes.size(); i++) {
                        if (quotes.get(i).getId() == findId) {
                            quotes.remove(i);
                            deleteQuoteFile(findId);
                            System.out.printf("%d번 명언이 삭제되었습니다.\n", findId);
                            flag = true;
                            break;
                        }
                    }

                    if (!flag) {
                        System.out.printf("%d번 명언은 존재하지 않습니다.\n", findId);
                    }
                    break;
                }
                case "수정": {
                    String data = command.split("\\?")[1]; // id=1
                    int findId = Integer.parseInt(data.split("=")[1]);

                    boolean flag = false;

                    for (int i = 0; i < quotes.size(); i++) {
                        Quote curQuote = quotes.get(i);
                        if (curQuote.getId() == findId) {
                            System.out.println("명언(기존) : " + curQuote.getContent());
                            System.out.print("명언 : ");
                            String newContent = sc.nextLine().trim();

                            System.out.println("작가(기존) : " + curQuote.getAuthor());
                            System.out.print("작가 : ");
                            String newAuthor = sc.nextLine().trim();

                            curQuote.setContent(newContent);
                            curQuote.setAuthor(newAuthor);
                            saveQuoteToFile(curQuote);
                            flag = true;
                            break;
                        }
                    }

                    if (!flag) {
                        System.out.printf("%d번 명언은 존재하지 않습니다.\n", findId);
                    }
                    break;
                }
                case "빌드" : {
                    makeDataJson(quotes);
                }
                default:
                    break;
            }
        }
    }

    private static void makeDataJson(List<Quote> quotes) {
        File file=new File(DB_PATH+"data.json");
        try {
            objectWriter.writeValue(file,quotes);
            System.out.println("data.json 파일의 내용이 갱신되었습니다.");
        }
        catch (IOException e) {
            System.out.println("data.json 생성 실패");
        }
    }

    private static List<Quote> loadQuotes() {
        List<Quote> quotes = new ArrayList<>();
        File dir = new File(DB_PATH);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".json"));

        for (File file : files) {
            try {
                Quote quote = objectMapper.readValue(file, Quote.class);
                quotes.add(quote);
            } catch (IOException e) {
                System.out.println("파일 로드 실패: " + file.getName());
            }
        }
        return quotes;
    }

    private static int loadLastId() {
        Path path = Path.of(LAST_ID_FILE);
        if (Files.exists(path)) {
            try {
                String content = Files.readString(path).trim();
                return Integer.parseInt(content) + 1;
            } catch (IOException e) {
                System.out.println("lastId 파일 로드 실패");
            }
        }
        return 1;
    }

    private static void saveLastId(int lastId) {
        try {
            Files.writeString(Path.of(LAST_ID_FILE), String.valueOf(lastId - 1));
        } catch (IOException e) {
            System.out.println("lastId 저장 실패");
        }
    }

    private static void saveQuoteToFile(Quote quote) {
        File file = new File(DB_PATH + quote.getId() + ".json");
        try {
            objectWriter.writeValue(file, quote);
        } catch (IOException e) {
            System.out.println("파일 저장 실패: " + file.getName());
        }
    }

    private static void deleteQuoteFile(int id) {
        File file = new File(DB_PATH + id + ".json");
        if (file.exists() && !file.delete()) {
            System.out.println("파일 삭제 실패: " + file.getName());
        }
    }
}