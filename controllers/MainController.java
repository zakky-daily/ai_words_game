package controllers;
import models.ConnectGemini;
import java.awt.image.BufferedImage;
import views.*;

public class MainController {
    public MainFrame mainFrame;
    public ConnectGemini connectGemini;

    private volatile BufferedImage gameSceneImage;
    private volatile BufferedImage judgeSceneImage;
    
    public MainController() {
        mainFrame = new MainFrame();            // フレームの生成
        startTitle();                           // タイトル画面追加
        connectGemini = new ConnectGemini();    // Geminiリクエスト用のインスタンスを生成
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

    public void setGameSceneImage(BufferedImage image) {
        this.gameSceneImage = image;
    }

    public BufferedImage getGameSceneImage() {
        return gameSceneImage;
    }

    public void setJudgeSceneImage(BufferedImage image) {
        this.judgeSceneImage = image;
    }

    public BufferedImage getJudgeSceneImage() {
        return judgeSceneImage;
    }
}
