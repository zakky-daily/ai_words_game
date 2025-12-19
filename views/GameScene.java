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
    private ArrayList<Cards> cards, judge;

    private JPanel judgeArea;

    private JLabel showLabel;
    public GameScene(){
        this.setLayout(null);
        this.setBackground(Color.WHITE);//背景設定。画像を使うならここを改変

        cards = new ArrayList();
        judge = new ArrayList();

        judgeArea = new JPanel();//判定枠の作成
        judgeArea.setBounds(120, 256, 720, 128);
        judgeArea.setBackground(Color.DARK_GRAY);
        this.add(judgeArea);

        DragAdapter adp = new DragAdapter();//ドラッグ＆ドロップ機能クラス作成
        for(int i=0; i < 13; i++){//各カードに上記の機能付与。Controllerから与えられるcardsに付与するよう改変予定
            Cards c = new Cards("abcdefghijklmn"+i, 20+60*i,500);
            c.addMouseListener(adp);
            c.addMouseMotionListener(adp);
            cards.add(c);
        }
    }

    public String printList(ArrayList<Cards> a){
        String rets = "";
        for(int i=0; i < a.size(); i++){
            String s = a.get(i).info.getWord();
            rets +=" " + s;
        }
        return rets;
    }

    class Cards extends JLabel{
        public CardInfo info;
        public Point lastp, initp;
        public Cards(String word, int x, int y){
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
            Cards card = (Cards)e.getComponent();
            Rectangle cardRect = card.getBounds();
            Rectangle judgeRect = judgeArea.getBounds();

            if(cardRect.intersects(judgeRect)){
                card.lastp.y = 256;
                card.lastp.x = card.getX();
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
            showLabel.setText(printList(judge));
            card.setLocation(card.lastp);
            ClickPoint = null;
        }//手札は初期7枚+ランダム6枚の13枚

    }
}
