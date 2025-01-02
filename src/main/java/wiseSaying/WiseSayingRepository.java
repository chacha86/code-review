package wiseSaying;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.simple.JSONArray;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class WiseSayingRepository {

    Map<Integer, String> wiseMap = new HashMap<>();
    JsonSetting jsonSetting = new JsonSetting();
    JSONArray jsonArray = new JSONArray();
    Scanner scanner = new Scanner(System.in);

    public WiseSaying insertWise(WiseSaying ws){

        int cnt = ++ws.lastId;
        ws.setWiseAuthorSet(cnt+"/"+ws.getWise()+"/"+ws.getAuthor());
        wiseMap.put(cnt,ws.getWiseAuthorSet());

        ObjectNode obj = jsonSetting.createjsonObj(cnt,ws.getWise(),ws.getAuthor());

        try{
            // lastid 생성
            String fileName = "D:/dev/dev_back4/code-review/db/wiseSaying/lastId.txt";
            BufferedWriter fw = new BufferedWriter((new FileWriter(fileName)));

            fw.write(Integer.toString(cnt));
            fw.flush();
            fw.close();

            // json 생성 구문
            String beautyJson = jsonSetting.beautyJson(obj);

            jsonArray.add(obj);

            FileWriter file = new FileWriter("D:/dev/dev_back4/code-review/db/wiseSaying/"+cnt+".json");
            file.write(beautyJson);
            file.flush();
            file.close();

        }catch(IOException e){
            e.printStackTrace();
        }

        return ws;
    }

    public void listWise(WiseSaying ws){
        List<Integer> keySet = new ArrayList<>(wiseMap.keySet());
        Collections.reverse(keySet);

        for(Integer key : keySet){
            System.out.println(wiseMap.get(key));
        }
    }

    public void deleteWise(WiseSaying ws){

        if(!wiseMap.containsKey(Integer.parseInt(ws.getRemoveKey()))){
            System.out.println(ws.getRemoveKey()+"번 명언은 존재하지 않습니다.");
        }else{

            for (int i = 0; i < jsonArray.size(); i++) {
                ObjectNode currentObj = (ObjectNode) jsonArray.get(i);

                // 특정 조건에 맞는 데이터 삭제
                if (ws.getRemoveKey().equals(currentObj.get("id").asText())) {
                    jsonArray.remove(i);  // i번째 인덱스 삭제
                    break;
                }
            }
            wiseMap.remove(Integer.parseInt(ws.getRemoveKey()));
        };
    }

    public void updateWise(WiseSaying ws){

        if(!wiseMap.containsKey(Integer.parseInt(ws.getUpdateKey()))){
            System.out.println(ws.getUpdateKey()+"번 명언은 존재하지 않습니다.");
        }else{
            ws.setParseMapValue(wiseMap.get(Integer.parseInt(ws.getUpdateKey())));
            String[] parseMapValueArr = ws.getParseMapValue().split("/");

            System.out.println("명언(기존): "+parseMapValueArr[1]);
            System.out.print("명언: ");
            ws.setWise(scanner.nextLine());
            System.out.println("작가(기존): "+parseMapValueArr[2]);
            System.out.print("작가: ");
            ws.setAuthor(scanner.nextLine());

            ws.setWiseAuthorSet(ws.getUpdateKey()+"/"+ws.getWise()+"/"+ws.getAuthor());
            wiseMap.put(Integer.parseInt(ws.getUpdateKey()),ws.getWiseAuthorSet());

            ObjectNode obj = jsonSetting.createjsonObj(
                    Integer.parseInt(ws.getUpdateKey()),ws.getWise(),ws.getAuthor());

            try{
                String beautyJson = jsonSetting.beautyJson(obj);

                for (int i = 0; i < jsonArray.size(); i++) {
                    ObjectNode currentObj = (ObjectNode) jsonArray.get(i);

                    // 특정 조건에 맞는 데이터 수정
                    if (ws.getUpdateKey().equals(currentObj.get("id").asText())) {
                        // 수정할 데이터 생성
                        ObjectNode updatedObj = currentObj.deepCopy();
                        updatedObj.put("content",ws.getWise()+" "+ws.getUpdateKey());
                        updatedObj.put("author",ws.getAuthor()+" "+ws.getUpdateKey());

                        // 같은 인덱스에 수정된 데이터 설정
                        jsonArray.set(i, updatedObj);
                    }
                }

                FileWriter file = new FileWriter("D:/dev/dev_back4/code-review/db/wiseSaying/"+ws.getUpdateKey()+".json");
                file.write(beautyJson);
                file.flush();
                file.close();

            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public JSONArray buildWise(WiseSaying ws){

        System.out.println(jsonArray);
        try{

            String beautyJson = jsonSetting.beautyJson(jsonArray);
            System.out.println(beautyJson);

            FileWriter file = new FileWriter("D:/dev/dev_back4/code-review/db/wiseSaying/data.json");
            file.write(beautyJson);
            file.flush();
            file.close();

        }catch (IOException e){
            e.printStackTrace();
        }
        return jsonArray;
    }


}


