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
    public TitleController(MainController mc) {
        this.model = new TitleModel();
        // TitleSceneを取得
        this.mainCtrl = mc;
        this.mainFrame = mc.mainFrame;
        TitleScene titleScene = mainFrame.startTitle();

        // TitleSceneにあるボタンにActionListenerを登録
        // startButton-----------------------------------
        titleScene.setStartButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                TitleModel.TitleAction action = model.getAction(e.getActionCommand());
                if(action == TitleModel.TitleAction.START){
                    System.out.println("はじめるボタンが押されました");
                    mainCtrl.startGame();
                    
                    /*mainFrame.getContentPane().removeAll();
                    mainFrame.startGame();
                    mainFrame.revalidate();
                    mainFrame.repaint();*/
                } else if(e.getActionCommand().equals("HOW_TO_PLAY")){
                    titleScene.showDialog(mainFrame);
                }
            }
        });
        
        // wayToPlayButton ------------------------------

        
        /*JButton wayToPlayBtn = null;
        // titleScene内にある部品を調べる
        for(Component comp : titleScene.getComponents()){
            // 見つけた要素のクラスがJButtonで、wayToPlayButtonなら取得する
            if(comp instanceof JButton && ((JButton) comp).getActionCommand().equals("HOW_TO_PLAY")){
                wayToPlayBtn = (JButton)comp;
                break;
            }
        }
        if(wayToPlayBtn != null){
            wayToPlayBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    showDialog(mainFrame);
                }
            });
        }*/
        mainFrame.setVisible(true);
    }
    /*
    // 「遊び方」を押した時に表示されるDialogを設定する関数
    private void showDialog(JFrame mainFrame){
        // manual.pngの読み込み
        URL manualURL = getClass().getResource("/Title/manual.png");
        if(manualURL != null){
            ImageIcon manualIcon = new ImageIcon(manualURL);
            // 必要に応じてリサイズ（例: 800x500）
            Image img = manualIcon.getImage().getScaledInstance(800, 500, Image.SCALE_SMOOTH);
            // 遊び方ダイアログとして画像を載せる
            JOptionPane.showMessageDialog(mainFrame, 
                new JLabel(new ImageIcon(img)), "遊び方",JOptionPane.PLAIN_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(mainFrame, "遊び方画像が見つかりません。");
        }
    }*/

}
