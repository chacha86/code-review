package wiseSaying;

import java.util.Scanner;

public class WiseSayingController {

    WiseSayingService wiseSayingService = new WiseSayingService();
    Scanner scanner = new Scanner(System.in);
    WiseSaying wiseSaying = new WiseSaying();

    // 등록
    public void insertWise(){

        System.out.print("명언 : ");
        wiseSaying.setWise(scanner.nextLine());// 입력값 가져옴 / 입력값이 없으면 기다린다.

        System.out.print("작가 : ");
        wiseSaying.setAuthor(scanner.nextLine());

        wiseSayingService.insertWise(wiseSaying);
        System.out.println(wiseSaying.getLastId()+"번 명언이 등록되었습니다.");

    }

    //목록
    public void listWise(){
        wiseSayingService.listWise(wiseSaying);
    }

    public void deleteWise(){

        System.out.print("몇번 명언을 삭제하시겠습니까?:");
        wiseSaying.setRemoveKey(scanner.nextLine());

        wiseSayingService.deleteWise(wiseSaying);
        System.out.println(wiseSaying.getRemoveKey() + "번 명언이 삭제되었습니다.");
    }

    public void updateWise(){
        System.out.print("몇번 명언을 수정하시겠습니까?:");
        wiseSaying.setUpdateKey(scanner.nextLine());
        wiseSayingService.updateWise(wiseSaying);
    }

    public void buildWise(){
        wiseSayingService.buildWise(wiseSaying);
    }
}
