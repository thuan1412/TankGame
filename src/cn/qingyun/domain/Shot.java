package cn.qingyun.domain;

public class Shot implements Runnable {

    int x, y;
    int direct;
    int spend = 5;
    boolean isLive = true;

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }


    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(50);
                switch (this.direct) {
                    case 0:
                        y -= spend;
                        break;
                    case 1:
                        y += spend;
                        break;
                    case 2:
                        x -= spend;
                        break;
                    case 3:
                        x += spend;
                        break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (this.x < -2 || this.x > 400 || this.y < -2 || this.y > 300) {
                this.isLive = false;
                break;
            }
        }

    }


    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }


    public int getX() {
        return x;
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


}
