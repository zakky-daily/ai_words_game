package views;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class JudgeScene extends JPanel {
    private JLabel scoreLabel;//点数
    private JLabel commentLabel;//コメント
    private JButton goTitleButton;//タイトルに戻る

    public JudgeScene() {
        this.setLayout(null);

        ImageIcon icon = new ImageIcon("res/JudgeScene/judge.png");
        this.backgroundImage = icon.getImage();//背景画像追加

        scoreLabel = new JLabel();//作った文章表示の作成
        scoreLabel.setBounds(380, 120, 210, 160);
        scoreLabel.setFont(new Font("Serif", Font.BOLD, 75));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(scoreLabel);

        commentLabel = new JLabel();
        commentLabel.setBounds(230, 420, 510, 110);
        commentLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(commentLabel);


        goTitleButton = new JButton();
        goTitleButton.setBounds(700, 445, 260, 185);
        goTitleButton.setBackground(Color.BLUE);
        this.add(goTitleButton);

        setSize(960, 640);//サイズ

    }
}