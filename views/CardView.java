package views;
import java.awt.*;
import javax.swing.*;
import models.CardInfo;

    public class CardView extends JLabel{
        public CardInfo info;
        public Point lastp, initp;
        public CardView(String word, int x, int y){
            info = new CardInfo(word, x, y);

            this.lastp = new Point(x, y); 
            this.initp = new Point(x, y);
            setText(word);

            this.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
            setOpaque(true);
            setBackground(Color.white);
            setHorizontalAlignment(CENTER);
            setBounds(x, y, 135, 65);
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
    }