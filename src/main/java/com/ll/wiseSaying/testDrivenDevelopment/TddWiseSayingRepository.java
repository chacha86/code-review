package com.ll.wiseSaying.testDrivenDevelopment;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.nio.file.Files.readAllBytes;

public class TddWiseSayingRepository {

    public TddWiseSayingRepository() {
        File folder = new File(DBPATH);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        loadAllFromFile();
    }

    private static final String DBPATH = "db/wiseSaying";
    private static final String LAST_ID_PATH = DBPATH + "/lastId.txt";
    private final List<TddWiseSaying> wiseSayingList = new ArrayList<>();
    private int lastId = 0;

    public int registerWiseSaying(String author, String content) {
        lastId++;
        TddWiseSaying wiseSaying = new TddWiseSaying(lastId, author, content);
        wiseSayingList.add(wiseSaying);
        wiseSayingList.sort(Comparator.comparingInt(TddWiseSaying::getId).reversed());

        saveLastIdToFile();
        saveWiseSayingToFile(wiseSaying);

        return lastId;
    }

    public TddPage<TddWiseSaying> findAll(int pageNum, int pageSize) {
        int start = Math.min((pageNum-1) * pageSize, wiseSayingList.size());
        int end = Math.min(start + pageSize, wiseSayingList.size());

        List<TddWiseSaying> result = wiseSayingList.subList(start, end);
        return new TddPage<>(result, result.size() / pageSize + 1, pageNum);
    }

    public TddPage<TddWiseSaying> findAllByContent(int pageNum, int pageSize, String content) {
        int start = (pageNum-1) * pageSize;
        int end = Math.min(start + pageSize, wiseSayingList.size());

        List<TddWiseSaying> result = wiseSayingList.stream()
                .filter(w -> w.getContent().contains(content))
                .toList().subList(start, end);

        return new TddPage<>(result, result.size() / pageSize + 1, pageNum);
    }

    public TddPage<TddWiseSaying> findAllByAuthor(int pageNum, int pageSize, String author) {
        int start = (pageNum-1) * pageSize;
        int end = Math.min(start + pageSize, wiseSayingList.size());

        List<TddWiseSaying> result = wiseSayingList.stream()
                .filter(w -> w.getAuthor().contains(author))
                .toList().subList(start, end);

        return new TddPage<>(result, result.size() / pageSize + 1, pageNum);
    }

    private void saveWiseSayingToFile(TddWiseSaying wiseSaying) {
        try {
            File file = new File(DBPATH + "/" + wiseSaying.getId() + ".json");
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            String json = "{\n  \"id\": " + wiseSaying.getId()
                    + ",\n  \"content\": \"" + wiseSaying.getContent()
                    + "\",\n  \"author\": \"" + wiseSaying.getAuthor() + "\"\n}";
            bw.write(json);
            bw.flush();
            bw.close();
        } catch (IOException ignored) {
        }
    }

    private void saveLastIdToFile() {
        try {
            Files.write(new File(LAST_ID_PATH).toPath(), String.valueOf(lastId).getBytes());
        } catch (IOException ignored) {
        }
    }

    private void loadAllFromFile() {
        File lastIdFile = new File(LAST_ID_PATH);

        if (lastIdFile.exists()) {
            try {
                lastId = Integer.parseInt(new String(readAllBytes(lastIdFile.toPath())));
            } catch (IOException ignored) {
            }
        }

        File folder = new File(DBPATH);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));

        if (files != null) {
            for (File file : files) {
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String json = br.readLine();

                    json = br.readLine();
                    String id = json.substring(json.indexOf(":") + 2, json.indexOf(",")).trim();
                    json = br.readLine();
                    String content = json.substring(json.indexOf(":") + 3, json.indexOf("\",")).trim();
                    json = br.readLine();
                    String author = json.substring(json.indexOf(":") + 3, json.lastIndexOf("\""));

                    TddWiseSaying wiseSaying = new TddWiseSaying(Integer.parseInt(id), author, content);
                    wiseSayingList.add(wiseSaying);
                } catch (IOException ignored) {
                }
            }

            wiseSayingList.sort(Comparator.comparingInt(TddWiseSaying::getId).reversed());
        }
    }
}
