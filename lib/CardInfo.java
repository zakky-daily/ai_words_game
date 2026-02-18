package lib;
import java.awt.*;

public class CardInfo{
    public String word;
    public Point lastp;
    public CardInfo(String word, int x, int y){
        this.word = word;
        this.lastp = new Point(x, y); 
    }
}
