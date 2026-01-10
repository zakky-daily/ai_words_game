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
        // スタートボタンを追加
        URL imageStart = getClass().getResource("/views/start.png");
        if (imageStart != null) {
            ImageIcon iconStart = new ImageIcon(imageStart);
            // ボタンのサイズ(230, 80)に合わせてリサイズ
            Image imgStart = iconStart.getImage().getScaledInstance(230, 80, Image.SCALE_SMOOTH);
            startButton = new JButton(new ImageIcon(imgStart));
        } else {
            startButton = new JButton("START");
        }

        startButton.setBounds(375, 420, 230, 80);
        // ボタンの装飾を消して画像だけ見せる設定
        startButton.setContentAreaFilled(false); // 背景を透明に
        startButton.setBorderPainted(false);     // 枠線を消す
        startButton.setFocusPainted(false);      // クリック時の枠を消す
        
        this.add(startButton);
    

        // 遊び方ボタンを追加
        URL imageToPlay = getClass().getResource("/res/howtoplay.png");
        if (imageToPlay != null) {
            ImageIcon iconToPlay = new ImageIcon(imageToPlay);
            // 同じく (230, 80) にリサイズ
            Image imgResized = iconToPlay.getImage().getScaledInstance(230, 80, Image.SCALE_SMOOTH);
            wayToPlayButton = new JButton(new ImageIcon(imgResized));
        } else {
            wayToPlayButton = new JButton("HOW TO PLAY");
        }

        wayToPlayButton.setBounds(375, 540, 230, 80);
        wayToPlayButton.setContentAreaFilled(false);
        wayToPlayButton.setBorderPainted(false);
        wayToPlayButton.setFocusPainted(false);
        
        this.add(wayToPlayButton);

        // 背景画像を用意
        URL image = getClass().getResource("/res/title.png");
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
