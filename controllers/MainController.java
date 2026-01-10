package controllers;
import views.MainFrame;
import views.TitleScene;

public class MainController {
    public MainController() {

        // フレームの生成
        MainFrame mainFrame = new MainFrame();

        // タイトル画面追加
        new TitleController(mainFrame);
        
        // とりあえず今は、いきなりゲーム開始とする
        // new GameController(mainFrame);
    }
}