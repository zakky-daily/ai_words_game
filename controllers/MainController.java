package controllers;
import models.ConnectGemini;
import views.*;

public class MainController {
    public MainFrame mainFrame;
    public ConnectGemini connectGemini;
    
    public MainController() {

        // フレームの生成
        mainFrame = new MainFrame();

        // タイトル画面追加
        startTitle();
        
        // とりあえず今は、いきなりゲーム開始とする
        // new GameController(mainFrame);

        connectGemini = new ConnectGemini();
    }

    public void changeScene(MainFrame m){
        m.revalidate();
        m.repaint();
    }

    public void startTitle(){
        new TitleController(this);
    }

    public void startGame(){
        new GameController(this);
    }

    public void startJudge(String themeKey, String createdText){
        new JudgeController(this, themeKey, createdText);
    }
}