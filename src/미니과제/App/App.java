package src.미니과제.App;


import src.미니과제.BulletinBoardController.BulletinBoardController;

public class App {
    private final BulletinBoardController controller;

    public App() {
        this.controller = new BulletinBoardController();
    }

    public void run() {
        controller.start();
    }
}
