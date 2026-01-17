package controllers;
import views.*;//←この書き方で別ディレクトリのファイル全部使える。

public class MainController {
    public MainFrame mainFrame;
    public MainController() {

        // フレームの生成
        mainFrame = new MainFrame();

        // タイトル画面追加
        startTitle();
        
        // とりあえず今は、いきなりゲーム開始とする
        // new GameController(mainFrame);
    }

    public void startTitle(){
        TitleController titleCtrl = new TitleController(this);
        mainFrame.startTitle();
    }

    public void startGame(){
        GameController gameCtrl = new GameController(this);
        mainFrame.startGame();
    }
}