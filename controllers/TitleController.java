package controllers;
import views.MainFrame;

public class TitleController {
    
    public TitleController(MainFrame mainFrame) {

        mainFrame.startTitle();
        mainFrame.setVisible(true);
    }
}
