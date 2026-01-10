package views;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.net.URL;

public class TitleScene extends JPanel {
    private JLabel titleLabel;
    private JButton startButton;
    private JButton wayToPlayButton;
    public TitleScene() {

        this.setSize(640, 680);
        
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        
        // 背景画像を用意
        URL image = getClass().getResource("/views/title.png");
        if (image != null) {
            ImageIcon icon = new ImageIcon(image);
            // 元の画像サイズが大きすぎる場合、ここでリサイズ
            Image img = icon.getImage().getScaledInstance(640, 300, Image.SCALE_SMOOTH);
            titleLabel = new JLabel(new ImageIcon(img));
        } else {
            titleLabel = new JLabel("画像が見つかりません");
        }   
        titleLabel.setBounds(0, 50, 640, 300);
        this.add(titleLabel);

        startButton = new JButton("はじめる");
        startButton.setBounds(350, 400, 230, 80);
        startButton.setBackground(Color.BLUE);
        this.add(startButton);

        wayToPlayButton = new JButton("遊び方");
        wayToPlayButton.setBounds(350, 500, 230, 80);
        wayToPlayButton.setBackground(Color.BLUE);
        this.add(wayToPlayButton);
    }
}
