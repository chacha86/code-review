package wiseSaying;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.simple.JSONArray;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        App app = new App();
        app.run();
    }
}

class App {

    WiseSayingController wiseSayingController = new WiseSayingController();

    public void run() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("== 명언 앱 ==");

        while (true) {
            String cmd;

            System.out.print("명령)");
            cmd = scanner.nextLine(); // 입력값 가져옴 / 입력값이 없으면 기다린다.

            if(cmd.equals("종료")){
                System.out.println("명언 앱을 종료합니다.");
                break;
            }else if (cmd.equals("등록")) {
                wiseSayingController.insertWise();
            }else if(cmd.equals("목록")){
                wiseSayingController.listWise();
            }else if(cmd.equals("삭제")){
                wiseSayingController.deleteWise();
            }else if(cmd.equals("수정")){
                wiseSayingController.updateWise();

            }else if(cmd.equals("빌드")){
                wiseSayingController.buildWise();
            }
        }
    }


}

class JsonSetting{
    // 입력 받은 값 json파싱
    public ObjectNode createjsonObj(int cnt, String wise, String author){

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode obj = mapper.createObjectNode();

        obj.put("id",cnt);
        obj.put("content",wise+" "+cnt);
        obj.put("author",author+" "+cnt);

        return obj;

    }


    // json 데이터 줄 맞춤 메소드
    public String beautyJson(ObjectNode obj) throws IOException{

        ObjectMapper objectMapper = new ObjectMapper();

        Object json = objectMapper.readValue(obj.toString(), Object.class);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);

    }

    public String beautyJson(JSONArray obj) throws IOException{

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement jsonElement = JsonParser.parseString(obj.toString());

        return gson.toJson(jsonElement);

    }
}
