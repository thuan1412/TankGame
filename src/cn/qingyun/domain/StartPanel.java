package cn.qingyun.domain;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.border.Border;

public class StartPanel extends JPanel {
    int times = 0;
    int userCount = 0;
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 600, 450);
        if (times % 2 == 0) {
            g.setColor(Color.green);
            Font myFont = new Font(Font.SANS_SERIF, Font.BOLD, 30);
            g.setFont(myFont);
            g.drawString("Total number : " + userCount, 150, 150);
        }
    }

    public void updateUserCount(int userCount) {
        this.userCount = userCount;
        this.repaint();
    }
}
