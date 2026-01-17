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

    public void changeScene(MainFrame m){
        m.revalidate();
        m.repaint();
    }

    public void startTitle(){
        mainFrame.getContentPane().removeAll();
        TitleController titleCtrl = new TitleController(this);
    }

    public void startGame(){
        mainFrame.getContentPane().removeAll();
        GameController gameCtrl = new GameController(this);
    }
}