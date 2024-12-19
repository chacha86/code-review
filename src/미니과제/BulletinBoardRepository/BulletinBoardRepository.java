package src.미니과제.BulletinBoardRepository;

import 미니과제.Proverb;

import java.io.*;
import java.util.*;

public class BulletinBoardRepository {
    private static final String DB = "src/미니과제/db/";
    private static final String LAST_ID_FILE = DB + "lastId.txt";
    private static final String DATA_PROVERB_FILE = DB + "data.json";

    public int readLastId() {
        File file = new File(LAST_ID_FILE);
        if (!file.exists()) return 1;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            return Integer.parseInt(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public void saveLastId(int id) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(LAST_ID_FILE))) {
            bw.write(String.valueOf(id));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Proverb> loadProverbs() {
        List<Proverb> proverbs = new ArrayList<>();
        File folder = new File(DB);

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));
        if (files != null) {
            for (File file : files) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String json = br.readLine(); // 한 줄만 읽기
                    // JSON 형식에서 id, content, author 추출
                    try {
                        String idString = json.substring(json.indexOf(":") + 1, json.indexOf(",")).trim();
                        String proverb = json.substring(json.indexOf("proverb") + 11, json.indexOf(",", json.indexOf("proverb")) - 1).trim();
                        String author = json.substring(json.indexOf("author") + 11, json.lastIndexOf("\"")).trim();

                        int id = Integer.parseInt(idString);
                        proverbs.add(new Proverb(id, proverb, author));
                    } catch (StringIndexOutOfBoundsException e) {
                        System.out.println("JSON 형식이 잘못되었습니다: " + json);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return proverbs;
    }

    public void saveProverb(Proverb proverb) {
        File file = new File(DB + proverb.getId() + ".json");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            String json = "{ \"id\": " + proverb.getId() + ", \"proverb\": \"" + proverb.getProverb() + "\", \"author\": \"" + proverb.getAuthor() + "\" }";
            bw.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteProverbFile(int id) {
        File file = new File(DB + id + ".json");
        if (file.exists()) file.delete();
    }

    public void saveProverbsToData(List<Proverb> proverbsList) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DATA_PROVERB_FILE))) {
            bw.write("[\n");
            for (int i = 0; i < proverbsList.size(); i++) {
                Proverb proverb = proverbsList.get(i);
                String json = "  {\n" +
                        "    \"id\": " + proverb.getId() + ",\n" +
                        "    \"proverb\": \"" + proverb.getProverb() + "\",\n" +
                        "    \"author\": \"" + proverb.getAuthor() + "\"\n" +
                        "  }";
                bw.write(json);
                if (i < proverbsList.size() - 1) {
                    bw.write(",\n");
                }
            }
            bw.write("\n]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
