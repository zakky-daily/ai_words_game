package views;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class JudgeScene extends JPanel {
    private JLabel scoreLabel;//点数
    private JLabel commentLabel;//コメント
    private JButton goTitleButton;//タイトルに戻る
    private Image backgroundImage;

    public JudgeScene() {
        this.setLayout(null);

        ImageIcon icon = new ImageIcon("res/JudgeScene/judge.png");
        this.backgroundImage = icon.getImage();//背景画像追加

        scoreLabel = new JLabel();
        scoreLabel.setBounds(380, 120, 210, 160);
        scoreLabel.setFont(new Font("Serif", Font.BOLD, 75));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(scoreLabel);

        commentLabel = new JLabel();
        commentLabel.setBounds(230, 420, 510, 110);
        commentLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(commentLabel);


        goTitleButton = new JButton();
        goTitleButton.setActionCommand("END");
        goTitleButton.setBounds(370, 583, 240, 40);
        goTitleButton.setContentAreaFilled(false); // 背景を透明にする
        goTitleButton.setBorderPainted(false);     // 枠線を透明にする
        this.add(goTitleButton);

        setSize(960, 640);//サイズ

    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(backgroundImage != null){
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }

    public void setEndButtonListener(ActionListener listener){
        goTitleButton.addActionListener(listener);
    }
}