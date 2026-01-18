package controllers;
import views.*;

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
        TitleController titleCtrl = new TitleController(this);
    }

    public void startGame(){
        GameController gameCtrl = new GameController(this);
    }

    public void startJudge(){
        JudgeController judgeCtrl = new JudgeController(this);
    }
}