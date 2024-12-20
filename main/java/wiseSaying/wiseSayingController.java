package wiseSaying;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class wiseSayingController {
    public final Scanner in;
    public final wiseSayingService wiseSayingService;

    public wiseSayingController(){
        this.in = new Scanner(System.in);
        this.wiseSayingService = new wiseSayingService();
    }

    public void control(String command) throws IOException {

        if(command.equals("등록")){
            System.out.print("명언 : ");
            String content = in.nextLine();
            System.out.print("작가 : ");
            String author = in.nextLine();

            wiseSayingService.addSaying(content, author);
            System.out.println(wiseSayingService.getLastId()+ "번 명언이 등록되었습니다.");
        }
        else if(command.startsWith("목록?keywordType")){
            String[] commands = command.split("=");
            String keywordType = commands[1].split("&")[0];
            String keyword = commands[2];

            System.out.println("번호 / 작가 / 명언");
            List<WiseSaying> wiseSayingList = wiseSayingService.searchSaying(keywordType, keyword);
            for(WiseSaying wiseSaying : wiseSayingList){
                System.out.println(wiseSaying.getId() + " / " + wiseSaying.getAuthor() + " / " + wiseSaying.getContent());
            }
        }
        else if(command.startsWith("목록")){
            int page = 1;

            if(command.contains("?"))
                page = Integer.parseInt(command.split("=")[1]);

            System.out.println("번호 / 작가 / 명언");
            System.out.println("----------------");

            List<WiseSaying> wiseSayingList = wiseSayingService.getPageWiseSayings(page);
            for(WiseSaying wiseSaying : wiseSayingList){
                System.out.println(wiseSaying.getId() + " / " + wiseSaying.getAuthor() + " / " + wiseSaying.getContent());
            }
            System.out.println("----------------");
            System.out.print("페이지 : ");

            int maxPage = wiseSayingService.getMaxPage();
            for(int i = 1; i <= maxPage; i++){
                if(i == page)
                    System.out.print(" ["+ i + "] ");
                else
                    System.out.print(" "+i+" ");

                if(i != maxPage)
                    System.out.print("/");
                else
                    System.out.println();
            }
        }
        else if(command.startsWith("삭제?")){
            int rm = Integer.parseInt(command.split("=")[1]);
            if(wiseSayingService.contentExists(rm)) {
                wiseSayingService.deleteSaying(rm);
            }
            else
                System.out.println(rm+"번 명언은 존재하지 않습니다.");
        }
        else if(command.startsWith("수정?")){
            int rp = Integer.parseInt(command.split("=")[1]);
            if(wiseSayingService.contentExists(rp)){
                Map<Integer, WiseSaying> wiseSayingMap = wiseSayingService.getWiseSayings();
                System.out.println("명언(기존) : " + wiseSayingMap.get(rp).getContent());
                System.out.print("명언 : ");
                String content = in.nextLine();
                System.out.println("작가(기존) : " + wiseSayingMap.get(rp).getAuthor());
                System.out.print("작가 : ");
                String author = in.nextLine();
                wiseSayingService.updateSaying(rp, content, author);
            }
            else
                System.out.println(rp+"번 명언은 존재하지 않습니다.");
        }
        else if(command.equals("빌드")){
            wiseSayingService.build();
        }
    }
}
