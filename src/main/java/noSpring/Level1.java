package noSpring;

import java.util.Scanner;

public class Level1 {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");
            String command=sc.nextLine().trim();
            if (command.equals("종료")) {
                break;
            }
        }
    }
}
