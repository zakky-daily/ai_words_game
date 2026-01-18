package views;
import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        this.setSize(980, 700);//サイズ指定
        this.setLocationRelativeTo(null);//中央にウィンドウが出現
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//xボタンで終了
    }


    private void changeScene(){
        this.revalidate();
        this.repaint();
    }

    // 戻り値をTitleSceneに変更
    public TitleScene startTitle() {
        getContentPane().removeAll();
        TitleScene titleScene = new TitleScene();
        this.add(titleScene);
        changeScene();
        return titleScene;
    }

    public GameScene startGame() {//gamescene作って表示
        getContentPane().removeAll();
        GameScene gameScene = new GameScene();
        this.add(gameScene);
        changeScene();
        return gameScene;
    }

    public JudgeScene startJudge(){
        getContentPane().removeAll();
        JudgeScene judgeScene = new JudgeScene();
        this.add(judgeScene);
        changeScene();
        return judgeScene;
    }
}
