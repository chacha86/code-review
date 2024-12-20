import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    App app = new App();
    Scanner in = new Scanner(System.in);
    while(true) {
      System.out.print("Command) ");
      String command = in.nextLine();
      if(app.parseCommand(command) == false) {
        break;
      }
    }
  }
}
