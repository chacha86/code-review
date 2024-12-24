package spring;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppContext { //캡슐화는 일단 나중에 해보고
    private static AppContext instance;

    private boolean isInitialized;//초기화 여부

    // public으로 변경하여 직접 접근 가능 >>캡슐화는 좀다 하자 일단 구현먼저
    public Scanner sc;
    public List<WiseSaying> data;
    public int count;

    private AppContext() {
        sc = new Scanner(System.in);
        data = new ArrayList<>();
        count = 0;  // 초기 카운트 값 설정

        isInitialized = false;
    } 

    public static AppContext getInstance() {
        if (instance == null) {
            instance = new AppContext();
        }
        return instance;
    }

    public void initializeDataLoad() {
        if (!isInitialized) 
        {
            WiseSayingRepository repository = new WiseSayingRepository();
            repository.dataLoad();  // 데이터 로드
            isInitialized = true;   // 초기화 완료 표시
        }
    }

    public void closeScanner() {
        sc.close();
    }
    
}
