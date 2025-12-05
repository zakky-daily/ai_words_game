import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class View1 extends JFrame implements ActionListener {
    private JFrame f;
    private JTextField input;
    public String ans, res;
    private JLabel resLabel;
      
    public View1() {
      input = new JTextField();
      this.setLayout(new GridLayout(2,2));
      this.add(new JLabel("Response"));
      resLabel = new JLabel(res);
      this.add(resLabel);
      this.add(new JLabel("Your Answer"));
      this.add(input);
      this.pack();
      input.addActionListener(this);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setVisible(true);
    }

    public void SendAI(){
        res = ans;
        resLabel.setText((res));
    }

    public void actionPerformed(ActionEvent e){
      if (e.getSource()==input){
        ans = input.getText();
        SendAI();
      } 
    }
    public static void main(String argv[]) {
      new View1();
    }
}