import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WiseSayingRepository {

    private int lastId;
    private final Map<Integer, WiseSaying> wiseSayings;
    private static final String PATH = "db/wiseSaying";
    private final ObjectMapper om;

    public WiseSayingRepository() {
        this.wiseSayings = new HashMap<>();
        this.lastId = 0;
        this.om = new ObjectMapper();
        init();
    }

    private void init() {

        File db = new File(PATH);
        if (!db.exists()) db.mkdirs();

        loadLastId();
        loadWiseSayings();
    }

    private void loadLastId() {

        File lastIdFile = new File(PATH + "/lastId.txt");
        if (lastIdFile.exists()) {

            try (BufferedReader reader = new BufferedReader(new FileReader(lastIdFile))) {

                String line = reader.readLine();
                if (line != null) {
                    lastId = Integer.parseInt(line);
                }
            } catch (Exception e) {
                throw new RuntimeException("lastId를 불러오는데 실패하였습니다.", e);
            }
        }
    }

    private void loadWiseSayings() {

        File dataFile = new File(PATH + "/data.json");

        if (dataFile.exists() && dataFile.length() > 0) {
            try {
                List<WiseSaying> wiseSayingList = om.readValue(dataFile,
                        new TypeReference<List<WiseSaying>>() {});

                for (WiseSaying wiseSaying : wiseSayingList) {
                    wiseSayings.put(wiseSaying.getId(), wiseSaying);
                    lastId = Math.max(lastId, wiseSaying.getId());
                }
            } catch (Exception e) {
                throw new RuntimeException("wiseSaying를 불러오는데 실패하였습니다.", e);
            }
        }
    }

    public WiseSaying create(String content, String author) {

        lastId++;
        WiseSaying wiseSaying = new WiseSaying(lastId, content, author);
        wiseSayings.put(lastId, wiseSaying);
        saveWiseSaying(wiseSaying);
        saveLastId();

        return wiseSaying;
    }

    public List<WiseSaying> search(String keywordType, String keyword) {

        List<WiseSaying> searchResults = new ArrayList<>();

        for (WiseSaying wiseSaying : wiseSayings.values()) {
            boolean matches = switch (keywordType) {
                case "content" -> wiseSaying.getContent().contains(keyword);
                case "author" -> wiseSaying.getAuthor().contains(keyword);
                default -> false;
            };

            if (matches) {
                searchResults.add(wiseSaying);
            }
        }

        return searchResults;
    }

    public List<WiseSaying> findAll() {
        return new ArrayList<>(wiseSayings.values());
    }

    public WiseSaying findById(int id) {
        return wiseSayings.get(id);
    }

    public void remove(int id) {

        wiseSayings.remove(id);
        new File(PATH + "/" + id + ".json").delete();
    }

    public void modify(int id, String content, String author) {

        WiseSaying wiseSaying = findById(id);
        if (wiseSaying != null) {
            wiseSaying.setContent(content);
            wiseSaying.setAuthor(author);
            saveWiseSaying(wiseSaying);
        }
    }

    public void build() {

        try {
            om.writeValue(new File(PATH + "/data.json"), new ArrayList<>(wiseSayings.values()));
        } catch (Exception e) {
            throw new RuntimeException("data.json 파일을 빌드하는데 실패하였습니다.", e);
        }
    }

    private void saveWiseSaying(WiseSaying wiseSaying) {

        try {
            om.writeValue(new File(PATH + "/" + wiseSaying.getId() + ".json"), wiseSaying);
        } catch (Exception e) {
            throw new RuntimeException("wiseSaying를 저장하는데 실패하였습니다.", e);
        }
    }

    private void saveLastId() {

        try (FileWriter file = new FileWriter(PATH + "/lastId.txt")) {
            file.write(String.valueOf(lastId));
        } catch (Exception e) {
            throw new RuntimeException("lastId를 저장하는데 실패하였습니다.", e);
        }
    }
}