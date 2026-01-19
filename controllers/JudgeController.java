package controllers;
import java.awt.event.*;
import java.util.*;
import views.*;

public class JudgeController{
    private MainController mainCtrl;
    private JudgeScene view;
    private MainFrame mainFrame;
    public JudgeController(MainController mc){
        this.mainCtrl = mc;
        this.mainFrame = mc.mainFrame;
        this.view = mainFrame.startJudge();
        view.setEndButtonListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(e.getActionCommand().equals("END")){
                    mainCtrl.startTitle();
                }
            }
        });
    }
    
}