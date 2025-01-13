import wiseSaying.WiseSayingController;
import wiseSaying.repository.WiseSayingFileRepository;
import wiseSaying.WiseSayingService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        App app = new App(new Scanner(System.in));
        app.run();
    }
}