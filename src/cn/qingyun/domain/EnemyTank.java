package cn.qingyun.domain;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class EnemyTank extends Tank implements Runnable {

    boolean isLive = true;

    //int spend = 1;
    boolean isStop = true;

    public Vector<Shot> shots = new Vector<Shot>();

    Vector<EnemyTank> enemyTanks = new Vector<EnemyTank>();


    public EnemyTank(int x, int y) {
        super(x, y);
    }

    public boolean isTouchOtherTank() {

        boolean flag = false;

        switch (this.direct) {

            case 0:
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank != this) {

                        if (enemyTank.direct == 0 || enemyTank.direct == 1) {

                            //this̹�˵����ϵ�
                            if (this.x >= enemyTank.x && this.x <= enemyTank.x + 20 && this.y >= enemyTank.y && this.y <= enemyTank.y + 30) {
                                return true;
                            }
                            //this̹�˵����ϵ�
                            if (this.x + 20 >= enemyTank.x && this.x + 20 <= enemyTank.x + 20 && this.y >= enemyTank.y && this.y <= enemyTank.y + 30) {
                                return true;
                            }
                        }

                        if (enemyTank.direct == 2 || enemyTank.direct == 3) {   //̹�������������
                            //this̹�˵����ϵ�
                            if (this.x >= enemyTank.x && this.x <= enemyTank.x + 30 && this.y >= enemyTank.y && this.y <= enemyTank.y + 20) {
                                return true;
                            }
                            //this̹�˵����µ�
                            if (this.x >= enemyTank.x && this.x <= enemyTank.x + 30 && this.y + 30 >= enemyTank.y && this.y + 30 <= enemyTank.y + 20) {
                                return true;
                            }
                        }
                    }
                }
                break;

            case 1:   //��ǰ̹������
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (this != enemyTank) {

                        if (enemyTank.direct == 0 || enemyTank.direct == 1) { //̹�����»�������

                            //this̹�˵����µ�
                            if (this.x >= enemyTank.x && this.x <= enemyTank.x + 20 && this.y + 30 >= enemyTank.y && this.y + 30 <= enemyTank.y + 30) {
                                return true;
                            }
                            //this̹�˵����µ�
                            if (this.x + 20 >= enemyTank.x && this.x + 20 <= enemyTank.x + 20 && this.y + 30 >= enemyTank.y && this.y + 30 <= enemyTank.y + 30) {
                                return true;
                            }
                        }

                        if (enemyTank.direct == 2 || enemyTank.direct == 3) {  //̹�������������

                            //this̹�˵����ϵ�
                            if (this.x >= enemyTank.x && this.x <= enemyTank.x + 30 && this.y >= enemyTank.y && this.y <= enemyTank.y + 20) {
                                return true;
                            }
                            //this̹�˵����µ�
                            if (this.x >= enemyTank.x && this.x <= enemyTank.x + 30 && this.y + 30 >= enemyTank.y && this.y + 30 <= enemyTank.y + 20) {
                                return true;
                            }
                        }
                    }
                }
                break;

            case 2:   //��ǰ̹������
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (this != enemyTank) {

                        if (enemyTank.direct == 0 || enemyTank.direct == 1) {  //̹�����»��������ƶ�

                            //this̹�����Ͻǵ�
                            if (this.x >= enemyTank.x && this.x <= enemyTank.x + 20 && this.y >= enemyTank.y && this.y <= enemyTank.y + 30) {
                                return true;
                            }
                            //this̹�����½ǵ�
                            if (this.x >= enemyTank.x && this.x <= enemyTank.x + 20 && this.y + 20 >= enemyTank.y && this.y + 20 <= enemyTank.y + 30) {
                                return true;
                            }
                        }

                        if (enemyTank.direct == 2 || enemyTank.direct == 3) {  //̹��������������ƶ�

                            //this̹�����Ͻǵ�
                            if (this.x >= enemyTank.x && this.x <= enemyTank.x + 30 && this.y >= enemyTank.y && this.y <= enemyTank.y + 20) {
                                return true;
                            }
                            //this̹�����½ǵ�
                            if (this.x >= enemyTank.x && this.x <= enemyTank.x + 30 && this.y + 20 >= enemyTank.y && this.y + 20 <= enemyTank.y + 20) {
                                return true;
                            }
                        }
                    }
                }
                break;

            case 3:   //��ǰ̹������
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (this != enemyTank) {

                        if (enemyTank.direct == 0 || enemyTank.direct == 1) { //̹�����ϻ��������ƶ�

                            //this̹�˵����ϵ�
                            if (this.x + 30 >= enemyTank.x && this.x + 30 <= enemyTank.x + 20 && this.y >= enemyTank.y && this.y <= enemyTank.y + 30) {
                                return true;
                            }
                            //this̹�˵����µ�
                            if (this.x + 30 >= enemyTank.x && this.x + 30 <= enemyTank.x + 20 && this.y + 20 >= enemyTank.y && this.y + 20 <= enemyTank.y + 30) {
                                return true;
                            }
                        }

                        if (enemyTank.direct == 2 || enemyTank.direct == 3) {   //̹��������������ƶ�

                            //this̹�˵����ϵ�
                            if (this.x + 30 >= enemyTank.x && this.x + 30 <= enemyTank.x + 30 && this.y >= enemyTank.y && this.y <= enemyTank.y + 20) {
                                return true;
                            }
                            //this̹�˵����µ�
                            if (this.x + 30 >= enemyTank.x && this.x + 30 <= enemyTank.x + 30 && this.y + 20 >= enemyTank.y && this.y + 20 <= enemyTank.y + 20) {
                                return true;
                            }
                        }
                    }
                }
                break;


        }

        return flag;
    }

    //ʵ��run����
    @Override
    public void run() {
//        while (true) {
//            switch (this.direct) {
//                case 0:
//                    for (int i = 0; i < 30; i++) {
//                        if (this.y > 0 && !isTouchOtherTank()) {
//                            this.y -= getSpeed();
//                        }
//                        try {
//                            Thread.sleep(50);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    break;
//
//                case 1:
//                    for (int i = 0; i < 30; i++) {
//                        if (this.y < 275 && !isTouchOtherTank()) {
//                            this.y += getSpeed();
//                        }
//                        try {
//                            Thread.sleep(50);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    break;
//
//                case 2:
//                    for (int i = 0; i < 30; i++) {
//                        if (this.x > 0 && !isTouchOtherTank()) {
//                            this.x -= getSpeed();
//                        }
//                        try {
//                            Thread.sleep(50);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    break;
//
//                case 3:
//                    for (int i = 0; i < 30; i++) {
//                        if (this.x < 380 && !isTouchOtherTank()) {
//                            this.x += getSpeed();
//                        }
//                        try {
//                            Thread.sleep(50);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    break;
//            }
//
//            //�ж��Ƿ�Ҫ��̹�˼����ӵ�
//
//            if (this.isLive && isStop) {
//                Shot shot = null;
//                if (this.shots.size() < 5) {
//                    //���ݷ����ж��ӵ���λ��
//                    switch (this.getDirect()) {
//                        case 0:
//                            //����һ���ӵ�
//                            shot = new Shot(this.getX() + 8, this.getY() - 10, this.getDirect());
//                            //��ӵ�������
//                            this.shots.add(shot);
//                            break;
//                        case 1:
//                            shot = new Shot(this.getX() + 10, this.getY() + 32, this.getDirect());
//                            this.shots.add(shot);
//                            break;
//                        case 2:
//                            shot = new Shot(this.getX() - 10, this.getY() + 12, this.getDirect());
//                            this.shots.add(shot);
//                            break;
//                        case 3:
//                            shot = new Shot(this.getX() + 30, this.getY() + 12, this.getDirect());
//                            this.shots.add(shot);
//                            break;
//                    }
//                }
//                Thread t = new Thread(shot);
//                t.start();
//            }
//
//
//            //�������һ������
//            if (isStop) {
//                this.direct = (int) (Math.random() * 4);
//            }
//            //�жϵ���̹���Ƿ�����
//            if (this.isLive == false) {
//                break;
//            }
//
//
//        }
    }


    //----------------------------------------------

    public int getDirect() {
        return direct;
    }


    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }


}
