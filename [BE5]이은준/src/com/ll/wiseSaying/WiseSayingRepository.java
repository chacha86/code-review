package com.ll.wiseSaying;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WiseSayingRepository {
    private static final String BASE_PATH = "db/wiseSaying/";

    public WiseSayingRepository() {
        File folder = new File(BASE_PATH);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public void save(WiseSaying wiseSaying) {
        try {
            File file = new File(BASE_PATH + wiseSaying.getId() + ".json");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("{\n");
                writer.write("\"id\": " + wiseSaying.getId() + ",\n");
                writer.write("\"content\": \"" + wiseSaying.getContent() + "\",\n");
                writer.write("\"author\": \"" + wiseSaying.getAuthor() + "\"\n");
                writer.write("}");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save wise saying", e);
        }
    }

    public WiseSaying findById(int id) {
        File file = new File(BASE_PATH + id + ".json");
        if (!file.exists()) return null;
        return loadFromFile(file);
    }

    public List<WiseSaying> findAll() {
        List<WiseSaying> wiseSayings = new ArrayList<>();
        File folder = new File(BASE_PATH);
        File[] files = folder.listFiles((dir, name) -> name.matches("\\d+\\.json"));

        if (files != null) {
            for (File file : files) {
                WiseSaying wiseSaying = loadFromFile(file);
                if (wiseSaying != null) {
                    wiseSayings.add(wiseSaying);
                }
            }
        }
        return wiseSayings;
    }

    public boolean delete(int id) {
        File file = new File(BASE_PATH + id + ".json");
        return file.exists() && file.delete();
    }

    private WiseSaying loadFromFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            return parseJson(json.toString());
        } catch (IOException e) {
            return null;
        }
    }

    private WiseSaying parseJson(String json) {
        try {
            json = json.trim().replace("{", "").replace("}", "").replace("\n", "").replace("\r", "");
            String[] lines = json.split(",");
            int id = Integer.parseInt(lines[0].split(":")[1].trim());
            String content = lines[1].split(":")[1].trim().replace("\"", "");
            String author = lines[2].split(":")[1].trim().replace("\"", "");
            return new WiseSaying(id, content, author);
        } catch (Exception e) {
            return null;
        }
    }

    public int getLastId() {
        File file = new File(BASE_PATH + "lastId.txt");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                return Integer.parseInt(reader.readLine().trim());
            } catch (IOException | NumberFormatException e) {
                return 1;
            }
        }
        return 1;
    }

    public void saveLastId(int lastId) {
        try {
            File file = new File(BASE_PATH + "lastId.txt");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(String.valueOf(lastId));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save last ID", e);
        }
    }

    public void buildData() {
        try {
            List<WiseSaying> wiseSayings = findAll();
            File dataFile = new File(BASE_PATH + "data.json");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile))) {
                writer.write("[\n");
                for (int i = 0; i < wiseSayings.size(); i++) {
                    WiseSaying ws = wiseSayings.get(i);
                    writer.write("  {\n");
                    writer.write("    \"id\": " + ws.getId() + ",\n");
                    writer.write("    \"content\": \"" + ws.getContent() + "\",\n");
                    writer.write("    \"author\": \"" + ws.getAuthor() + "\"\n");
                    writer.write("  }");
                    if (i < wiseSayings.size() - 1) writer.write(",");
                    writer.write("\n");
                }
                writer.write("]\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to build data", e);
        }
    }
}