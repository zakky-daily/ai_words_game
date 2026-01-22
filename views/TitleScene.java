package views;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TitleScene extends JPanel {
    private JButton startButton;
    private JButton wayToPlayButton;
    private Image backgroundImage;
    private JLabel imageLabel;
    private JButton prevButton;
    private JButton nextButton;

    public TitleScene() {
        
        this.setLayout(null);
        
        // ボタンの追加
        // スタートボタンを追加
        startButton = new JButton();
        startButton.setActionCommand("START");
        startButton.setBounds(390, 485, 215, 55);
        startButton.setContentAreaFilled(false); // 背景を透明にする
        startButton.setBorderPainted(false);// 枠線を透明にする
        startButton.setFocusPainted(false);//ボタンとしての枠を消す
        this.add(startButton);
    

        // 遊び方ボタンを追加
        wayToPlayButton = new JButton();
        wayToPlayButton.setActionCommand("HOW_TO_PLAY");
        wayToPlayButton.setBounds(390, 570, 215, 55);
        wayToPlayButton.setContentAreaFilled(false); // 背景を透明にする
        wayToPlayButton.setBorderPainted(false);// 枠線を透明にする
        wayToPlayButton.setFocusPainted(false);//ボタンとしての枠を消す
        
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

    

    public void showDialog(JFrame mainFrame, ActionListener prevAction, ActionListener nextAction, ImageIcon initialImage, boolean isFirst, boolean isLast) {
        // 全体のパネル
        JPanel panel = new JPanel(new BorderLayout());
        
        this.imageLabel = new JLabel(initialImage);// 画像を表示するラベル
        this.imageLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(this.imageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));// ボタンを表示するパネル
        
        this.prevButton = new JButton("◀");// 「◀ 前へ」ボタン
        this.prevButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        this.prevButton.addActionListener(prevAction);
        this.prevButton.setEnabled(false); // 最初のページなら無効化

        this.nextButton = new JButton("▶");// 「次へ ▶」ボタン
        this.nextButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        this.nextButton.addActionListener(nextAction);
        this.nextButton.setEnabled(!isLast);//最後のページなら無効化
        
        buttonPanel.add(this.prevButton);
        buttonPanel.add(this.nextButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        JOptionPane.showMessageDialog(
            mainFrame, 
            panel, 
            "遊び方", 
            JOptionPane.PLAIN_MESSAGE
        );
    }

    public void updateView(ImageIcon nextImage, boolean isFirst, boolean isLast){
        this.imageLabel.setIcon(nextImage);
        this.prevButton.setEnabled(!isFirst);
        this.nextButton.setEnabled(!isLast);
    }


    @Override
    protected void paintComponent(Graphics g) {// 背景画像の大きさをウィンドウの大きさに揃える
        super.paintComponent(g);
        if (backgroundImage != null) {
            // 画面の幅(this.getWidth())と高さ(this.getHeight())に合わせて描画
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }

    public void setStartButtonListener(ActionListener listener){
        startButton.addActionListener(listener);
        wayToPlayButton.addActionListener(listener);
    }

}
