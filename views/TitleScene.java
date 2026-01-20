package views;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

public class TitleScene extends JPanel {
    private JButton startButton;
    private JButton wayToPlayButton;
    private Image backgroundImage;
    public TitleScene() {
        
        this.setLayout(null);
        
        // ボタンの追加
        // スタートボタンを追加
        startButton = new JButton();
        startButton.setActionCommand("START");
        startButton.setBounds(380, 495, 225, 50);
        startButton.setContentAreaFilled(false); // 背景を透明にする
        startButton.setBorderPainted(false);// 枠線を透明にする
        startButton.setFocusPainted(false);//タップした時に出てくる枠線を消す
        this.add(startButton);
    

        // 遊び方ボタンを追加
        wayToPlayButton = new JButton();
        wayToPlayButton.setActionCommand("HOW_TO_PLAY");
        wayToPlayButton.setBounds(380, 575, 225, 50);
        wayToPlayButton.setContentAreaFilled(false); // 背景を透明にする
        wayToPlayButton.setBorderPainted(false);     // 枠線を透明にする
        wayToPlayButton.setFocusPainted(false);//タップした時に出てくる枠線を消す
        this.add(wayToPlayButton);


        // 背景画像を用意
        ImageIcon icon = new ImageIcon("res/Title/title.png");
        if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            backgroundImage = icon.getImage();
        } else {
            System.out.println("背景画像が見つかりません");
            this.setBackground(Color.WHITE);
        } 

        setSize(960, 640);//サイズ
    }

    @Override
    protected void paintComponent(Graphics g) {// 背景画像の大きさをウィンドウの大きさに揃える
        super.paintComponent(g);
        if (backgroundImage != null) {
            // 画面の幅(this.getWidth())と高さ(this.getHeight())に合わせて描画
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }

    public void showDialog(JFrame mainFrame){
        // manual.pngの読み込み
        URL manualURL = getClass().getResource("/res/Title/manual.png");
        ImageIcon manualIcon = new ImageIcon("res/Title/manual.png");

        if(manualIcon.getImageLoadStatus() == MediaTracker.COMPLETE){
            Image img = manualIcon.getImage().getScaledInstance(800, 500, Image.SCALE_SMOOTH);
            JOptionPane.showMessageDialog(mainFrame, 
                new JLabel(new ImageIcon(img)), "遊び方",JOptionPane.PLAIN_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(mainFrame, "遊び方画像が見つかりません。");
        }
    }
    

    public void setStartButtonListener(ActionListener listener){
        startButton.addActionListener(listener);
        wayToPlayButton.addActionListener(listener);
    }

}
