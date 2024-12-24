package spring;

import java.util.List;

public class WiseSayingController {
    private final WiseSayingService wiseSayingService;
    AppContext appContext = AppContext.getInstance();

    public WiseSayingController(WiseSayingService wiseSayingService) {
        this.wiseSayingService = wiseSayingService;
    }

    public void executeCommand(String command) {
        if (command.startsWith("삭제?id=")) {
            ctrl_Delete(command);
        }
        else if(command.startsWith("수정?id="))
        {
            ctrl_Modify(command);
        }
        else
        switch (command) {
            case "종료":
                ctrl_Exit();
                break;

            case "등록":
            
                ctrl_Update();
                break;

            case "목록":
                
                ctrl_List();
                break;  
                
            
            case "빌드":
                ctrl_Build();
                break;   


            case "초기화":
                ctrl_Clear();
                break;

            default:
                System.out.println("잘못된 명령입니다.");
                break;
        }
    }

    private void ctrl_Exit()
    {
        System.out.println("프로그램 종료");

    }
    private void ctrl_Update()
    {
        System.out.print("명언 : ");
        String insert_data_1 = appContext.sc.nextLine();
        System.out.print("작가 : ");
        String insert_data_2 = appContext.sc.nextLine();
        
        String result = wiseSayingService.명령_등록(insert_data_1,insert_data_2);
        System.out.println(result);
    }
    private void ctrl_List()
    {
        System.out.println("번호 / 작가 / 명언\n----------------------");
        List<String> result = wiseSayingService.명령_목록();
        for (String print : result) { System.out.println(print); }
    }

    private void ctrl_Delete(String command)
    {
        String result = wiseSayingService.명령_삭제(command);
        System.out.println(result);

    }

    private void ctrl_Modify(String command)
    {
        int id = Integer.parseInt(command.substring("수정?id=".length())); 

        WiseSaying number = ID_Extract(id);
        
        if (number == null) {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
            return;
        }
        
        System.out.println("명언(기존) : " + number.content);
        System.out.print("명언 : ");
        String insert_data_1 = appContext.sc.nextLine();
                        
        System.out.println("작가(기존) : " + number.author);
        System.out.print("작가 : ");
        String insert_data_2 = appContext.sc.nextLine();
    
        String result = wiseSayingService.명령_수정(id, insert_data_1, insert_data_2);
        if(!result.equals("")) System.out.println(result);
         
    }
    private WiseSaying ID_Extract(int id) {
        for (WiseSaying wiseSaying : appContext.data) {
            if (wiseSaying.number == id) {
                return wiseSaying; 
            }
        }
        return null; 
    }
    
    
    private void ctrl_Build()
    {
        wiseSayingService.명령_빌드();
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");


    }
    private void ctrl_Clear()
    {
        System.out.print("초기화 하시겠습니까? (y/n): ");
        String input = appContext.sc.nextLine();  

        if ("y".equalsIgnoreCase(input)) { 
            wiseSayingService.명령_초기화(); 
            System.out.println("초기화가 완료되었습니다.");
        } 
        else {
            System.out.println("초기화가 취소되었습니다.");
        }

    }
    
}
