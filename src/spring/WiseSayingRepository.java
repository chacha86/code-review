package spring;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class WiseSayingRepository {
    AppContext appContext = AppContext.getInstance();
    

    public void dataLoad()
    {
        //파일 유무 확인
        //데이터 배열에 저장
        //순서 인트에 저장
        ID_FileMaker();
        ID_FileReader();
        JSON_FileReader();

    }

    void ReLoad()
    {
        JSON_FileWriter();
        ID_FileWriter(appContext.count);//덮어쓰고 재갱신
        appContext.data.clear(); //사실 안해도될거같은데 값 변질 우려때문에 한번 날려줌
        dataLoad();

    }

    void ID_FileMaker()
    {
        File file = new File("db/wiseSaying/lastId.txt");
        try 
        { 
            if (!file.exists()) 
            {
                file.createNewFile(); // 파일 생성
                ID_FileWriter(0);  
            } 
        } 
        catch (IOException e) {System.out.println("생성도중 오류 발생 : " + e.getMessage());}

    }
    void ID_FileReader()
    {
        File file = new File("db/wiseSaying/lastId.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                appContext.count = Integer.parseInt(line); 
            }
        }
        
        catch (IOException e) {System.out.println("생성도중 오류 발생 : " + e.getMessage());}

    }

    void ID_FileWriter(int temp) // 인수를 정하는 방법도있지만 일단 초기화 할때도 써야해서 인티저 받음
    {
        File file = new File("db/wiseSaying/lastId.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, java.nio.charset.StandardCharsets.UTF_8))) 
        {             
            writer.write(String.valueOf(temp));
            writer.flush();  
        } 
        catch (IOException e) {System.out.println("파일접근중 오류 발생: " + e.getMessage());}    
    }

    void JSON_FileReader()
    {
        File file = new File("db/wiseSaying/data.json");
    
        try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) 
        {
            StringBuilder jsonContent = new StringBuilder();
            String line;
    
            while ((line = reader.readLine()) != null) { jsonContent.append(line).append("\n"); }
    
            String json = jsonContent.toString().trim();
            
            if (json.startsWith("[") && json.endsWith("]")) { json = json.substring(1, json.length() - 1).trim(); } // 대괄호 제거
            if (json.isEmpty()) { return; } // 빈 배열일 경우 탈출
            
            String[] objects = json.split("},\\s*\\{");// 추출 정규식
    
            for (int i = 0; i < objects.length; i++) 
            {
                String item = objects[i].trim();

                // 괄호 해체
                if (i == 0 && item.startsWith("{")) { item = item.substring(1).trim(); }
                if (i == objects.length - 1 && item.endsWith("}")) { item = item.substring(0, item.length() - 1).trim(); }
    
                // id, content, author 추출
                int id = Integer.parseInt(json_ExtractValue2(item, "\"id\":", ","));
                String content = json_ExtractValue2(item, "\"content\":", ",");
                String author = json_ExtractValue2(item, "\"author\":", null);
                
                //데이터 입력
                appContext.data.add(new WiseSaying(id, content, author));
            }
            
        } 
        catch (IOException e) { return; }

    }
    String json_ExtractValue2(String json, String key, String delimiter)//널체크 개선 
    {
        int startIdx = json.indexOf(key) + key.length();
        int endIdx = delimiter != null ? json.indexOf(delimiter, startIdx) : json.length();
        return json.substring(startIdx, endIdx).trim().replaceAll("[\"{}]", "");
    }
    void JSON_FileWriter() {

        StringBuilder jsonBuilder = new StringBuilder("[\n");

        for (int i = 0; i < appContext.data.size(); i++) {
            WiseSaying data = appContext.data.get(i);
            String jsonObject = String.format("  {\n    \"id\": %d,\n    \"content\": \"%s\",\n    \"author\": \"%s\"\n  }",data.number, data.content, data.author);
            jsonBuilder.append(jsonObject);

            // 마지막 개체가 아닐시 추가용으로 콤마 추가
            if (i < appContext.data.size() - 1) 
            { jsonBuilder.append(",\n"); } 
            else 
            { jsonBuilder.append("\n"); }
        }
        //배열생성 종료
        jsonBuilder.append("]");

        // 파일에 저장
        File file = new File("db/wiseSaying/data.json");
        try 
        {
            if (!file.exists()) 
            { 
                file.createNewFile(); 
            }
        } 
        catch (IOException e) { System.out.println("데이터 파일 생성 실패" + e.getMessage()); }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) 
        {
            writer.write(jsonBuilder.toString());
            writer.flush();

        } 
        catch (IOException e) { System.out.println("파일 저장 중 오류 발생: " + e.getMessage()); }
    }
    
    
}
