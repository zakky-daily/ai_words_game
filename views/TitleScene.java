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

        this.setSize(980, 700);
        
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        
        // ボタンの追加
        Font buttonFont = new Font("SansSerif", Font.BOLD, 30);
        URL imageStart = getClass().getResource("/views/title.png");
        URL imageToPlay = getClass().getResource("/views/title.png");
        ImageIcon iconStart = new ImageIcon(imageStart);
        ImageIcon iconToPlay = new ImageIcon(imageToPlay);
        // 元の画像サイズが大きすぎる場合、ここでリサイズ
        Image imgSrtart = iconStart.getImage().getScaledInstance(230, 80, Image.SCALE_SMOOTH);
        Image imgToPlay = iconToPlay.getImage().getScaledInstance(230, 80, Image.SCALE_SMOOTH);

        // スタートボタンを追加
        startButton = new JButton();
        startButton.setBounds(375, 420, 230, 80);
        startButton.setBackground(new Color(40, 40, 80)); // 濃紺
        startButton.setForeground(Color.BLACK);
        startButton.setIcon(iconStart);
        startButton.setFocusPainted(false);
        this.add(startButton);
        

        // 遊び方ボタンを追加
        wayToPlayButton = new JButton("遊び方");
        wayToPlayButton.setBounds(375, 540, 230, 80);
        wayToPlayButton.setForeground(Color.BLACK);
        wayToPlayButton.setFocusPainted(false);
        startButton.setIcon(iconToPlay);
        this.add(wayToPlayButton);

        // 背景画像を用意
        URL image = getClass().getResource("/views/title.png");
        if (image != null) {
            ImageIcon icon = new ImageIcon(image);
            // 元の画像サイズが大きすぎる場合、ここでリサイズ
            Image img = icon.getImage().getScaledInstance(980, 700, Image.SCALE_SMOOTH);
            titleLabel = new JLabel(new ImageIcon(img));
        } else {
            System.out.println("探した場所: " + getClass().getPackage().getName());
            titleLabel = new JLabel("画像が見つかりません");
        }   
        titleLabel.setBounds(0, 0, 980, 700);
        this.add(titleLabel);


    }
}
