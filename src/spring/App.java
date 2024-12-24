package spring;

public class App {
    public void run()
    {
        System.out.println("== 명언 앱 ==");
        
        AppContext appContext = AppContext.getInstance();
        appContext.initializeDataLoad();

        WiseSayingService wiseSayingService = new WiseSayingService();
        WiseSayingController     controller = new WiseSayingController(wiseSayingService);

        while (true) {
            System.out.print("명령) ");
            String command = appContext.sc.nextLine();

            if ("종료".equals(command)) {
                controller.executeCommand(command);
                break;
            } 
            else {
                controller.executeCommand(command);
            }
        }
        appContext.closeScanner();
    
    }
    
}
