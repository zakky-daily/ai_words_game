package controllers;
import views.MainFrame;

public class MainController {
    public MainController() {

        // フレームの生成
        MainFrame mainFrame = new MainFrame();

        // とりあえず今は、いきなりゲーム開始とする
        mainFrame.startGame();

        mainFrame.setVisible(true);
    }
}
