package lib;
import java.awt.*;

public class CardInfo{
    private String word;
    public Point lastp, initp;
    public CardInfo(String word, int x, int y){
        this.word = word;

        this.lastp = new Point(x, y); 
        this.initp = new Point(x, y);
    }

    public String getWord(){
        return this.word;
    }

    public Integer getx(){
        return this.lastp.x;
    }
}
