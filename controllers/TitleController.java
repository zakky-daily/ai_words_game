package controllers;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.*;
import views.MainFrame;
import views.TitleScene;


public class TitleController{
    
    public TitleController(MainFrame mainFrame) {
        // TitleSceneを取得
        TitleScene titleScene = mainFrame.startTitle();

        // TitleSceneにあるボタンにActionListenerを登録
        // startButton-----------------------------------
        titleScene.setStartButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("はじめるボタンが押されました");

                mainFrame.getContentPane().removeAll();

                mainFrame.startGame();

                mainFrame.revalidate();
                mainFrame.repaint();

            }
        });

        // wayToPlayButton ------------------------------
        JButton wayToPlayBtn = null;
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
        }
        mainFrame.setVisible(true);
    }

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
    }
}
