package views;
import javax.swing.*;

public class MainFrame extends JFrame {
    public GameScene gameScene;
    public MainFrame() {
        this.setSize(980, 700);//サイズ指定
        this.setLocationRelativeTo(null);//中央にウィンドウが出現
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//xボタンで終了
    }

    public void startTitle() {
        TitleScene titleScene = new TitleScene();
        this.add(titleScene);
    }

    public void startGame() {//gamescene作って表示
        gameScene = new GameScene();
        this.add(gameScene);
    }
}
