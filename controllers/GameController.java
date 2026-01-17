package controllers;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import lib.CardInfo;
import models.GameModel;
import views.CardView;
import views.GameScene;
import views.MainFrame;

//!!!Modelの関数がないうちは文章反映できません!!!

public class GameController extends MouseAdapter implements ActionListener{
private MainFrame mainFrame;//viewやmodelなどの関数呼び出し用
private Point clickPoint; //mousePresed時の座標記憶先
private final GameModel model;
private final int themeId;
private final String themeKey;

    public GameController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.model = new GameModel();
        mainFrame.startGame();
        mainFrame.setVisible(true);
        this.themeId = model.pickRandomThemeId();
        this.themeKey = model.getThemeKey(themeId);
        ArrayList<String> cards = model.buildCardSet(themeKey);
        this.mainFrame.gameScene.GetCards(cards, this);//thisがMouseAdapter
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
                card.info.lastp.y = card.lastp.y;
                mainFrame.gameScene.addCardtoJudge(card);//view側でArrayListに追加
            }else{
                card.lastp.x = card.initp.x;//元の位置に戻す。Modelでやりたい。
                card.lastp.y = card.initp.y;
                card.info.lastp.x = card.lastp.x;
                card.info.lastp.y = card.lastp.y;
                mainFrame.gameScene.removeCard(card);//view側でArrayListから削除
                card.setText(card.info.word);
            }
            
            updateJudgeSentence();//画面上の文章とカード表示を更新
            card.setLocation(card.lastp);//余裕があればViewに同機能の関数作ってそれを呼び出しに変更
            clickPoint = null;
        }//MouseAdapterの再定義完了

        @Override
        public void actionPerformed(ActionEvent e){//提出ボタン用
            if(e.getActionCommand().equals("提出")){
                mainFrame.gameScene.updateLabel(1, "test");//この中身は提出後の動作全般。処理は""絶対に""それぞれ別で関数に書くこと。ここでは呼び出しがメイン
            }
        }

    private void updateJudgeSentence() {
        ArrayList<CardView> judgeCards = mainFrame.gameScene.getJudgeCards();
        if (judgeCards.isEmpty()) {
            mainFrame.gameScene.updateLabel(GameScene.LABEL_ID, "");
            return;
        }

        ArrayList<CardInfo> infos = new ArrayList<>(judgeCards.size());
        for (CardView card : judgeCards) {
            infos.add(card.info);
        }

        ArrayList<String> trimmedWords = model.applyOverlapByPosition(infos, GameModel.CARD_WIDTH);
        List<CardView> sortedCards = new ArrayList<>(judgeCards);
        sortedCards.sort(Comparator.comparingInt(c -> c.info.lastp.x));
        for (int i = 0; i < sortedCards.size() && i < trimmedWords.size(); i++) {
            sortedCards.get(i).setText(trimmedWords.get(i));
        }

        StringBuilder sentence = new StringBuilder();
        for (String word : trimmedWords) {
            sentence.append(word);
        }
        mainFrame.gameScene.updateLabel(GameScene.LABEL_ID, sentence.toString());
    }
}
