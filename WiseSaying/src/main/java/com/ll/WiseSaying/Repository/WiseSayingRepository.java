package com.ll.WiseSaying.Repository;


import com.ll.WiseSaying.Domain.WiseSaying;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ll.WiseSaying.Utils.Constant.*;


public class WiseSayingRepository {
    private final Map<Integer, WiseSaying> wiseSayingMap = new HashMap<>();
    private int lastId = 0;

    public int save(WiseSaying wiseSaying) {
        wiseSaying.setId(++lastId);
        wiseSayingMap.put(wiseSaying.getId(), wiseSaying);
        saveToFile(wiseSaying);
        saveToLastId();
        return wiseSaying.getId();
    }

    public boolean remove(int id) {
        if (wiseSayingMap.containsKey(id)) {
            wiseSayingMap.remove(id);
            return true;
        } else {
            return false;
        }
    }

    public WiseSaying findById(int id) {
        return wiseSayingMap.get(id);
    }

    public List<WiseSaying> findAll() {
        return new ArrayList<>(wiseSayingMap.values());
    }

    public void update(int id, WiseSaying wiseSaying) {
        wiseSayingMap.put(id, wiseSaying);
        WiseSaying w = wiseSayingMap.get(id);
        System.out.println(w.id + w.author + w.content);
        System.out.println(id);
    }

    private void saveToFile(WiseSaying wiseSaying) {
        try {
            String directoryPath = DIR;
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File file = new File(directoryPath + "/" + wiseSaying.getId() + JSON);
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("{\n");
                writer.write("  \"id\": " + wiseSaying.getId() + ",\n");
                writer.write("  \"content\": \"" + wiseSaying.getContent() + "\",\n");
                writer.write("  \"author\": \"" + wiseSaying.getAuthor() + "\"\n");
                writer.write("}");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToLastId() {
        try {
            String filePath = DIR + "lastId" + TXT;
            File file = new File(filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
            }

            try (FileWriter writer = new FileWriter(file)) {
                writer.write(String.valueOf(lastId));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToJson() {
        try {
            String filePath = DIR + "data" + JSON;
            File file = new File(filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
            }

            List<WiseSaying> wiseSayingList = findAll();
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("[\n");
                for (int i = 0; i < wiseSayingList.size(); i++) {
                    WiseSaying ws = wiseSayingList.get(i);
                    writer.write("  {\n");
                    writer.write("    \"id\": " + ws.getId() + ",\n");
                    writer.write("    \"content\": \"" + ws.getContent() + "\",\n");
                    writer.write("    \"author\": \"" + ws.getAuthor() + "\"\n");
                    writer.write("  }");
                    if (i < wiseSayingList.size() - 1) {
                        writer.write(",\n");
                    } else {
                        writer.write("\n");
                    }
                }
                writer.write("]");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
