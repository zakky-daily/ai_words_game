package views;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Dialog {
    private JLabel imageLabel;
    private JButton prevButton;
    private JButton nextButton;

    public void show(JFrame parentFrame, ActionListener prevAction, ActionListener nextAction, ImageIcon initialImage, boolean isFirst, boolean isLast){
        // 全体
        JPanel panel = new JPanel(new BorderLayout());

        // 遊び方画像
        imageLabel = new JLabel(initialImage);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(imageLabel, BorderLayout.CENTER);

        // ボタン
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        prevButton = new JButton("◀");// 「◀ 前へ」ボタン
        prevButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        prevButton.addActionListener(prevAction);
        prevButton.setEnabled(false); // 最初のページなら無効化

        nextButton = new JButton("▶");// 「次へ ▶」ボタン
        nextButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        nextButton.addActionListener(nextAction);
        nextButton.setEnabled(!isLast);//最後のページなら無効化

        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        // 表示ダイアログ
        JOptionPane.showMessageDialog(
            parentFrame,
            panel,
            "遊び方",
            JOptionPane.PLAIN_MESSAGE
        );
    }

    //画面を更新
    public void updateState(ImageIcon nextImage, boolean isFirst, boolean isLast) {
        imageLabel.setIcon(nextImage);
        prevButton.setEnabled(!isFirst);
        nextButton.setEnabled(!isLast);
    }
}
