package wiseSaying;


import org.json.JSONArray;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import org.json.JSONObject;

public class WiseSayingSaveFile {
    private static Path dbDirectory = Paths.get("db/wiseSaying");
    private static Path lastIdFile = dbDirectory.resolve("lastId.txt");

    public static void saveWiseByFile(WiseSaying wise) {
        Path filePath = dbDirectory.resolve(wise.getId() + ".json");
        JSONObject json = new JSONObject();
        json.put("id", wise.getId());
        json.put("content", wise.getContent());
        json.put("author", wise.getAuthor());
        try {
            Files.write(filePath, json.toString().getBytes());
            System.out.println("파일 저장 성공");
        } catch (IOException e) {
            System.out.println("파일 저장에 실패했습니다.");
        }
    }

    public static void updateLastId(Long id) {
        try {
            Files.write(lastIdFile, String.valueOf(id).getBytes());
        } catch (IOException e) {
            System.out.println("파일 저장에 실패했습니다.");
        }
    }

    public static void buildFile(Set<WiseSaying> wiseSet) {
        Path filePath = dbDirectory.resolve("data.json");
        JSONArray jsonArray = new JSONArray();
        for (WiseSaying wise : wiseSet) {
            JSONObject obj = new JSONObject();
            obj.put("id", wise.getId());
            obj.put("content", wise.getContent());
            obj.put("author", wise.getAuthor());
            jsonArray.put(obj);
        }
        try {
            Files.write(filePath, jsonArray.toString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException("파일 저장에 실패했습니다.");
        }
    }
}
