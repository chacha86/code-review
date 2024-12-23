import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        WiseSayingRepository repository = new WiseSayingRepository();
        WiseSayingService service = new WiseSayingService(repository);
        WiseSayingController controller = new WiseSayingController(scanner, service);

        App app = new App(scanner, controller);
        app.run();
    }
}