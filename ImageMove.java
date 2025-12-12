import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class ImageMove extends JFrame{
    private ArrayList<Cards> cards, judge;

    private JPanel judgeArea;

    private JLabel showLabel;
    public ImageMove(){
        setTitle("オブジェクトドラッグ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        cards = new ArrayList();
        judge = new ArrayList<>();

        JPanel p = new JPanel();
        judgeArea = new JPanel();
        judgeArea.setBounds(120, 256, 720, 128);
        judgeArea.setBackground(Color.DARK_GRAY);
        this.add(judgeArea);
        //String d[] = {"テスト","てすと","test"};
        DragAdapter adp = new DragAdapter();
        for(int i=0; i < 13; i++){
            Cards c = new Cards("abcdefghijklmn"+i, 20+60*i,500);
            c.addMouseListener(adp);
            c.addMouseMotionListener(adp);
            cards.add(c);
        }

        for(int i=0; i < 13; i++){
            this.add(cards.get(i));
        }

        showLabel = new JLabel("中身");
        showLabel.setBounds(120, 120, 720, 64);
        this.add(showLabel);
        
        setSize(960, 640);
        setLocationRelativeTo(null);
    }

    public String printList(ArrayList<Cards> a){
        String rets = "";
        for(int i=0; i < a.size(); i++){
            String s = a.get(i).word;
            rets +=" " + s;
        }
        return rets;
    }

    class Cards extends JLabel{
        private String word;
        public Point lastp, initp;
        public Cards(String word, int x, int y){
            this.word = word;

            this.lastp = new Point(x, y); 
            this.initp = new Point(x, y);
            setText(word);
            setOpaque(true);
            setBackground(Color.white);
            setHorizontalAlignment(CENTER);
            setBounds(x, y, 80, 60);
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }

        public String getWord(){
            return this.word;
        }

        public Integer getx(){
            return this.lastp.x;
        }
    }//4:3 960x640

    class DragAdapter extends MouseAdapter{
        private Point ClickPoint;

        @Override
        public void mousePressed(MouseEvent e) {
            ClickPoint = e.getPoint();
            Component c = e.getComponent();
            c.getParent().setComponentZOrder(c, 0);
            c.repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (ClickPoint == null) return;
            Component c = e.getComponent();
            
            int newX = c.getX() + (e.getX() - ClickPoint.x);
            int newY = c.getY() + (e.getY() - ClickPoint.y);
            c.setLocation(newX, newY);
        }

        @Override
        public void mouseReleased(MouseEvent e){
            Cards card = (Cards)e.getComponent();
            Rectangle cardRect = card.getBounds();
            Rectangle judgeRect = judgeArea.getBounds();

            if(cardRect.intersects(judgeRect)){
                card.lastp.y = 256;
                card.lastp.x = card.getX();
                if(!judge.contains(card)){
                    judge.add(card);
                }
                
            }else{
                card.lastp.x = card.initp.x;
                card.lastp.y = card.initp.y;
                if(judge.contains(card)){
                    judge.remove(judge.indexOf(card));
                }
            }
            showLabel.setText(printList(judge));
            card.setLocation(card.lastp);
            ClickPoint = null;
        }//手札は初期7枚+ランダム6枚の13枚

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ImageMove().setVisible(true));
    }
    
}