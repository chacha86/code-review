package noSpring;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Level6 {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("== 명언 앱 ==");
        List<Quote> quotes=new ArrayList<>();


        int cnt=1;
        while (true) {
            System.out.print("명령) ");
            String command=sc.nextLine().trim();
            // 삭제 기능을 위해 ? 기준으로 분리
            switch (command.split("\\?")[0]) {
                case "종료": {
                    return;
                }
                case "등록": {
                    System.out.print("명언 : ");
                    String quote = sc.nextLine().trim();

                    System.out.print("작가 : ");
                    String author = sc.nextLine().trim();

                    quotes.add(new Quote(cnt++,author,quote)); // 배열에 추가

                    System.out.printf("%d번 명언이 등록되었습니다.", cnt-1);
                    System.out.println();

                    break;
                }
                case "목록" : {
                    System.out.println("번호  / 작가 / 명언");
                    System.out.println("---------------------------");
                    for (int i = quotes.size()-1; i >=0 ; i--) {
                        System.out.println(quotes.get(i));
                    }
                    break;
                }
                case "삭제" : {
                    String data=command.split("\\?")[1]; // id=1
                    int id=Integer.parseInt(data.split("=")[1]);

                    for (int i=0;i<quotes.size();i++) {
                        if (quotes.get(i).getId()==id) {
                            quotes.remove(i);
                            System.out.println((i+1)+"번 명언이 삭제되었습니다.");
                        }
                    }

                    break;
                }
                default:
                    break;
            }
        }
    }
}


