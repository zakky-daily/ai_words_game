package controllers;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.Timer;
import javax.imageio.ImageIO;
import java.util.TreeMap;

import models.GameModel;
import views.CardView;
import views.GameScene;
import views.MainFrame;

public class GameController extends MouseAdapter implements ActionListener{
    private MainController mainCtrl;
    private GameScene view;
    private MainFrame mainFrame;        //viewやmodelなどの関数呼び出し用
    private Point clickPoint;           //mousePresed時の座標記憶先
    private Timer gameTimer;
    private int remainingTime = 60;     //制限時間は60秒
    private final GameModel model;
    private final int themeId;
    private final String themeKey;
    private String createdText = "";

    public GameController(MainController mc) {
        this.mainCtrl = mc;
        this.mainFrame = mc.mainFrame;
        this.model = new GameModel();
        this.view = mainFrame.startGame();
        this.themeId = model.pickRandomThemeId();
        this.themeKey = model.getThemeKey(themeId);
        view.setTheme(this.themeKey);
        ArrayList<String> cards = model.buildCardSet(themeKey);
        view.GenerateCards(cards, this);    //thisがMouseAdapter
        view.addSubmitListener(this);       //提出ボタンにListenerを付与
        startTimer();
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
        Rectangle judgeRect = view.getJudgeAreaBounds();

        if(cardRect.intersects(judgeRect)){ //判定枠と重なっていた場合
            card.lastp.y = 275; //y座標固定
            card.lastp.x = card.getX(); card.info.lastp.x = card.lastp.x; //x座標をReleased時の値に更新
            card.info.lastp.y = card.lastp.y;
            view.addCardtoJudge(card);//view側でArrayListに追加
        }else{
            card.lastp.x = card.initp.x;//元の位置に戻す。Modelでやりたい。
            card.lastp.y = card.initp.y;
            view.removeCard(card);//view側でArrayListから削除
        }
        
        updateJudgeSentence();//画面上の文章とカード表示を更新
        card.setLocation(card.lastp);//余裕があればViewに同機能の関数作ってそれを呼び出しに変更
        clickPoint = null;
    }//MouseAdapterの再定義完了

    @Override
    public void actionPerformed(ActionEvent e){//提出ボタン用
        if(e.getActionCommand().equals("Submit")){
            // 時間が0になった時の処理
            if(gameTimer != null){
                gameTimer.stop();
            }

            captureGameScene();
            mainCtrl.startJudge(themeKey, this.createdText);
            //view.updateLabel(1, "test");//この中身は提出後の動作全般。処理は""絶対に""それぞれ別で関数に書くこと。ここでは呼び出しがメイン
        }
    }

    private void updateJudgeSentence() {
        TreeMap<Integer,Character> charList = new TreeMap<>();
        for (var cards : view.getJudgeCards()) {
            charList.subMap(cards.lastp.x-67, cards.lastp.x+67).clear();
            for (int i = 0; i < cards.info.word.length(); i++) {
                int x = Math.round(cards.lastp.x + (i - (cards.info.word.length()-1) / 2.0f) * 15);  // 文字毎の座標を推定
                charList.put(x, cards.info.word.charAt(i));
            }
        }
        StringBuilder sb = new StringBuilder();
        for (var ch : charList.values()) sb.append(ch);
        this.createdText = sb.toString();
        view.updateLabel(GameScene.LABEL_ID, this.createdText);
    }

    private void startTimer(){
        gameTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                remainingTime--;

                view.updateLabel(GameScene.TIMER_ID, String.valueOf(remainingTime));

                if(remainingTime <= 0){
                    gameTimer.stop();
                    captureGameScene();
                    mainCtrl.startJudge(themeKey, GameController.this.createdText);
                }
            }
        });
        gameTimer.start();
    }

    private void captureGameScene() {
        BufferedImage image = new BufferedImage(view.getWidth(), view.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        view.paintAll(g);
        g.dispose();
        try {
            ImageIO.write(image, "png", new File("res/game_scene.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
