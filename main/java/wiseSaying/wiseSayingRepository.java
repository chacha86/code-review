package wiseSaying;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.util.*;

public class wiseSayingRepository {
    private final String dirPath = "src\\main\\resources\\db\\wiseSaying";
    private final Map<Integer, WiseSaying> wiseSayings = new HashMap();
    private int lastId = 0;

    public wiseSayingRepository(){
        loadContents();
    }

    private void loadContents() {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();

        if (files == null) return;

        JSONParser parser = new JSONParser();

        for (File file : files) {
            String fileName = file.getName().split("\\.")[0];
            if (isNumeric(fileName)) {
                try (Reader reader = new FileReader(file)) {
                    JSONObject jsonObject = (JSONObject) parser.parse(reader);
                    int id = Integer.parseInt(jsonObject.get("id").toString());
                    String content = (String) jsonObject.get("content");
                    String author = (String) jsonObject.get("author");

                    WiseSaying w = new WiseSaying();
                    w.id = id;
                    w.content = content;
                    w.author = author;

                    wiseSayings.put(id, w);

                    lastId = Math.max(lastId, id);
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void saveContent(int id, String content, String author){
        WiseSaying w = new WiseSaying();
        w.id = id;
        w.content = content;
        w.author = author;

        wiseSayings.put(id, w);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("content", content);
        jsonObject.put("author", author);

        try (FileWriter file = new FileWriter(dirPath + "\\" + id + ".json")) {
            file.write(jsonObject.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveLastId() throws IOException {
        File f = new File(dirPath+"\\lastId.txt");
        FileWriter fw = new FileWriter(f, false) ;
        fw.write(lastId + "");
        fw.flush();
        fw.close();
    }

    public void deleteContent(int id) {
        wiseSayings.remove(id);

        File file = new File(dirPath + "\\" + id + ".json");
        if (file.exists()) {
            file.delete();
        }
    }

    public void buildJson() {
        JSONArray jsonArray = new JSONArray();
        for (WiseSaying wiseSaying : wiseSayings.values()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", wiseSaying.getId());
            jsonObject.put("content", wiseSaying.getContent());
            jsonObject.put("author", wiseSaying.getAuthor());
            jsonArray.add(jsonObject);
        }

        try (FileWriter file = new FileWriter(dirPath + "\\data.json")) {
            file.write(jsonArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean contentExists(int id) {
        return wiseSayings.containsKey(id);
    }

    public int plusLastId(){
        return ++lastId;
    }

    public int getLastId(){
        return lastId;
    }

    public Map<Integer, WiseSaying> getWiseSayings(){
        return wiseSayings;
    }

    private boolean isNumeric(String str) {
        return str.matches("[+-]?\\d*(\\.\\d+)?");
    }
}
