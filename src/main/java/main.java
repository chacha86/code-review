
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class main {
    private static final List<Quote> quotes=new ArrayList<>();
    private static int nextId=1;

    public static void main(String[] args) {
        loadQuotesFile();   //기존 파일 로드
        Scanner scanner=new Scanner(System.in);

        while(true){
            System.out.println("==명언 앱==");
            System.out.print("명령) ");
            String command = scanner.nextLine().trim();

            if(command.equals("종료")){
                break;
            } else if (command.startsWith("등록")) {
                registerQuote(scanner);
            } else if (command.equals("목록")) {
                listQuotes();
            } else if (command.startsWith("삭제?id=")) {
                deleteQuote(command);
            } else if (command.startsWith("수정?id=")) {
                modifyQuote(command,scanner);
            } else if (command.equals("빌드")) {
                buildQuotesJsonFile();
            }
        }
        scanner.close();    //리소스해제
    }


    private static void registerQuote(Scanner scanner){ //등록 메서드
        System.out.print("명언: ");
        String content=scanner.nextLine().trim();
        System.out.print("작가: ");
        String author=scanner.nextLine().trim();

        Quote quote=new Quote(nextId++,content,author);
        quotes.add(quote);

        saveQuoteToFile(quote); //파일에 저장
        saveLastId();   //ID 저장

        System.out.println(quote.id+"번 명언이 등록되었습니다.");
    }

    private static void listQuotes() {  //목록 확인 메서드
        if(quotes.isEmpty()){
            System.out.println("명언을 등록하세요");
            return;
        }

        System.out.println("번호 / 작가 / 명언 ");
        System.out.println("----------------");

        for(int i=quotes.size()-1;i>=0;i--){    //역순 출력
            Quote quote=quotes.get(i);  //i번째 명언
            System.out.println(quote.id+" / "+
                    quote.author+" / "+ quote.content);
        }
    }

    private static void deleteQuote(String command){    //삭제 메서드
        int id=Integer.parseInt(command.substring(6));  //삭제?id=n, id=6번째 문자

        for(int i=0;i<quotes.size();i++){
            if (quotes.get(i).id==id){  //i번째 명언의 id
                quotes.remove(i);
                System.out.println(id+"번 명언이 삭제되었습니다. ");
                String path = System.getProperty("user.dir");
                File file=new File(path+"/db/wiseSaying/"+id+".json");
                file.delete();
                return;
            }
        }
        System.out.println(id+"번 명언은 존재하지 않습니다.");
    }
    private static void modifyQuote(String command,Scanner scanner){    //수정 메서드
        int id=Integer.parseInt(command.substring(6));

        for(Quote quote:quotes){
            if(quote.id==id){
                System.out.println("명언(기존): "+ quote.content);
                System.out.print("명언: ");
                quote.content=scanner.nextLine().trim();

                System.out.println("작가(기존):"+quote.author);
                System.out.print("작가: ");
                quote.author=scanner.nextLine().trim();

                saveQuoteToFile(quote);
                System.out.println(id+"번 명언 수정.");
                return;
            }
        }
        System.out.println(id+ "번 명언은 존재하지 않습니다");
    }

    private static void saveQuoteToFile(Quote quote){
        String path = System.getProperty("user.dir");
        try{
            JSONObject json=new JSONObject();   //Json 객체 생성
            json.put("id",quote.id);
            json.put("content",quote.content);
            json.put("author",quote.author);

            File file=new File(path+"/db/wiseSaying/"+quote.id+".json");
            FileWriter writer=new FileWriter(file); //FileWriter 객체 생성
            writer.write(json.toString());  //문자열로 변환하여 작성
            writer.close(); //리소스 해제
        } catch (IOException e){
            System.out.println("json파일 저장 실패"+e.getMessage());
        }
    }

    private static void saveLastId(){
        String path = System.getProperty("user.dir");
        try{
            FileWriter writer=new FileWriter(path+"/db/wiseSaying/lastId.txt");
            writer.write(String.valueOf(nextId-1));
            writer.close();
        } catch(IOException e){
            System.out.println("오류 발생"+e.getMessage());
        }
    }

    private static void loadQuotesFile(){
        String path = System.getProperty("user.dir");
        File folder=new File(path+"/db/wiseSaying");
        File[] listOfFiles=folder.listFiles();

        if(listOfFiles!=null){
            for(File file:listOfFiles){
                if(file.isFile() && file.getName().endsWith(".json")){
                    try{
                        String content=new String(Files.readAllBytes(file.toPath()));
                        JSONObject json=new JSONObject(content);

                        //data.json만 해당되는 예외 허용-> json형식이 아니어도 pass
                        if(file.getName().equals("data.json")){
                            //file.getName().equals("data.json");
                            continue;
                        }

                        int id=json.getInt("id");
                        String quoteContent=json.getString("content");
                        String author=json.getString("author");

                        quotes.add(new Quote(id,quoteContent,author));
                        nextId=Math.max(nextId,id+1);
                    }catch(IOException e){
                        System.out.println("파일 로드 실패"+e.getMessage());
                    }catch (JSONException e) {
                        System.out.println("JSON 파싱 실패: " + e.getMessage());
                    }
                }
            }
        }
        else {
            System.out.println("파일을 찾을 수 없음");
        }
    }

    private static void buildQuotesJsonFile() { //빌드 명령
        String path = System.getProperty("user.dir");
        File file=new File(path+"/db/wiseSaying/data.json");

        //기존에 파일 있으면 삭제 후 다시 생성
        if (file.exists()) {
            if (!file.delete()) {
                System.out.println("기존 data.json 파일 삭제 실패. 파일이 사용 중일 수 있습니다.");
                return; // 삭제 실패 시 메서드를 종료
            }
        }

        try(FileWriter writer=new FileWriter(file)){
            writer.write("[\n");
            JSONArray jsonArray=new JSONArray(); //jsonarray 객체 생성

            for(int i=0;i<quotes.size();i++){
                Quote quote=quotes.get(i);
                JSONObject json=new JSONObject();
                json.put("id",quote.id);
                json.put("content",quote.content);
                json.put("author",quote.author);
                jsonArray.put(json);
                writer.write(json.toString());
                if(i<quotes.size()-1){
                    writer.write(",\n");
                }
            }
            //writer.write(jsonArray.toString());
            writer.write("\n]");
            writer.close();
            System.out.println("data.json 파일의 내용이 갱신되었습니다.");
        }catch (IOException e){
            System.out.println("빌드 실패"+e.getMessage());
        }
    }


}