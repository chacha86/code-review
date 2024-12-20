package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void quotes() throws IOException, ParseException {
        int number = 0;
        Scanner in = new Scanner(System.in);
        String command = "";
        Map<Integer,String> words = new HashMap<>();
        Map<Integer,String> names = new HashMap<>();
        File dir = new File("E:\\JavaStudy\\quotesList\\db\\wiseSaying");
        File files[] = dir.listFiles();
        for(int i = 0; i < files.length; i++){
            String filename = files[i].getName().split("\\.")[0];
            if(isNumberic(filename)){
                JSONParser parser = new JSONParser();
                Reader reader = new FileReader(files[i].getPath());
                JSONObject jsonObject = (JSONObject) parser.parse(reader);

                String w = (String) jsonObject.get("content");
                String n = (String) jsonObject.get("author");
                int num = Integer.parseInt(String.valueOf(jsonObject.get("id")));

                words.put(num, w);
                names.put(num, n);

                if(number < num)
                    number = num;

                reader.close();
            }
        }

        while(true){
            System.out.println("== 명언 앱 ==");
            System.out.print("명령) ");
            command = in.nextLine();
            if(command.equals("종료"))
                break;
            else if(command.equals("등록")){
                System.out.print("명언 : ");
                String word = in.nextLine();
                System.out.print("작가 : ");
                String name = in.nextLine();
                number++;
                words.put(number, word);
                names.put(number, name);
                System.out.println(number+ "번 명언이 등록되었습니다.");
                file_save(number, word, name);
                File f = new File("E:\\JavaStudy\\quotesList\\db\\wiseSaying\\lastId.txt");
                FileWriter fw = new FileWriter(f, false) ;
                fw.write(number + "");
                fw.flush();
                fw.close();
            }
            else if(command.equals("목록")){
                System.out.println("번호 / 작가 / 명언");
                for(int key : words.keySet()){
                    System.out.println(key + " / " + names.get(key) + " / " + words.get(key));
                }
            }
            else if(command.contains("삭제")){
                int rm = Integer.parseInt(command.split("=")[1]);
                if(words.containsKey(rm)){
                    words.remove(rm);
                    names.remove(rm);
                    System.out.println(rm+"번 명언이 삭제되었습니다.");
                    Path path = Paths.get("E:\\JavaStudy\\quotesList\\db\\wiseSaying\\"+rm+".json");
                    try {
                        Files.delete(path);
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
                else
                    System.out.println(rm+"번 명언은 존재하지 않습니다.");

            }
            else if(command.contains("수정")){
                int rp = Integer.parseInt(command.split("=")[1]);
                if(words.containsKey(rp)){
                    System.out.println("명언(기존) : " + words.get(rp));
                    System.out.print("명언 : ");
                    String word = in.nextLine();
                    System.out.println("작가(기존) : " + names.get(rp));
                    System.out.print("작가 : ");
                    String name = in.nextLine();
                    words.put(rp, word);
                    names.put(rp, name);
                    file_save(rp, word, name);
                }
                else
                    System.out.println(rp+"번 명언은 존재하지 않습니다.");
            }
            else if(command.equals("빌드")){
                JSONArray jsonArray = new JSONArray();
                for(int key : words.keySet()){
                    JSONObject obj = new JSONObject();
                    obj.put("id", key);
                    obj.put("content", words.get(key));
                    obj.put("author", names.get(key));
                    jsonArray.add(obj);
                }
                try {
                    FileWriter file = new FileWriter("E:\\JavaStudy\\quotesList\\db\\wiseSaying\\data.json");
                    file.write(jsonArray.toJSONString());
                    file.flush();
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public static boolean isNumberic(String str) {
        return str.matches("[+-]?\\d*(\\.\\d+)?");
    }

    public static void file_save(int number, String word, String name){
        JSONObject obj = new JSONObject();
        obj.put("id", number);
        obj.put("content", word);
        obj.put("author", name);
        try {
            FileWriter file = new FileWriter("E:\\JavaStudy\\quotesList\\db\\wiseSaying\\"+number+".json");
            file.write(obj.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, ParseException {
        quotes();
    }
}