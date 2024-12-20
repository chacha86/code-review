package wiseSaying;

import org.json.simple.parser.ParseException;
import java.io.*;
import java.util.*;

public class App {
    public final wiseSayingController controller;
    public App(){
        this.controller = new wiseSayingController();
    }
    public void run() throws IOException {
        int number = 0;
        String[] commands = {"등록", "종료", "삭제", "수정", "빌드", "목록"};
        Scanner in = new Scanner(System.in);
        String command = "";

        while(true){
            System.out.println("== 명언 앱 ==");
            System.out.print("명령) ");
            command = in.nextLine();

            if(command.length() < 2 || !Arrays.asList(commands).contains(command.substring(0, 2))){
                System.out.println("존재하지 않는 커맨드입니다.");
            }
            else if(command.equals("종료")){
                break;
            }
            else{
                controller.control(command);
            }
        }
    }
}
