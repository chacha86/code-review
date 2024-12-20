package TDD.main.repository;

import TDD.Proverb;

import java.io.*;
import java.util.*;

public class ProverbRepository {
    private static final String DB_FOLDER = "src/미니과제/db/";
    private static final String LAST_ID_FILE = DB_FOLDER + "lastId.txt";
    private static final String DATA_FILE = DB_FOLDER + "data.json";

    public int readLastId() {
        File file = new File(LAST_ID_FILE);
        if (!file.exists()) return 1;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            return Integer.parseInt(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
            return 1;
        }
    }

    public void saveLastId(int id) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(LAST_ID_FILE))) {
            bw.write(String.valueOf(id));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveProverb(Proverb proverb) {
        File file = new File(DB_FOLDER + proverb.getId() + ".json");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            String json = "{ \"id\": " + proverb.getId() + ", \"proverb\": \"" + proverb.getProverb() + "\", \"author\": \"" + proverb.getAuthor() + "\" }";
            bw.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getNextId() {
        int lastId = readLastId();
        saveLastId(lastId + 1);
        return lastId;
    }


    public void deleteProverbFile(int id) {
        File file = new File(DB_FOLDER + id + ".json");
        if (file.exists()) file.delete();
    }

    public List<Proverb> loadProverbs() {
        List<Proverb> proverbs = new ArrayList<>();
        File folder = new File(DB_FOLDER);

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));
        if (files != null) {
            for (File file : files) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String json = br.readLine();
                    if (json == null || json.isBlank()) {
                        System.out.println("빈 파일을 무시합니다: " + file.getName());
                        continue;
                    }

                    try {
                        String idString = json.substring(json.indexOf(":") + 1, json.indexOf(",")).trim();
                        String proverb = json.substring(json.indexOf("proverb") + 11, json.indexOf(",", json.indexOf("proverb")) - 1).trim();
                        String author = json.substring(json.indexOf("author") + 11, json.lastIndexOf("\"")).trim();

                        int id = Integer.parseInt(idString);
                        proverbs.add(new Proverb(id, proverb, author));
                    } catch (StringIndexOutOfBoundsException | NumberFormatException e) {
                        System.out.println("JSON 형식 오류로 파일을 무시합니다: " + file.getName());
                    }
                } catch (IOException e) {
                    System.out.println("파일 읽기 실패: " + file.getName());
                    e.printStackTrace();
                }
            }
        }
        return proverbs;
    }

    public void saveProverbsToData(List<Proverb> proverbsList) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DATA_FILE))) {
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
