package test;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class test {
    public static void main(String[] args) {
        
        System.out.println("== 명언 앱 ==");
        Scanner sc = new Scanner(System.in);

        List<Data> data = new ArrayList();
        
        fileMaker_ID();
        fileReader_Json_alpha(data);
        int count = fileReader_lastID();
        
        while (true) 
        {  
            System.out.print("명령) ");
            String command = sc.nextLine();
            //종료 로직
           
            if ("종료".equals(command))
            {
                System.out.println("프로그램 종료");
                break;  
            }
            else if("등록".equals(command))
            {
                명령_등록(data, count , sc);
                /**
                count++;
                System.out.print("명언 : ");
                String insert_data_1 = sc.nextLine();
                System.out.print("작가 : ");
                String insert_data_2 = sc.nextLine();
                System.out.println(count+"번 명언이 등록되었습니다.");
                
                data.add(0,new Data(count, insert_data_1, insert_data_2)); 
                fileMaker_Json_single(count);
                fileWriter_Json_single(count, insert_data_1, insert_data_2);
                fileWriter_lastID(count);
                */
            }
            else if("목록".equals(command))
            {   
                명령_목록(data, sc);
                /**
                System.out.println("번호 / 작가 / 명언\n----------------------");
                for (Data d : data) { d.printData(); }

                File directory = new File("db/wiseSaying");
                File[] files = directory.listFiles();

                for (File file : files) 
                {
                    if (file.isFile() && file.getName().endsWith(".json")) 
                    {
                        fileReader_Json_single(file.getPath());
                    }
                }
                */
                
            }
            else if("빌드".equals(command))
            {
                명령_커밋(data, count);
                /** 
                fileWriter_Json_alpha(data);
                fileWriter_lastID(count);//덮어쓰고 재갱신
                data.clear();

                fileReader_Json_alpha(data);
                count = fileReader_lastID();
                */
            }
            else if (command.startsWith("삭제?id=")) 
            {
                명령_삭제(data, command); 
                /**
                int     number = Integer.parseInt(command.substring("삭제?id=".length())); 
                boolean found  = false; //확인용
          
                for (int i = 0; i < data.size(); i++) 
                {
                    if (data.get(i).number == number) {
                        System.out.println(number + "번 명언이 삭제되었습니다.");
                        data.remove(i);  // 해당 명언 삭제
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    System.out.println(number + "번 명언은 존재하지 않습니다.");
                }

                int     number = Integer.parseInt(command.substring("삭제?id=".length())); 
                fileDelete("db/wiseSaying/"+number+".json");
                */
            }

            else if(command.startsWith("수정?id="))
            {
                명령_수정(data, command, sc);
                /**
                int number = Integer.parseInt(command.substring("수정?id=".length())); 
                boolean found  = false; //확인용

                for (int i = 0; i < data.size(); i++) 
                {
                    if (data.get(i).number == number) {
                    
                        System.out.println("명언(기존) : " + data.get(i).content);
                        System.out.print("명언 : ");
                        String insert_data_1 = sc.nextLine();
                        
                        System.out.println("작가(기존) : " + data.get(i).author);
                        System.out.print("작가 : ");
                        String insert_data_2 = sc.nextLine();

                        data.set(i, new Data(number, insert_data_1, insert_data_2));
                        found = true;
                        
                        break;
                    }
                }

                if (!found) { System.out.println(number + "번 명언은 존재하지 않습니다."); }

                fileModify_Json_single("db/wiseSaying/"+number+".json",sc);
                */
            }
            else if ("초기화".equals(command)) 
            {   
                명령_초기화(data, command, count);
                /** 
                File directory = new File("db/wiseSaying");
                if (directory.isDirectory()) 
                {
                    File[] files = directory.listFiles();
                    if (files != null) 
                    { 
                        for (File file : files) { file.delete(); }
                    }
                }
                fileMaker_ID();
                count = fileReader_lastID();
                System.out.println("초기화 완료.");
                */
            }
            else { System.out.println("잘못된 명령입니다."); }
        }
        sc.close();
    }

    static void fileMaker_ID()//생성기능
    {
        File file = new File("db/wiseSaying/lastId.txt");
        try 
        { 
            if (!file.exists()) 
            {
                file.createNewFile(); // 파일 생성
                fileWriter_lastID(0);  
            } 
        } 
        catch (IOException e) {System.out.println("생성도중 오류 발생 : " + e.getMessage());}
    }
    static void fileMaker_Json_single(int number)//생성기능
    {
        File file = new File("db/wiseSaying/"+number+".json");
        try { file.createNewFile(); } 
        catch (IOException e) {System.out.println("생성도중 오류 발생 : " + e.getMessage());}
    }

    static void fileWriter_lastID(int temp) 
    {
        File file = new File("db/wiseSaying/lastId.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, java.nio.charset.StandardCharsets.UTF_8))) 
        {             
            writer.write(String.valueOf(temp));
            writer.flush();  
        } 
        catch (IOException e) {System.out.println("파일접근중 오류 발생: " + e.getMessage());}
        
    }
    static void fileWriter_Json_single(int number ,String content ,String author)//작성자
    {
        String json = "{\n" +
                  "  \"id\": " + number + ",\n" +
                  "  \"content\": \"" + content + "\",\n" +
                  "  \"author\": \"" + author + "\"\n" +
                  "}";
        File file = new File("db/wiseSaying/"+number+".json");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, java.nio.charset.StandardCharsets.UTF_8))) 
        { 
            writer.write(json);
            writer.flush(); 
        }  
        catch (IOException e) {System.out.println("작성도중 오류 발생 : " + e.getMessage());}
    }

    static int fileReader_lastID() 
    {
        File file = new File("db/wiseSaying/lastId.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                return Integer.parseInt(line.trim()); 
            }
        }
        
        catch (IOException e) {System.out.println("생성도중 오류 발생 : " + e.getMessage());}
        return -1;
        
    }
    static void fileReader_Json_single(String path) 
    {
        File file = new File(path);

        try (BufferedReader reader = new BufferedReader(new FileReader(file, java.nio.charset.StandardCharsets.UTF_8))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) { jsonContent.append(line).append("\n"); }

            String json = jsonContent.toString();

            // 간단한 파싱을 통해 값 추출
            int id = Integer.parseInt(json_ExtractValue(json, "\"id\":", ","));
            String content = json_ExtractValue(json, "\"content\":", ",");
            String author  = json_ExtractValue(json, "\"author\":", "}");

            // 출력
            System.out.println(id + ". \"" + content + "\" / " + author);

        } 
        catch (IOException e) {System.out.println("파일 읽기 중 오류 발생: " + e.getMessage()); }
        
    }

    static void fileModify_Json_single(String path ,Scanner sc) 
    {
        File file = new File(path);  

        try (BufferedReader reader = new BufferedReader(new FileReader(file, java.nio.charset.StandardCharsets.UTF_8))) 
        {
            StringBuilder jsonContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) { jsonContent.append(line).append("\n"); }         
            String json = jsonContent.toString(); //JSON 파싱 id, content, author 

            int id = Integer.parseInt(json_ExtractValue(json, "\"id\":", ","));
            String content = json_ExtractValue(json, "\"content\":", ",");
            String author  = json_ExtractValue(json, "\"author\":", "}");

            System.out.println("명언(기존) : " + content);
            System.out.print("명언 : ");
            String insert_data_1 = sc.nextLine();
                        
            System.out.println("작가(기존) : " + author);
            System.out.print("작가 : ");
            String insert_data_2 = sc.nextLine();

            fileWriter_Json_single(id,insert_data_1,insert_data_2);

        } 
        catch (IOException e) {System.out.println("파일 읽기 중 오류 발생: " + e.getMessage()); }
        
    }

    static void fileReader_Json_alpha(List<Data> data) {
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
                data.add(new Data(id, content, author));
            }
            
        } 
        catch (IOException e) { return; }
    }

    static String json_ExtractValue(String json, String key, String delimiter) 
    {
        int startIdx = json.indexOf(key) + key.length();
        int endIdx = json.indexOf(delimiter, startIdx);
        return json.substring(startIdx, endIdx).trim().replaceAll("\"", "");
    }
    static String json_ExtractValue2(String json, String key, String delimiter)//널체크 개선 
    {
        int startIdx = json.indexOf(key) + key.length();
        int endIdx = delimiter != null ? json.indexOf(delimiter, startIdx) : json.length();
        return json.substring(startIdx, endIdx).trim().replaceAll("[\"{}]", "");
    }
    
    static void fileDelete(String targetLink) //삭제기능
    {
        File file = new File(targetLink);
        try 
        {
            if (file.exists()) { file.delete();} 
            else { System.out.println("파일이 존재하지 않습니다: " + targetLink);}
        }
        catch (Exception e) {System.out.println("삭제중 오류 발생 : " + e.getMessage());}
    }

    static void fileWriter_Json_alpha(List<Data> Data) {

        StringBuilder jsonBuilder = new StringBuilder("[\n");

        for (int i = 0; i < Data.size(); i++) {
            Data data = Data.get(i);
            String jsonObject = String.format("  {\n    \"id\": %d,\n    \"content\": \"%s\",\n    \"author\": \"%s\"\n  }",data.number, data.content, data.author);
            jsonBuilder.append(jsonObject);

            // 마지막 개체가 아닐시 추가용으로 콤마 추가
            if (i < Data.size() - 1) 
            {
                jsonBuilder.append(",\n");
            } else 
            {
                jsonBuilder.append("\n");
            }
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
        catch (IOException e) { System.out.println("데이터 파일 미확인 생성합니다" + e.getMessage()); }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) 
        {
            writer.write(jsonBuilder.toString());
            writer.flush();

        } 
        catch (IOException e) { System.out.println("파일 저장 중 오류 발생: " + e.getMessage()); }
    }
    /////////////////////////////////업데이트 함수 저장공간//////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    void dataUpadater()
    {
        lastIdCreator();
        lastIdWriter(0);

    }

    void lastIdCreator()
    {
        File file = new File("db/wiseSaying/lastId.txt");
        try 
        { 
            if (!file.exists()) 
            {
                file.createNewFile();
                lastIdWriter(0);  
            } 
        } 
        catch (IOException e) {System.out.println("ID 파일 생성실패[lastIdCreator()] : " + e.getMessage());}
    }

    void lastIdWriter(int number)
    {
        File file = new File("db/wiseSaying/lastId.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, java.nio.charset.StandardCharsets.UTF_8))) 
        {             
            writer.write(String.valueOf(number));
            writer.flush();  
        } 
        catch (IOException e) {System.out.println("ID 파일 수정실패[lastIdCreator()] : " + e.getMessage());}

    }

    int lastIdReader()
    {
        File file = new File("db/wiseSaying/lastId.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                return Integer.parseInt(line.trim()); 
            }
        }
        
        catch (IOException e) {System.out.println("생성도중 오류 발생 : " + e.getMessage());}
        return -99;
    }

    void dataJsonMaker()
    {
        File file = new File("db/wiseSaying/data.json");
        try 
        {
            if (!file.exists()) 
            { 
                file.createNewFile(); 
            }
        } 
        catch (IOException e) { System.out.println("data.Json생성 실패 : " + e.getMessage()); }
    }

    void dataJsonWriter(List<Data> Data)
    {
        StringBuilder jsonBuilder = new StringBuilder("[\n");

        for (int i = 0; i < Data.size(); i++) 
        {
            Data data = Data.get(i);
            String jsonObject = String.format("  {\n    \"id\": %d,\n    \"content\": \"%s\",\n    \"author\": \"%s\"\n  }",data.number, data.content, data.author);
            jsonBuilder.append(jsonObject);

            jsonBuilder.append(i < Data.size() - 1 ? ",\n" : "\n"); // 마지막 개체가 아닐시 추가용으로 콤마 추가 
            //if (i < Data.size() - 1) {jsonBuilder.append(",\n");} 
            //else {jsonBuilder.append("\n");}
        }
        //배열생성 종료
        jsonBuilder.append("]");

        File file = new File("db/wiseSaying/data.json");
        dataJsonMaker(); // 파일 초기화 

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) 
        {
            writer.write(jsonBuilder.toString());
            writer.flush();
        } 
        catch (IOException e) { System.out.println("파일 저장 중 오류 발생: " + e.getMessage()); }
    }

    void dataJsonReader(List<Data> data)
    {
        File file = new File("db/wiseSaying/data.json");
    
        try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) 
        {
            StringBuilder jsonContent = new StringBuilder();
            String line;
    
            while ((line = reader.readLine()) != null) { jsonContent.append(line).append("\n"); }
           
            String json = jsonContent.toString().trim();
            
            if (json.startsWith("[") && json.endsWith("]")) json = json.substring(1, json.length() - 1).trim();  // 대괄호 해체
            if (json.isEmpty()) return;  // <== 빈 배열일경우 추가연산 없이 탈출.
            
            String[] objects = json.split("},\\s*\\{");// 추출 정규식
    
            
            for (String item : objects) //가독성 강화
            {
                item = item.trim();
    
                // 괄호 해체
                if (item.startsWith("{")) { item = item.substring(1).trim(); }
                if (item.endsWith("}")) { item = item.substring(0, item.length() - 1).trim(); }
    
                // id, content, author 추출
                int id         = Integer.parseInt(json_ExtractValue2(item, "\"id\":", ","));
                String content = json_ExtractValue2(item, "\"content\":", ",");
                String author  = json_ExtractValue2(item, "\"author\":", null);
    
                // 데이터 입력
                data.add(new Data(id, content, author));
            }
            /** 이전 버전

            for (int i = 0; i < objects.length; i++) 
            {
                String item = objects[i].trim();

                // 괄호 해체
                if (i == 0 && item.startsWith("{")) { item = item.substring(1).trim(); }
                if (i == objects.length - 1 && item.endsWith("}")) { item = item.substring(0, item.length() - 1).trim(); }
    
                // id, content, author 추출
                int    id      = Integer.parseInt(json_ExtractValue2(item, "\"id\":", ","));
                String content = json_ExtractValue2(item, "\"content\":", ",");
                String author  = json_ExtractValue2(item, "\"author\":", null);
                
                //데이터 입력
                data.add(new Data(id, content, author));
            }
            */
            
        } 
        catch (IOException e) {System.out.println("data.json 접근 오류: 색인에 실패했습니다." + e.getMessage());}

    }

    String dataJson_ExtractValue(String json, String key, String delimiter) //추출기
    {
        int startIdx = json.indexOf(key) + key.length();
        int endIdx = delimiter != null ? json.indexOf(delimiter, startIdx) : json.length();
        return json.substring(startIdx, endIdx).trim().replaceAll("[\"{}]", "");

    }

    /////////////////////////////////명령어 함수 저장공간//////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    static void 명령_등록(List<Data> data ,int count ,Scanner sc)
    {
        count++;
        System.out.print("명언 : ");
        String insert_data_1 = sc.nextLine();
        System.out.print("작가 : ");
        String insert_data_2 = sc.nextLine();
        System.out.println(count+"번 명언이 등록되었습니다.");
                
        data.add(0,new Data(count, insert_data_1, insert_data_2));
    }
    
    static void 명령_목록(List<Data> data ,Scanner sc)
    {
        System.out.println("번호 / 작가 / 명언\n----------------------");
                for (Data d : data) { d.printData(); }
    }

    static void 명령_삭제(List<Data> data, String command)
    {
        int number = Integer.parseInt(command.substring("삭제?id=".length())); 
        boolean found  = false; //확인용
          
        for (int i = 0; i < data.size(); i++) 
        {
            if (data.get(i).number == number) 
            {
                System.out.println(number + "번 명언이 삭제되었습니다.");
                data.remove(i);  // 해당 명언 삭제
                found = true;
                break;
            }
        }

        if (!found) { System.out.println(number + "번 명언은 존재하지 않습니다."); }
    }

    static void 명령_수정(List<Data> data, String command, Scanner sc)
    {
        int number = Integer.parseInt(command.substring("수정?id=".length())); 
        boolean found  = false; //확인용

        for (int i = 0; i < data.size(); i++) 
        {
            if (data.get(i).number == number) 
            {
                System.out.println("명언(기존) : " + data.get(i).content);
                System.out.print("명언 : ");
                String insert_data_1 = sc.nextLine();
                        
                System.out.println("작가(기존) : " + data.get(i).author);
                System.out.print("작가 : ");
                String insert_data_2 = sc.nextLine();

                data.set(i, new Data(number, insert_data_1, insert_data_2));
                found = true;
                        
                break;
            }
        }

        if (!found) { System.out.println(number + "번 명언은 존재하지 않습니다."); }
    }

    static void 명령_커밋(List<Data> data ,int count)
    {
        fileWriter_Json_alpha(data);
        fileWriter_lastID(count);//덮어쓰고 재갱신
        data.clear();

        fileReader_Json_alpha(data);
        count = fileReader_lastID();
    }
    static void 명령_초기화(List data , String command , int count)
    {
        File directory = new File("db/wiseSaying");
        if (directory.isDirectory()) 
        {
            File[] files = directory.listFiles();
            if (files != null) 
            { 
                for (File file : files) { file.delete(); }
            }
        }
        fileMaker_ID();
        count = fileReader_lastID();
        System.out.println("초기화 완료.");

    }
}
class Data 
{
    int     number;   // 순서 번호
    String content;   // 명언 내용
    String  author;   // 작가

    public Data(int number, String content, String author) {
        this.number = number;
        this.content = content;
        this.author = author;
     
    }
    // 출력용 
    public void printData() {
        System.out.println(number + " / " + content + " / " + author);
    }
}
