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

    private JLabel showLabel; public static final int LABEL_ID = 1;//updateLabel用
    private JLabel showtimer; public static final int TIMER_ID = 2;

    private JButton submitButton;//提出ボタン
    public GameScene(){
        this.setLayout(null);
        this.setBackground(Color.WHITE);//背景設定。画像を使うならここを改変

        cards = new ArrayList<CardView>();//手札リスト
        judge = new ArrayList<CardView>();//判定リスト

        judgeArea = new JPanel();//判定枠の作成
        judgeArea.setBounds(120, 256, 720, 60);//配置はここの座標を変更
        judgeArea.setBackground(Color.DARK_GRAY);
        this.add(judgeArea);

        showLabel = new JLabel("中身");//作った文章表示の作成
        showLabel.setBounds(120, 120, 720, 64);//配置はここの座標を変更
        this.add(showLabel);

        submitButton = new JButton("提出");//提出ボタンの作成
        submitButton.setBounds(850, 256, 80, 60);//配置はここの座標を変更
        this.add(submitButton);

        //timer表示用の関数作ってくれるとすｇｇｇｇっごく助かる。

        setSize(960, 640);//サイズ
    }

    public void GetCards(ArrayList<String> a, GameController controller){//文字列のリストからカードリストを作成、表示する関数
        for(int i=0; i < 12; i++){
            CardView c = new CardView(a.get(i), 20+60*i, 500);//カードの配置はここの座標を変更
            c.addMouseListener(controller);//GameControllerで用意したListenerをここで適用
            c.addMouseMotionListener(controller);
            cards.add(c);//リストに追加
            this.add(c);//パネルに追加
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

    public void addSubmitListener(ActionListener listener){//ボタンにlistenerを付与
        submitButton.addActionListener(listener);//GameControllerで用意したListenerをここで適用
    }
    public void updateLabel(int id, String t){//指定したIDのJLabelの文字を変更する。タイマーと作れた文章用
        switch (id) {
            case LABEL_ID://1
                showLabel.setText(t);
                break;
            case TIMER_ID://2
                showtimer.setText(t);
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
