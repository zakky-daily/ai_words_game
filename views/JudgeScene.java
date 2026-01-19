package views;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class JudgeScene extends JPanel {
    private JButton goTitleButton;
    public JudgeScene() {

        this.setSize(640, 680);
        
        this.setLayout(null);
        this.setBackground(Color.WHITE);


        goTitleButton = new JButton("おわる");
        goTitleButton.setActionCommand("END");
        goTitleButton.setBounds(350, 400, 230, 80);
        goTitleButton.setBackground(Color.BLUE);
        this.add(goTitleButton);

    }

    public void setEndButtonListener(ActionListener listener){
        goTitleButton.addActionListener(listener);
    }
}