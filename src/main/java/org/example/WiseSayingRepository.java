package org.example.wiseSaying;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *  레포지토리
 */

public class WiseSayingRepository {
    private final List<WiseSaying> wiseSayings = new ArrayList<>();
    private final String DB_PATH = "db/wiseSaying";
    private final String DATA_FILE = DB_PATH + "/data.json";
    private final String LAST_ID_FILE = DB_PATH + "/lastId.txt";
    private int lastId = 0;

    /// 파일 영속성 초기화
    public void initDatabase() throws IOException, ParseException {
        Files.createDirectories(Paths.get(DB_PATH));

        File lastIdFile = new File(LAST_ID_FILE);
        if (lastIdFile.exists()) {
            lastId = Integer.parseInt(Files.readString(lastIdFile.toPath()).trim());
        }

        File jsonFile = new File(DATA_FILE);
        if (jsonFile.exists()) {
            String content = Files.readString(jsonFile.toPath());
            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = (JSONArray) jsonParser.parse(content);

            for (Object obj : jsonArray) {
                JSONObject json = (JSONObject) obj;
                WiseSaying wiseSaying = parseWiseSaying(json);
                if (wiseSaying != null) {
                    wiseSayings.add(wiseSaying);
                }
            }
        }
    }

    /// JSON -> WiseSaying
    public WiseSaying parseWiseSaying(JSONObject json) {
        int id = Integer.parseInt(json.get("id").toString());
        String author = json.get("author").toString();
        String content = json.get("content").toString();

        return new WiseSaying(id, author, content);
    }

    /// WiseSaying -> File
    public void saveWiseSayingToFile(WiseSaying wiseSaying) throws IOException {
        JSONObject json = new JSONObject();
        json.put("id", wiseSaying.getId());
        json.put("author", wiseSaying.getAuthor());
        json.put("content", wiseSaying.getContent());

        File file = new File(DB_PATH, wiseSaying.getId() + ".json");
        Files.createDirectories(file.getParentFile().toPath());
        Files.writeString(file.toPath(), json.toString());
    }

    /// LastId -> File
    public void saveLastId(WiseSaying wiseSaying) throws IOException {
        File lastIdFile = new File(LAST_ID_FILE);
        Files.writeString(lastIdFile.toPath(), String.valueOf(wiseSaying.getId()));
    }

    /// 명언 저장
    public void saveWiseSaying(WiseSaying wiseSaying) throws IOException {
        wiseSayings.add(wiseSaying);

        saveWiseSayingToFile(wiseSaying);
        updateDataJsonFile();
    }

    /// 모든 명언 조회
    public List<WiseSaying> findAllWiseSayings() {
        return new ArrayList<>(wiseSayings);
    }

    /// id로 명언 조회
    public WiseSaying findWiseSayingById(int id) {
        return wiseSayings.stream()
                .filter(wiseSaying -> wiseSaying.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /// 명언 삭제
    public boolean deleteWiseSayingById(int id) throws IOException {
        WiseSaying wiseSaying = findWiseSayingById(id);

        if (wiseSaying != null) {
            wiseSayings.remove(wiseSaying);

            File file = new File(DB_PATH, wiseSaying.getId() + ".json");
            if (file.exists()) {
                file.delete();
            }

            updateDataJsonFile();
            return true;
        }

        return false;
    }

    /// data.json 갱신
    private void updateDataJsonFile() throws IOException {
        JSONArray jsonArray = new JSONArray();

        for (WiseSaying ws : wiseSayings) {
            JSONObject json = new JSONObject();
            json.put("id", ws.getId());
            json.put("author", ws.getAuthor());
            json.put("content", ws.getContent());
            jsonArray.add(json);
        }

        Files.writeString(Paths.get(DATA_FILE), jsonArray.toJSONString());
    }
}