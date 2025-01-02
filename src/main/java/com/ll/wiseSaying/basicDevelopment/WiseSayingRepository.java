package com.ll.wiseSaying.basicDevelopment;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import static java.nio.file.Files.readAllBytes;

public class WiseSayingRepository {

    private final static WiseSayingRepository instance = new WiseSayingRepository();
    private WiseSayingRepository() {
        File folder = new File(DBPATH);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        loadAll();
    }
    public static synchronized WiseSayingRepository getInstance() {
        return instance;
    }

    private static final String DBPATH = "db/wiseSaying";

    private int lastId = 0;
    private final List<WiseSaying> wiseSayingList = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public WiseSaying save(String content, String author) {
        lastId++;

        WiseSaying wiseSaying = new WiseSaying(lastId, author, content);
        wiseSayingList.add(wiseSaying);
        saveWiseSayingToFile(wiseSaying);

        saveLastIdToFile();
        return wiseSaying;
    }

    public List<WiseSaying> findAll() {
        wiseSayingList.sort(Comparator.comparingInt(WiseSaying::getId).reversed());
        return wiseSayingList;
    }

    public List<WiseSaying> findAllByContent(String content) {
        return wiseSayingList.stream()
                .filter(w -> w.getContent().contains(content))
                .sorted(Comparator.comparingInt(WiseSaying::getId).reversed())
                .toList();
    }

    public List<WiseSaying> findAllByAuthor(String author) {
        return wiseSayingList.stream()
                .filter(w -> w.getAuthor().contains(author))
                .sorted(Comparator.comparingInt(WiseSaying::getId).reversed())
                .toList();
    }

    public List<WiseSaying> findAllPaginated(int page, int size, List<WiseSaying> resultList) {
        return resultList.stream()
                .sorted(Comparator.comparingInt(WiseSaying::getId).reversed())
                .skip((long) (page - 1) * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    public boolean deleteById(int id) {
        WiseSaying wiseSaying = findById(id);
        if (wiseSaying != null) {
            wiseSayingList.remove(wiseSaying);
            new File(DBPATH + "/" + id + ".json").delete();
            return true;
        }
        return false;
    }

    public WiseSaying findById(int id) {
        return wiseSayingList.stream()
                .filter(w -> w.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void update(int id, String content, String author) {
        WiseSaying wiseSaying = findById(id);
        if (wiseSaying != null) {
            wiseSaying.setContent(content);
            wiseSaying.setAuthor(author);
            saveWiseSayingToFile(wiseSaying);
        }
    }

    public void saveAll() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(DBPATH+"/data.json"), wiseSayingList);
        } catch (IOException e) {
            System.out.println("data 파일 저장 중 오류 : " + e.getMessage());
        }
    }

    private void loadAll() {
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
                    WiseSaying wiseSaying = objectMapper.readValue(file, WiseSaying.class);
                    wiseSayingList.add(wiseSaying);
                } catch (IOException ignored) {
                }
            }
        }
    }

    private void saveWiseSayingToFile(WiseSaying wiseSaying) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(DBPATH+"/"+wiseSaying.getId()+".json"), wiseSaying);
        } catch (IOException e) {
            System.out.println("명언 저장 중 오류 : " + e.getMessage());
        }
    }

    private void saveLastIdToFile() {
        try {
            Files.write(new File(DBPATH+"/lastId.txt").toPath(), String.valueOf(lastId).getBytes());
        } catch (IOException ignored) {
        }
    }
}
