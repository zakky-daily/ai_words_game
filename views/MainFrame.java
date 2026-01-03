package views;
import java.util.ArrayList;
import javax.swing.*;

public class MainFrame extends JFrame {
    
    public MainFrame() {
        this.setSize(980, 700);//サイズ指定
        this.setLocationRelativeTo(null);//中央にウィンドウが出現
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//xボタンで終了
    }

    public void startGame() {
        GameScene gamescene = new GameScene();
        this.add(gamescene);
        ArrayList<String> l = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            l.add(i + "");            
        }
        gamescene.GetCards(l);
    }
}
