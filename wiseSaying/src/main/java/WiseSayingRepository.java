import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class WiseSayingRepository {
    private final String LAST_ID_FILE = "/lastId.txt";
    private final String DATA_FILE = "/data.json";
    private final Path dbPath;

    public WiseSayingRepository() {
        dbPath = Paths.get("db/wiseSaying");
        initDatabase();
    }

    public WiseSayingRepository(Path dbPath) {
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

    public int save(WiseSaying wiseSaying) {
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
        return nextId;
    }

    public WiseSaying findOneById(int id) {
        Path filePath = Paths.get(dbPath.toString() + "/" + id + ".json");
        if (Files.exists(filePath)) {
            try {
                String json = Files.readString(filePath);
                return WiseSaying.fromJson(json);
            } catch (IOException e) {
                throw new RuntimeException("Failed to read file: " + e.getMessage());
            }
        }
        return null;
    }

    public List<WiseSaying> findMany() {
        List<File> files = Arrays.stream(Objects.requireNonNull(dbPath.toFile().listFiles()))
                .filter((file) -> file.getName().endsWith(".json") && !file.getName().equals("data.json"))
                .toList();

        List<WiseSaying> wiseSayings = new ArrayList<>();
        for (File file : files) {
            try {
                String json = Files.readString(file.toPath());
                wiseSayings.add(WiseSaying.fromJson(json));
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
}
