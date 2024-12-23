package com.ll.wiseSaying.testDrivenDevelopment;

import com.ll.wiseSaying.basicDevelopment.WiseSaying;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class TddWiseSayingRepository {

    private final static TddWiseSayingRepository instance = new TddWiseSayingRepository();
    private TddWiseSayingRepository() {
        File folder = new File(DBPATH);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // loadAllFromFile();
    }
    public static synchronized TddWiseSayingRepository getInstance() {
        return instance;
    }

    private static final String DBPATH = "db/wiseSaying";
    private List<WiseSaying> wiseSayingList = new ArrayList<>();
    private int lastId = 0;

    public int registerWiseSaying(String author, String content) {
        lastId++;
        saveLastIdToFile();
        saveWiseSayingToFile(lastId, author, content);
        return lastId;
    }

    private void saveWiseSayingToFile(int id, String author, String content) {
        WiseSaying wiseSaying = new WiseSaying(id, author, content);
        wiseSayingList.add(wiseSaying);

        try {
            File file = new File(DBPATH + "/" + id + ".json");
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            String json = "{\n  \"id\": " + id
                    + ",\n  \"content\": \"" + content
                    + "\",\n  \"author\": \"" + author + "\"\n}";
            bw.write(json);
            bw.flush();
            bw.close();
        } catch (IOException ignored) {
        }
    }

    private void saveLastIdToFile() {
        try {
            Files.write(new File(DBPATH + "/lastId.txt").toPath(), String.valueOf(lastId).getBytes());
        } catch (IOException ignored) {
        }
    }

    /*private void loadAllFromFile() {
        File lastIdFile = new File(DBPATH + "/lastId.txt");
        if (lastIdFile.exists()) {
            try {
                lastId = Integer.parseInt(new String(readAllBytes(lastIdFile.toPath())));
            } catch (IOException ignored) {
            }
        }

        File folder = new File(DBPATH);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json") && !name.equals("lastId.txt"));

        if (files != null) {
            for (File file : files) {
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String json = br.readLine();

                    String id = json.substring(json.indexOf("\"id\": ") + 1, json.indexOf(",")).trim();
                    String content = json.substring(json.indexOf("\"content\": \""), json.indexOf("\"author\"")).trim();
                    String author = json.substring(json.indexOf("\"author\": \""), json.lastIndexOf("\""));

                    WiseSaying wiseSaying = new WiseSaying(Integer.parseInt(id), author, content);
                    wiseSayingList.add(wiseSaying);
                } catch (IOException ignored) {
                }
            }
        }
    }*/
}
