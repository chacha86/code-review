package wiseSaying.repository;

import wiseSaying.WiseSaying;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class WiseSayingFileRepository implements WiseSayingRepository {
    private final String LAST_ID_FILE = "/lastId.txt";
    private final String DATA_FILE = "/data.json";
    private final Path dbPath;

    public WiseSayingFileRepository() {
        dbPath = Paths.get("db/wiseSaying");
        initDatabase();
    }

    public WiseSayingFileRepository(Path dbPath) {
        this.dbPath = dbPath;
        initDatabase();
    }

    private void initDatabase() {
        try {
            Files.createDirectories(dbPath);
            Path lastIdFile = Paths.get(dbPath.toString() + LAST_ID_FILE);
            if (!Files.exists(lastIdFile)) {
                Files.write(lastIdFile, "0".getBytes());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize database: " + e.getMessage());
        }
    }

    private int getLastId() {
        try {
            Path lastIdFile = Paths.get(dbPath.toString() + LAST_ID_FILE);
            String content = Files.readString(lastIdFile).trim();

            return Integer.parseInt(content);
        } catch (IOException e) {
            return 0;
        }
    }

    private void saveLastId(int id) {
        try {
            Path lastIdFile = Paths.get(dbPath.toString() + LAST_ID_FILE);
            Files.writeString(lastIdFile, Integer.toString(id));
        } catch (IOException e) {
            throw new RuntimeException("Failed to save last ID: " + e.getMessage());
        }
    }

    public WiseSaying save(WiseSaying wiseSaying) {
        int nextId = getLastId() + 1;
        wiseSaying.setId(nextId);
        String json = wiseSaying.toJson();

        try {
            Path filePath = Paths.get(dbPath.toString() + "/" + nextId + ".json");
            Files.writeString(filePath, json, StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save wise saying: " + e.getMessage());
        }

        saveLastId(nextId);
        return wiseSaying;
    }

    public Optional<WiseSaying> findOneById(int id) {
        Path filePath = Paths.get(dbPath.toString() + "/" + id + ".json");
        try {
            String json = Files.readString(filePath);
            return Optional.of(WiseSaying.fromJson(json));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public List<WiseSaying> findAll() {
        List<WiseSaying> wiseSayings = new ArrayList<>();
        for (File file : findFilesDescendingId()) {
            try {
                String json = Files.readString(file.toPath());
                wiseSayings.add(WiseSaying.fromJson(json));
            } catch (IOException e) {
                throw new RuntimeException("Failed to read file: " + e.getMessage());
            }

        }
        return wiseSayings;
    }

    public List<WiseSaying> findWhere(String key, String keyword) {
        List<WiseSaying> wiseSayings = new ArrayList<>();
        for (File file : findFilesDescendingId()) {
            try {
                String json = Files.readString(file.toPath());
                WiseSaying wiseSaying = WiseSaying.fromJson(json);
                boolean containKeyword = (key.equals("content") && wiseSaying.getContent().contains(keyword)) ||
                        (key.equals("author") && wiseSaying.getAuthor().contains(keyword));
                if (containKeyword) {
                   wiseSayings.add(wiseSaying);
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to read file: " + e.getMessage());
            }

        }
        return wiseSayings;
    }

    public boolean deleteOneById(int id) {
        Path filePath = Paths.get(dbPath.toString() + "/" + id + ".json");
        try {
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            return false;
        }
    }

    public boolean updateOne(WiseSaying wiseSaying) {
        Path filePath = Paths.get(dbPath.toString() + "/" + wiseSaying.getId() + ".json");
        String json = wiseSaying.toJson();
        try {
            Files.writeString(filePath, json);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public void saveData(String data) {
        try {
            Path dataFile = Paths.get(dbPath.toString() + DATA_FILE);
            Files.writeString(dataFile, data);
        } catch (IOException e) {
            throw new RuntimeException("Failed to build data.json: " + e.getMessage());
        }
    }

    private List<File> findFilesDescendingId() {
        return Arrays.stream(Objects.requireNonNull(dbPath.toFile().listFiles()))
                .filter((file) -> file.getName().endsWith(".json") && !file.getName().equals("data.json"))
                .sorted((file1, file2) -> {
                    int id1 = Integer.parseInt(file1.getName().replace(".json", ""));
                    int id2 = Integer.parseInt(file2.getName().replace(".json", ""));
                    return Integer.compare(id2, id1);
                })
                .toList();
    }
}
