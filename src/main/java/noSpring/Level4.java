package noSpring;

import java.util.Scanner;

public class Level4 {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("== 명언 앱 ==");

        int cnt=1;
        while (true) {
            System.out.print("명령) ");
            String command=sc.nextLine().trim();
            if (command.equals("종료")) {
                break;
            }
            else if (command.equals("등록")) {
                System.out.print("명언 : ");
                String quote = sc.nextLine().trim();

                System.out.print("작가 : ");
                String author = sc.nextLine().trim();

                System.out.printf("%d번 명언이 등록되었습니다.", cnt++);
                System.out.println();
            }
        }
    }
}