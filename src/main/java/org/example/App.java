package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.List;
import java.util.Scanner;

class App {

    private static final ObjectMapper obj = new ObjectMapper();
    private static final String FOLDER_PATH = "db/";
    private static final File LAST_ID_FILE = new File(FOLDER_PATH + "lastId.txt");

    private static int lastId;


    public void init() {
        FileUtil.ensureFolderExists(FOLDER_PATH);
        FileUtil.ensureFileExistsWithDefaultvalue(LAST_ID_FILE,1);
        lastId = FileUtil.readValueFromFile(LAST_ID_FILE,Integer.class,1);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");
            String command = scanner.nextLine().trim();

            if(command.equals("등록")) {
                registerWiseSaying(scanner);
            }
            else if(command.equals("목록")) {
                printWiseSayings();
            }
            else if(command.equals("종료")) {
                System.out.println("명언 앱을 종료합니다.");
                saveLastId();
                break;
            }
            else if(command.equals("빌드")) {
                System.out.println("data.json 파일의 내용이 갱신되었습니다.");
                mergeJsonFile();
            }
            else if(command.startsWith("삭제")) {
                deleteSaying(extractIdFromCommand(command));
            }
            else if(command.startsWith("수정")) {
                modifySaying(extractIdFromCommand(command), scanner);
            }
            else {
                System.out.println("존재하지 않는 명령어 입니다.");
            }
        }
    }

    private void registerWiseSaying(Scanner scanner) {

        System.out.print("명언 : ");
        String quote = scanner.nextLine();

        System.out.print("작가 : ");
        String author = scanner.nextLine();

        WiseSaying wiseSaying = new WiseSaying(lastId, quote, author);
        File jsonFile = new File(FOLDER_PATH + wiseSaying.getId() + ".json");

        FileUtil.writeValueToFile(jsonFile,wiseSaying);
        System.out.println(lastId++ + "번째 명언이 등록되었습니다.");
    }

    private void printWiseSayings() {

        List<WiseSaying> wiseSayings = FileUtil.readWiseSayingFromFolder(FOLDER_PATH);

        if(wiseSayings.isEmpty()){
            System.out.println("명언이 존재하지 않습니다.");
        } else{
            wiseSayings.forEach(System.out::println);
        }
    }

    private void deleteSaying(int id) {

        File jsonFile = new File(FOLDER_PATH + id + ".json");

        if (jsonFile.exists() && jsonFile.delete()) {
            System.out.println(id + "번째 명언이 삭제되었습니다.");
        } else {
            System.out.println(id + "번째 명언이 존재하지 않습니다.");
        }
    }

    private void modifySaying(int id, Scanner scanner) {
        File jsonFile = new File(FOLDER_PATH + id + ".json");
        WiseSaying getWiseSaying = FileUtil.readValueFromFile(jsonFile, WiseSaying.class, null);

        if(getWiseSaying == null){
            System.out.println(id + "번째 명언이 존재하지 않습니다.");
            return;
        }

        int lastIndexQuote = getWiseSaying.getQuote().lastIndexOf(" ");
        String quote = getWiseSaying.getQuote().substring(0,lastIndexQuote);

        int lastIndexAuthor = getWiseSaying.getAuthor().lastIndexOf(" ");
        String author = getWiseSaying.getAuthor().substring(0,lastIndexAuthor);

        System.out.println("명언(기존) : " + quote);
        System.out.print("명언 : ");
        String modifySaying = scanner.nextLine();

        System.out.println("작가(기존) : " + author);
        System.out.print("작가 : ");
        String modifyAuthor = scanner.nextLine();

        WiseSaying updatedSaying = new WiseSaying(id, modifySaying, modifyAuthor);
        FileUtil.writeValueToFile(jsonFile,updatedSaying);
    }

    private void saveLastId()
    {
       FileUtil.writeValueToFile(LAST_ID_FILE, lastId);
    }

    private int extractIdFromCommand(String command){

        String[] splitId = command.split("=");

        return Integer.parseInt(splitId[1]);
    }

    private void mergeJsonFile()
    {
        List<WiseSaying> mergedWiseSaying = FileUtil.readWiseSayingFromFolder(FOLDER_PATH);
        File mergedJsonFile = new File(FOLDER_PATH + "data.json");

        if(!mergedWiseSaying.isEmpty()){
            FileUtil.writeValueToFile(mergedJsonFile,mergedWiseSaying);
        }
    }
}

