package cn.qingyun.domain;

import java.util.Vector;

public class RoleTank extends Tank {

    Shot shot = null;
    Vector<Shot> shots = new Vector<Shot>();
    boolean isLive = true;
    boolean isStop = true;

    public RoleTank(int x, int y) {
        super(x, y);
    }

    public void shotRole() {
        // from tank's current direction, add the bullet to frame
        switch (this.getDirect()) {
            case 0:
                shot = new Shot(this.getX() + 8, this.getY() - 10, this.getDirect());
                shots.add(shot);
                break;
            case 1:
                shot = new Shot(this.getX() + 10, this.getY() + 32, this.getDirect());
                shots.add(shot);
                break;
            case 2:
                shot = new Shot(this.getX() - 10, this.getY() + 12, this.getDirect());
                shots.add(shot);
                break;
            case 3:
                shot = new Shot(this.getX() + 30, this.getY() + 12, this.getDirect());
                shots.add(shot);
                break;
        }
        Thread t = new Thread(shot);
        t.start();

    }
}
