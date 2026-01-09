package controllers;
import views.*;//←この書き方で別ディレクトリのファイル全部使える。

public class MainController {
    public MainController() {

        // フレームの生成
        MainFrame mainFrame = new MainFrame();
        
        // とりあえず今は、いきなりゲーム開始とする
        new GameController(mainFrame);
    }
}
