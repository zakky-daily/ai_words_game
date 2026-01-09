package controllers;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import lib.CardInfo;
import views.*;

//!!!Modelの関数がないうちは文章反映できません!!!

public class GameController extends MouseAdapter implements ActionListener{
private MainFrame mainFrame;//viewやmodelなどの関数呼び出し用
private Point clickPoint; //mousePresed時の座標記憶先

    public GameController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        mainFrame.startGame();
        mainFrame.setVisible(true);
        ArrayList<String> l = new ArrayList<>();//GetCards検証用
        for (int i = 0; i < 12; i++) {
            l.add(i + "");            
        }
        this.mainFrame.gameScene.GetCards(l, this);//thisがMouseAdapter
        this.mainFrame.gameScene.addSubmitListener(this);//提出ボタンにListenerを付与
    }
    
    @Override   //MouseAdapterの再定義開始
        public void mousePressed(MouseEvent e) { 
            clickPoint = e.getPoint(); //押した時の座標記憶
            Component c = e.getComponent(); //MouseAdapter持ちのインスタンス、要するにクリックしたカードをcに記憶
            c.getParent().setComponentZOrder(c, 0); //カードを最前面に
            c.repaint();//余裕があればViewに同機能の関数作ってそれを呼び出しに変更
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (clickPoint == null) return; //クリックしてなかったらなにもしない
            Component c = e.getComponent(); //カードを記憶
            
            int newX = c.getX() + (e.getX() - clickPoint.x); //カードの座標から、新しい座標を計算(ここの計算も正直Modelに置きたい)
            int newY = c.getY() + (e.getY() - clickPoint.y);
            c.setLocation(newX, newY);
        }

        @Override
        public void mouseReleased(MouseEvent e){
            CardView card = (CardView)e.getComponent(); //クラス内の変数を更新するため、CardViewクラスに記憶

            //カードが枠内にあるかの判定をするため、四角形のクラスでカード、判定枠を記憶
            Rectangle cardRect = card.getBounds();
            Rectangle judgeRect = mainFrame.gameScene.getJudgeAreaBounds();

            if(cardRect.intersects(judgeRect)){ //判定枠と重なっていた場合
                card.lastp.y = 256; //y座標固定
                card.lastp.x = card.getX(); card.info.lastp.x = card.lastp.x; //x座標をReleased時の値に更新
                mainFrame.gameScene.addCardtoJudge(card);//view側でArrayListに追加
            }else{
                card.lastp.x = card.initp.x;//元の位置に戻す。Modelでやりたい。
                card.lastp.y = card.initp.y;
                mainFrame.gameScene.removeCard(card);//view側でArrayListから削除
            }
            
            mainFrame.gameScene.updateLabel(1, "t");//画面上の文章を更新。いまはどんな動作をしてもtが表示される。"t"を更新した文字列の入った変数に変更。
            card.setLocation(card.lastp);//余裕があればViewに同機能の関数作ってそれを呼び出しに変更
            clickPoint = null;
        }//MouseAdapterの再定義完了

        @Override
        public void actionPerformed(ActionEvent e){//提出ボタン用
            if(e.getActionCommand().equals("提出")){
                mainFrame.gameScene.updateLabel(1, "test");//この中身は提出後の動作全般。処理は""絶対に""それぞれ別で関数に書くこと。ここでは呼び出しがメイン
            }
        }


    public String GenerateSentence(ArrayList<CardInfo> cards) {
        cards.sort(Comparator.comparingInt(c -> c.lastp.x));
        String res = "";
        for (CardInfo c : cards) res += c.word;
        return res;
    }
}
