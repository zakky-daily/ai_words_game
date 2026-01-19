package views;
import controllers.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

//!!!CardViewクラスはファイル分けました。viewsディレクトリにあります!!!

public class GameScene extends JPanel {
    //テーマ決定
    //カード獲得&Ready Start表示
    //タイマースタート＆ゲーム開始
    //時間切れor提出ボタンで遷移
    //↑は時間処理はControllerで、それ以外はModelでお願い
    private ArrayList<CardView> cards, judge;//手札、判定カードの表示用リスト

    private JPanel judgeArea; //判定枠
    private JPanel initArea; //カードの初期位置

    private JLabel scoreLabel;
    private JLabel showLabel; public static final int LABEL_ID = 1;//updateLabel用
    private JLabel showtimer; public static final int TIMER_ID = 2;
    private JLabel themeLabel;//テーマ

    private JButton submitButton;//提出ボタン
    private Image backgroundImage; //背景画像

    public GameScene(){
        this.setLayout(null);

        cards = new ArrayList<CardView>();//手札リスト
        judge = new ArrayList<CardView>();//判定リスト

        judgeArea = new JPanel();//判定枠の作成
        judgeArea.setBounds(20, 285, 780, 100);//配置はここの座標を変更
        judgeArea.setBackground(Color.LIGHT_GRAY);
        this.add(judgeArea);

        initArea = new JPanel();//カードが最初に置かれているパネルの作成
        initArea.setBounds(20, 420, 920, 190);//配置はここの座標を変更
        initArea.setBackground(Color.DARK_GRAY);
        this.add(initArea);


        showLabel = new JLabel("中身");//作った文章表示の作成
        showLabel.setBounds(290, 60, 430, 80);//配置はここの座標を変更
        showLabel.setFont(new Font("Serif", Font.BOLD, 24));
        showLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(showLabel);


        themeLabel = new JLabel("テーマ：");//テーマ表示する用
        themeLabel.setBounds(330, 180, 340, 50);
        themeLabel.setOpaque(true); // 背景色を有効に
        themeLabel.setBackground(Color.WHITE);
        themeLabel.setFont(new Font("SansSerif", Font.BOLD, 35));
        themeLabel.setHorizontalAlignment(JLabel.CENTER); // 真ん中寄せ
        this.add(themeLabel);

        submitButton = new JButton(" ▶︎提出");//提出ボタンの作成
        submitButton.setActionCommand("Submit");
        submitButton.setBounds(780, 285, 160, 100);//配置はここの座標を変更
        submitButton.setBackground(Color.decode("#2F6EBA"));// 背景色を黄色に設定
        submitButton.setForeground(Color.WHITE);// 文字の色を白色に設定
        submitButton.setOpaque(true); // ボタンを不透明にして背景色を表示させる
        submitButton.setBorderPainted(false); // 枠線を消す
        submitButton.setFont(new Font("Serif", Font.BOLD, 30));
        this.add(submitButton);

        showtimer = new JLabel("60");
        showtimer.setBounds(75,110, 90, 75);
        showtimer.setFont(new Font("Arial", Font.BOLD, 60));
        this.add(showtimer);
        
        //timer表示用の関数作ってくれるとすｇｇｇｇっごく助かる。
        

        setSize(960, 640);//サイズ
    }


    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(backgroundImage != null){
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }

    public void setTheme(String themeId){//テーマ名を受け取って背景を切り替える
        String filename = "god.png";
        String themeText = "???";
        switch (themeId) {
            case "oracle":
                filename = "god.png";
                themeText = "神託";
                break;
            case "propose":
                filename = "propose.png";
                themeText = "プロポーズ";
                break;
            case "begging":
                filename = "life.png";
                themeText = "命乞い";
                break;
        }
        ImageIcon icon = new ImageIcon("res/GameScene/" + filename);
        if(icon.getImageLoadStatus() == MediaTracker.COMPLETE){//テーマによって背景変更
            this.backgroundImage = icon.getImage();
        }
        if(themeLabel != null){//テーマによって文字変更
            themeLabel.setText("テーマ：" + themeText);
        }
        this.repaint();
    }

    public void GenerateCards(ArrayList<String> a, GameController controller){//文字列のリストからカードリストを作成、表示する関数
        for(int i=0; i < 12; i++){
            CardView c = new CardView(a.get(i), 40+147*(i%6), 435+90*(i/6));//カードの配置はここの座標を変更
            c.addMouseListener(controller);//GameControllerで用意したListenerをここで適用
            c.addMouseMotionListener(controller);
            cards.add(c);//リストに追加
            this.add(c);//パネルに追加
            this.setComponentZOrder(c, 0);//カードを最前面に追加
        }
        this.repaint();//一応再描画。多分いらん
    }

    public Rectangle getJudgeAreaBounds(){//判定枠を返す。Controllerでの判定用
        return judgeArea.getBounds();
    }

    public void addCardtoJudge(CardView card){//CardViewのArrayListであるjudgeにカードが入っているかで判定。
        if(!judge.contains(card)){            //下の関数含め、if分岐ははModelでやるべき
            judge.add(card);
        }
    }

    public void removeCard(CardView card){//下に同じく
        if(judge.contains(card)){
            judge.remove(card);
        }
    }

    public ArrayList<CardView> getJudgeCards(){//Controller側で文章生成に使う
        return new ArrayList<>(judge);
    }

    public void addSubmitListener(ActionListener listener){//ボタンにlistenerを付与
        submitButton.addActionListener(listener);//GameControllerで用意したListenerをここで適用
    }
    public void updateLabel(int id, String t){//指定したIDのJLabelの文字を変更する。タイマーと作れた文章用
        switch (id) {
            case LABEL_ID://1
                String displayText = "<html><div style='text-align: center; width: 360px;'>" + t + "</div></html>";
                showLabel.setText(displayText); //吹き出しの中を複数行に対応させた
                showLabel.repaint();
                break;
            case TIMER_ID://2
                showtimer.setText(t);
                break;
            default:
                throw new AssertionError();
        }
    }

    public String getList(ArrayList<CardView> a){//リスト内の文字列を全て繋げて返す。抹消予定。整列含め絶対Model上のCardInfoでやったほうがいい。
        String rets = "";
        for(int i=0; i < a.size(); i++){
            String s = a.get(i).info.getWord();
            rets += s;
        }
        return rets;
    }
}
