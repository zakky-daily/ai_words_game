package views;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class JudgeScene extends JPanel {
    private JLabel scoreLabel; public static final int SCORE_ID = 1;//点数
    private JLabel commentLabel; public static final int COMMENT_ID = 2;//コメント
    private JButton goTitleButton;//タイトルに戻る
    private JButton xButton;//通信後に表示するボタン
    private JLabel loadingLabel;//共有処理中の表示
    private Image backgroundImage;

    public JudgeScene() {
        this.setLayout(null);

        ImageIcon icon = new ImageIcon("res/JudgeScene/judge.png");
        this.backgroundImage = icon.getImage();//背景画像追加

        scoreLabel = new JLabel();//作った文章表示の作成
        scoreLabel.setBounds(380, 130, 210, 160);
        scoreLabel.setFont(new Font("Serif", Font.BOLD, 160));
        scoreLabel.setForeground(Color.decode("#FF7B28"));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(scoreLabel);

        commentLabel = new JLabel("判定中");
        commentLabel.setBounds(230, 420, 510, 110);
        commentLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        commentLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(commentLabel);


        goTitleButton = new JButton();
        goTitleButton.setActionCommand("END");
        goTitleButton.setBounds(370, 583, 240, 40);
        goTitleButton.setContentAreaFilled(false); // 背景を透明にする
        goTitleButton.setBorderPainted(false);     // 枠線を透明にする
        this.add(goTitleButton);

        ImageIcon rawXIcon = new ImageIcon("res/JudgeScene/x.png");
        int xWidth = rawXIcon.getIconWidth() / 4;
        int xHeight = rawXIcon.getIconHeight() / 4;
        ImageIcon xIcon = new ImageIcon(rawXIcon.getImage().getScaledInstance(xWidth, xHeight, Image.SCALE_SMOOTH));
        xButton = new JButton(xIcon);
        xButton.setActionCommand("SHARE");
        xButton.setBounds(960 - xWidth - 20, 20, xWidth, xHeight);
        xButton.setContentAreaFilled(false);
        xButton.setBorderPainted(false);
        xButton.setFocusPainted(false);
        xButton.setVisible(false);
        this.add(xButton);

        loadingLabel = new JLabel("Loading...") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(0, 0, 0, 180));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        loadingLabel.setBounds(0, 0, 960, 640);
        loadingLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
        loadingLabel.setForeground(Color.WHITE);
        loadingLabel.setOpaque(false);
        loadingLabel.setHorizontalAlignment(JLabel.CENTER);
        loadingLabel.setVerticalAlignment(JLabel.CENTER);
        loadingLabel.setVisible(false);
        this.add(loadingLabel);

        setSize(960, 640);//サイズ

    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(backgroundImage != null){
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }

        public void updateLabel(int id, String t){//指定したIDのJLabelの文字を変更する。タイマーと作れた文章用
        switch (id) {
            case SCORE_ID://1
                scoreLabel.setText(t); //吹き出しの中を複数行に対応させた
                break;
            case COMMENT_ID://2
                String displayText = "<html><div style='text-align: center; width: 360px;'>" + t + "</div></html>";
                commentLabel.setText(displayText);
                break;
            default:
                throw new AssertionError();
        }
    }

    public void setEndButtonListener(ActionListener listener){
        goTitleButton.addActionListener(listener);
        xButton.addActionListener(listener);
    }

    public void showXButton(){
        xButton.setVisible(true);
    }

    public void showLoading(){
        loadingLabel.setBounds(0, 0, getWidth(), getHeight());
        loadingLabel.setVisible(true);
        setComponentZOrder(loadingLabel, 0);
        repaint();
    }

    public void hideLoading(){
        loadingLabel.setVisible(false);
    }
}
