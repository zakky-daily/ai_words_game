package views;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import lib.CardInfo;

public class GameScene extends JPanel {
    //テーマ決定
    //カード獲得&Ready Start表示
    //タイマースタート
    //ゲームシーン
    //時間切れor提出ボタンで遷移
    //センター文字を表示するclass
    private ArrayList<CardView> cards, judge;

    private JPanel judgeArea;

    private JLabel showLabel;
    public GameScene(){
        this.setLayout(null);
        this.setBackground(Color.WHITE);//背景設定。画像を使うならここを改変

        cards = new ArrayList();//手札リスト
        judge = new ArrayList();//判定リスト

        judgeArea = new JPanel();//判定枠の作成
        judgeArea.setBounds(120, 256, 720, 60);
        judgeArea.setBackground(Color.DARK_GRAY);
        this.add(judgeArea);

        showLabel = new JLabel("中身");
        showLabel.setBounds(120, 120, 720, 64);
        this.add(showLabel);

        setSize(960, 640);
    }

    public void GetCards(ArrayList<String> a){//文字列のリストからカードリストを作成、表示する関数
        DragAdapter adp = new DragAdapter();//ドラッグ＆ドロップ機能クラス作成
        for(int i=0; i < 12; i++){
            CardView c = new CardView(a.get(i), 20+60*i, 500);
            c.addMouseListener(adp);
            c.addMouseMotionListener(adp);
            cards.add(c);
        }

        for(int i=0; i < 12; i++){
            this.add(cards.get(i));
        }
    }

    public String getList(ArrayList<CardView> a){//リスト内の文字列を全て繋げて返す
        String rets = "";
        for(int i=0; i < a.size(); i++){
            String s = a.get(i).info.getWord();
            rets += s;
        }
        return rets;
    }

    public

    class CardView extends JLabel{
        public CardInfo info;
        public Point lastp, initp;
        public CardView(String word, int x, int y){
            info = new CardInfo(word, x, y);

            this.lastp = new Point(x, y); 
            this.initp = new Point(x, y);
            setText(word);
            setOpaque(true);
            setBackground(Color.white);
            setHorizontalAlignment(CENTER);
            setBounds(x, y, 80, 60);
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
    }

    class DragAdapter extends MouseAdapter{
        private Point ClickPoint;

        @Override
        public void mousePressed(MouseEvent e) {
            ClickPoint = e.getPoint();
            Component c = e.getComponent();
            c.getParent().setComponentZOrder(c, 0);
            c.repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (ClickPoint == null) return;
            Component c = e.getComponent();
            
            int newX = c.getX() + (e.getX() - ClickPoint.x);
            int newY = c.getY() + (e.getY() - ClickPoint.y);
            c.setLocation(newX, newY);
        }

        @Override
        public void mouseReleased(MouseEvent e){
            CardView card = (CardView)e.getComponent();
            Rectangle cardRect = card.getBounds();
            Rectangle judgeRect = judgeArea.getBounds();

            if(cardRect.intersects(judgeRect)){
                card.lastp.y = 256;
                card.lastp.x = card.getX(); card.info.lastp.x = card.lastp.x;
                if(!judge.contains(card)){
                    judge.add(card);
                }
            }else{
                card.lastp.x = card.initp.x;
                card.lastp.y = card.initp.y;
                if(judge.contains(card)){
                    judge.remove(judge.indexOf(card));
                }
            }
            showLabel.setText(getList(judge));
            card.setLocation(card.lastp);
            ClickPoint = null;
        }//手札は初期6枚+ランダム6枚の12枚

    }
}
