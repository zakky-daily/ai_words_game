package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import models.TitleModel;
import views.MainFrame;
import views.TitleScene;


public class TitleController{
    private final TitleModel model;
    private MainController mainCtrl;
    private MainFrame mainFrame;
    private TitleScene titleScene;

    public TitleController(MainController mc) {
        this.model = new TitleModel();
        // TitleSceneを取得
        this.mainCtrl = mc;
        this.mainFrame = mc.mainFrame;
        this.titleScene = mainFrame.startTitle();

        // TitleSceneにあるボタンにActionListenerを登録
        // startButton-----------------------------------
        titleScene.setStartButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(e.getActionCommand().equals("START")){
                    mainCtrl.startGame();
                } else if(e.getActionCommand().equals("HOW_TO_PLAY")){
                    openHowToDialog();
                }
            }
        });
        mainFrame.setVisible(true);
    }

    private void openHowToDialog(){// 遊び方ダイアログを開く処理
        model.resetIndex();
        ActionListener prevAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.prevPage();// model更新
                titleScene.updateView(
                    model.getCurrentPage(),
                    model.isFirstPage(),
                    model.isLastPage()
                );
            }
        };
        ActionListener nextAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.nextPage();// model更新
                titleScene.updateView(
                    model.getCurrentPage(),
                    model.isFirstPage(),
                    model.isLastPage()
                );
            }
        };

        titleScene.showDialog(
            mainFrame, 
            prevAction, 
            nextAction, 
            model.getCurrentPage(), 
            model.isFirstPage(), 
            model.isLastPage()
        );
    }

}