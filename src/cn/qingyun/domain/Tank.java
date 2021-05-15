package cn.qingyun.domain;

import javax.swing.JPanel;

/**
 * ̹�����û��趨̹�˵�λ��
 *
 * @author ������
 */
public class Tank {

    int x;
    int y;

    int speed = 5;

    public int direct = 0;

    public int Color = 0;

    public Tank(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }

    public void moveUp() {
        y -= speed;
    }

    public void moveDown() {
        y += speed;
    }

    public void moveLeft() {
        x -= speed;
    }

    public void moveRight() {
        x += speed;
    }


    //----------------------------------------------

    public int getX() {
        return x;
    }

    public int getColor() {
        return Color;
    }

    public void setColor(int color) {
        Color = color;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }


}
