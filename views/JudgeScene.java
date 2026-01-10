package views;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class JudgeScene extends JPanel {
    private JButton goTitleButton;
    public JudgeScene() {

        this.setSize(640, 680);
        
        this.setLayout(null);
        this.setBackground(Color.WHITE);


        goTitleButton = new JButton("はじめる");
        goTitleButton.setBounds(350, 400, 230, 80);
        goTitleButton.setBackground(Color.BLUE);
        this.add(goTitleButton);

    }
}