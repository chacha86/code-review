package spring;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WiseSayingService {
    WiseSayingRepository repository = new WiseSayingRepository();
    AppContext appContext = AppContext.getInstance();

    String 명령_등록(String insert_data_1, String insert_data_2)
    {
        appContext.count++;         
        appContext.data.add(0,new WiseSaying(appContext.count, insert_data_1, insert_data_2));
        return (appContext.count+"번 명언이 등록되었습니다.");
    }

    List<String> 명령_목록() 
    {
        List<String> formattedList = new ArrayList<>();
        for (WiseSaying line : appContext.data) { formattedList.add(line.formatPrint());}
        return formattedList;
    }
    String 명령_삭제(String command)
    {
        int number = Integer.parseInt(command.substring("삭제?id=".length())); 
        boolean found  = false; //확인용
        String resultMessage = "";
          
        for (int i = 0; i < appContext.data.size(); i++) 
        {
            if (appContext.data.get(i).number == number) 
            {
                resultMessage = number + "번 명언이 삭제되었습니다."; 
                appContext.data.remove(i);  // 해당 명언 삭제
                found = true;
                break;
            }
        }
        if (!found) { resultMessage = number + "번 명언은 존재하지 않습니다."; }

        return resultMessage;
    }

    String 명령_수정(int number, String insert_data_1, String insert_data_2)
    {
        String resultMessage = "";
        boolean found  = false; //확인용

        for (int i = 0; i < appContext.data.size(); i++) 
        {
            if (appContext.data.get(i).number == number) 
            {
                appContext.data.set(i, new WiseSaying(number, insert_data_1, insert_data_2));
                found = true;
                break;
            }
        }

        if (!found) { resultMessage = (number + "번 명언은 존재하지 않습니다."); }
        return resultMessage;
    }

    void 명령_빌드()
    {
        repository.ReLoad();
    }
    void 명령_초기화()
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
        
        appContext.data.clear();
        repository.dataLoad();
    }
    
}
