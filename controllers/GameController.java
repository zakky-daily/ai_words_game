package controllers;
import java.util.ArrayList;
import java.util.Comparator;

import lib.CardInfo;
import views.MainFrame;

public class GameController {
    
    public GameController(MainFrame mainFrame) {
        mainFrame.startGame();
        mainFrame.setVisible(true);
    }

    public String GenerateSentence(ArrayList<CardInfo> cards) {
        cards.sort(Comparator.comparingInt(c -> c.lastp.x));
        String res = "";
        for (CardInfo c : cards) res += c.word;
        return res;
    }
}
