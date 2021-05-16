package cn.qingyun.domain;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import client.Client;

public class MainPanel extends JPanel implements KeyListener, Runnable {
    // User tank
    RoleTank roleTank = null;
    TeamTank teamTank = null;
    public int flag = 0;
    int roleTankOver = 0;
    int enemyTankOver = 1;
//    int teamTankOver = 1;
    public Vector<EnemyTank> enemyTanks = new Vector<EnemyTank>();   // array of enemyTanks
    public Vector<TeamTank> teamTanks = new Vector<TeamTank>();   // array of teamTanks
    int enemyTankNum = 0;
    private int NUMBER_CLIENTS = 4;

    Image image1 = null;
    Image image2 = null;
    Image image3 = null;
//    Image image4
    // connection instances
    Socket connection = null;
    DataOutputStream outToServer = null;
    BufferedReader inFromServer = null;
    Client client = null;
    int roleTankIdx;
    int teamTankIdx;

    // when tank got a bullet -> boommmmm
    Vector<Bobm> bobms = new Vector<Bobm>();
    public int clientIds[];
    public ArrayList<Integer> enemyIds = new ArrayList<Integer>(); ///
    public ArrayList<Integer> teamIds = new ArrayList<Integer>();
    public MainPanel(Client client, int clientIds[]) {
//        roleTank = new RoleTank(this.enemyTanks.size() * 50, this.enemyTanks.size() * 50);
        this.client = client;
        this.clientIds = clientIds;
//        try {
//            System.out.println("Out to server");
//            client.outToServer.writeBytes("mew mew mew" + '\n');
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        for (int i = 0; i < NUMBER_CLIENTS; i++) {
            if (this.clientIds[i] == this.client.clientId) {
                this.roleTankIdx = i;
                this.roleTank = new RoleTank((this.roleTankIdx + 1) * 20, (this.roleTankIdx + 1) * 70);
                break;

            }
        }

        switch (roleTankIdx) {
            case 0:
                this.teamTankIdx = 1;

                break;
            case 1:
                this.teamTankIdx = 0;
                break;
            case 2:
                this.teamTankIdx = 3;
                break;
            case 3:
                this.teamTankIdx = 2;
                break;
        }
        this.teamTank = new TeamTank((this.teamTankIdx + 1) * 20, (this.teamTankIdx + 1) * 70);
        this.teamTank.setColor(2);

        for (int i = 0; i < NUMBER_CLIENTS; i++) {
            if (i == roleTankIdx || i == teamTankIdx) continue;


            EnemyTank enemyTank = new EnemyTank((i + 1) * 20, (i + 1) * 70);
            enemyTank.setEnemyTanks(enemyTanks);

            enemyTank.setColor(0);
            enemyTank.setDirect(0);
            enemyTanks.add(enemyTank);
            this.enemyIds.add(this.clientIds[i]);
        }



        ImageIcon icon = new ImageIcon(Panel.class.getResource("/bomb_1.gif"));
        image1 = icon.getImage();
        ImageIcon icon2 = new ImageIcon(Panel.class.getResource("/bomb_2.gif"));
        image2 = icon2.getImage();
        ImageIcon icon3 = new ImageIcon(Panel.class.getResource("/bomb_3.gif"));
        image3 = icon3.getImage();
    }

    // direction: UP - DOWN - LEFT - RIGHT
    public void onTankMove(int tankId, String direction) {
        if ( tankId == this.clientIds[teamTankIdx]) {
            switch(direction){
                case "UP":
                    teamTank.setDirect(0);
                    teamTank.moveUp();
                    break;
                case "DOWN":
                    teamTank.setDirect(1);
                    teamTank.moveDown();
                    break;
                case "LEFT":
                    teamTank.setDirect(2);
                    teamTank.moveLeft();
                    break;
                case "RIGHT":
                    teamTank.setDirect(3);
                    teamTank.moveRight();
                    break;
            }
        }
        for (int i = 0; i < NUMBER_CLIENTS - 2; i++) {
            if (this.enemyIds.get(i) == tankId) {
                EnemyTank enemyTank = this.enemyTanks.get(i);
                switch (direction) {
                    case "UP":
                        enemyTank.setDirect(0);
                        enemyTank.moveUp();
                        break;
                    case "DOWN":
                        enemyTank.setDirect(1);
                        enemyTank.moveDown();
                        break;
                    case "LEFT":
                        enemyTank.setDirect(2);
                        enemyTank.moveLeft();
                        break;
                    case "RIGHT":
                        enemyTank.setDirect(3);
                        enemyTank.moveRight();
                        break;
                }
            }
        }
    }



    public void onTankShot(int tankId) {
        System.out.println(this.teamTankIdx);
        if (tankId == this.clientIds[teamTankIdx]){
            Shot shot=null;
            System.out.println("XXXXX"+teamTank.getDirect());

            switch (teamTank.getDirect()) {
                case 0:
                    shot = new Shot(teamTank.getX() + 8, teamTank.getY() - 10, teamTank.getDirect());
                    teamTank.shots.add(shot);
                    break;
                case 1:
                    shot = new Shot(teamTank.getX() + 10, teamTank.getY() + 32, teamTank.getDirect());
                    teamTank.shots.add(shot);
                    break;
                case 2:
                    shot = new Shot(teamTank.getX() - 10, teamTank.getY() + 12, teamTank.getDirect());
                    teamTank.shots.add(shot);
                    break;
                case 3:
                    shot = new Shot(teamTank.getX() + 30, teamTank.getY() + 12, teamTank.getDirect());
                    teamTank.shots.add(shot);
                    break;
            }
            System.out.println("YYYYY: "+shot.isLive);
            Thread t = new Thread(shot);
            t.start();
        }
        for (int i = 0; i < NUMBER_CLIENTS - 2; i++) {
            if (this.enemyIds.get(i) == tankId) {
                EnemyTank enemyTank = this.enemyTanks.get(i);
                Shot shot = null;
                switch (enemyTank.getDirect()) {
                    case 0:
                        shot = new Shot(enemyTank.getX() + 8, enemyTank.getY() - 10, enemyTank.getDirect());
                        enemyTank.shots.add(shot);
                        break;
                    case 1:
                        shot = new Shot(enemyTank.getX() + 10, enemyTank.getY() + 32, enemyTank.getDirect());
                        enemyTank.shots.add(shot);
                        break;
                    case 2:
                        shot = new Shot(enemyTank.getX() - 10, enemyTank.getY() + 12, enemyTank.getDirect());
                        enemyTank.shots.add(shot);
                        break;
                    case 3:
                        shot = new Shot(enemyTank.getX() + 30, enemyTank.getY() + 12, enemyTank.getDirect());
                        enemyTank.shots.add(shot);
                        break;
                }
                Thread t = new Thread(shot);
                t.start();
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
//        System.out.println("repaint " + this.enemyTanks.size());
        g.fillRect(0, 0, 400, 300);

        if (roleTank.isLive) {
            drawTank(roleTank.getX(), roleTank.getY(), g, roleTank.getDirect(), 1);
        }

        if (teamTank.isLive) {
            drawTank(teamTank.getX(), teamTank.getY(), g, teamTank.getDirect(), 2);
        }

        drawTank(60, 320, g, 0, 0);
        drawTank(430, 60, g, 0, 0);
        drawTank(130, 320, g, 0, 1);
        g.setColor(Color.black);
        Font font = new Font("����", Font.BOLD, 30);
        g.drawString(Message.enemyTankNums + "", 90, 340);
        g.drawString(Message.roleTankNums + "", 160, 340);
        Font font2 = new Font("����", Font.BOLD, 26);
        g.setFont(font2);
        g.drawString("���в�������", 430, 30);
        g.drawString(Message.hitTankNums + "", 460, 85);

        // paint enemy tanks
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            if (enemyTank.isLive) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 0);
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    Shot shot = enemyTank.shots.get(j);
                    if (shot.isLive) {
                        g.draw3DRect(shot.getX(), shot.getY(), 2, 2, false);
                    } else {
                        enemyTank.shots.remove(shot);
                    }

                }
            }
        }



        for (int i = 0; i < this.roleTank.shots.size(); i++) {
            Shot myShot = this.roleTank.shots.get(i);
            if (myShot != null && myShot.isLive == true) {
                g.setColor(Color.yellow);
                g.draw3DRect(myShot.getX(), myShot.getY(), 2, 2, false);
            }

            if (myShot.isLive == false) {
                this.roleTank.shots.remove(myShot);
            }
        }

        for (int i = 0; i < this.teamTank.shots.size(); i++) {
            Shot myShot = this.teamTank.shots.get(i);
            if (myShot != null && myShot.isLive == true) {
                g.setColor(Color.red);
                g.draw3DRect(myShot.getX(), myShot.getY(), 2, 2, false);
            }

            if (myShot.isLive == false) {
                this.teamTank.shots.remove(myShot);
            }
        }

        for (int i = 0; i < bobms.size(); i++) {
            Bobm bobm = bobms.get(i);
            if (bobm.isLive) {
                if (bobm.bobmLife > 6) {
                    g.drawImage(image1, bobm.getX(), bobm.getY(), 30, 30, this);
                } else if (bobm.bobmLife > 3) {
                    g.drawImage(image2, bobm.getX(), bobm.getY(), 30, 30, this);
                } else {
                    g.drawImage(image3, bobm.getX(), bobm.getY(), 30, 30, this);
                }
            }
            bobm.bobmDown();

            if (bobm.isLive == false) {
                bobms.remove(bobm);
            }
        }


    }

    private void hitTank(Shot shot, EnemyTank enemyTank) {
        switch (enemyTank.direct) {
            case 0:
            case 1:
                if (shot.getX() > enemyTank.getX() && shot.getX() < enemyTank.getX() + 20 && shot.getY() > enemyTank.getY() && shot.getY() < enemyTank.getY() + 30) {
                    shot.isLive = false;
                    enemyTank.isLive = false;
                    Message.downEnemyTankNums();
                    Message.addHitTankNumus();
                    Bobm bobm = new Bobm(enemyTank.getX(), enemyTank.getY());
                    bobms.add(bobm);



                }
                break;
            case 2:
            case 3:
                if (shot.getX() > enemyTank.getX() && shot.getX() < enemyTank.getX() + 30 && shot.getY() > enemyTank.getY() && shot.getY() < enemyTank.getY() + 20) {
                    shot.isLive = false;
                    enemyTank.isLive = false;
                    Message.downEnemyTankNums();
                    Message.addHitTankNumus();
                    Bobm bobm = new Bobm(enemyTank.getX(), enemyTank.getY());
                    bobms.add(bobm);




                }
                break;
        }
    }

    private void hitRoleTank(Shot shot, Tank roleTank2) {
        switch (roleTank2.direct) {
            case 0:
            case 1:
                if (shot.getX() > roleTank2.getX() && shot.getX() < roleTank2.getX() + 20 && shot.getY() > roleTank2.getY() && shot.getY() < roleTank2.getY() + 30) {
                    shot.isLive = false;
                    roleTank2.isLive = false;
                    Message.downRoleTankNums();

                    Bobm bobm = new Bobm(roleTank2.getX(), roleTank2.getY());
                    bobms.add(bobm);

                }
                break;
            case 2:
            case 3:
                if (shot.getX() > roleTank2.getX() && shot.getX() < roleTank2.getX() + 30 && shot.getY() > roleTank2.getY() && shot.getY() < roleTank2.getY() + 20) {
                    shot.isLive = false;
                    roleTank2.isLive = false;

                    Message.downRoleTankNums();

                    Bobm bobm = new Bobm(roleTank2.getX(), roleTank2.getY());
                    bobms.add(bobm);


                }
                break;
        }
    }


    private void drawTank(int x, int y, Graphics g, int direct, int type) {
        switch (type) {
            case 0: // enemy
                g.setColor(Color.cyan);
                break;
            case 1: // role
                g.setColor(Color.yellow);
                break;
            case 2:
                g.setColor(Color.red);
                break;
        }

        switch (direct) {
            case 0:
                g.fill3DRect(x, y, 5, 30, false);
                g.fill3DRect(x + 15, y, 5, 30, false);
                g.fill3DRect(x + 5, y + 5, 10, 20, false);
                g.fillOval(x + 5, y + 10, 10, 10);
                g.drawLine(x + 10, y, x + 10, y + 10);
                break;
            case 1:
                g.fill3DRect(x, y, 5, 30, false);
                g.fill3DRect(x + 15, y, 5, 30, false);
                g.fill3DRect(x + 5, y + 5, 10, 20, false);
                g.fillOval(x + 5, y + 10, 10, 10);
                g.drawLine(x + 10, y + 20, x + 10, y + 30);
                break;
            case 2:
                g.fill3DRect(x - 5, y + 20, 30, 5, false);
                g.fill3DRect(x - 5, y + 5, 30, 5, false);
                g.fill3DRect(x, y + 10, 20, 10, false);
                g.fillOval(x + 5, y + 10, 10, 10);
                g.drawLine(x + 5, y + 15, x - 5, y + 15);
                break;
            case 3:
                g.fill3DRect(x - 5, y + 20, 30, 5, false);
                g.fill3DRect(x - 5, y + 5, 30, 5, false);
                g.fill3DRect(x, y + 10, 20, 10, false);
                g.fillOval(x + 5, y + 10, 10, 10);
                g.drawLine(x + 15, y + 15, x + 25, y + 15);
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 38 && this.roleTank.isStop) {
            this.roleTank.setDirect(0);
            try {
                this.client.outToServer.writeBytes("ENEMY_MOVE " + "UP" + '\n');
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            this.roleTank.moveUp();
        } else if (e.getKeyCode() == 40 && this.roleTank.isStop) {
            this.roleTank.setDirect(1);
            this.roleTank.moveDown();
            try {
                this.client.outToServer.writeBytes("ENEMY_MOVE " + "DOWN" + '\n');
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else if (e.getKeyCode() == 37 && this.roleTank.isStop) {
            this.roleTank.setDirect(2);
            this.roleTank.moveLeft();
            try {
                this.client.outToServer.writeBytes("ENEMY_MOVE " + "LEFT" + '\n');
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else if (e.getKeyCode() == 39 && this.roleTank.isStop == true) {
            this.roleTank.setDirect(3);
            this.roleTank.moveRight();
            try {
                this.client.outToServer.writeBytes("ENEMY_MOVE " + "RIGHT" + '\n');
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        if (e.getKeyChar() == KeyEvent.VK_SPACE && this.roleTank.isStop) {
            if (this.roleTank.shots.size() < 5 && this.roleTank.isLive) {
                this.roleTank.shotRole();
                try {
                    this.client.outToServer.writeBytes("ENEMY_SHOT" + '\n');
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }

//        if (e.getKeyChar() == KeyEvent.VK_SPACE) {
//            if (flag % 2 != 0) {
//                this.roleTank.speed = 0;
//                this.roleTank.isStop = false;
//                for (int i = 0; i < enemyTanks.size(); i++) {
//                    EnemyTank enemyTank = enemyTanks.get(i);
//                    enemyTank.speed = 0;
//                    enemyTank.isStop = false;
//                    for (int j = 0; j < enemyTank.shots.size(); j++) {
//                        Shot shot = enemyTank.shots.get(j);
//                        shot.spend = 0;
//                    }
//                }
//                flag++;
//            } else {
//                this.roleTank.speed = 1;
//                this.roleTank.isStop = true;
//                for (int i = 0; i < enemyTanks.size(); i++) {
//                    EnemyTank enemyTank = enemyTanks.get(i);
//                    enemyTank.speed = 1;
//                    enemyTank.isStop = true;
//                    for (int j = 0; j < enemyTank.shots.size(); j++) {
//                        Shot shot = enemyTank.shots.get(j);
//                        shot.spend = 3;
//                    }
//                }
//                flag++;
//            }
//
//        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(50);
                this.repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < this.roleTank.shots.size(); i++) {
                Shot shot = this.roleTank.shots.get(i);
                if (shot.isLive) {
                    for (int j = 0; j < this.enemyTanks.size(); j++) {
                        EnemyTank enemyTank = this.enemyTanks.get(j);
                        if (enemyTank.isLive) {
                            hitTank(shot, enemyTank);
                        }
                    }
                }
            }

            for (int i = 0; i < this.teamTank.shots.size(); i++) {
                Shot shot = this.teamTank.shots.get(i);
                if (shot.isLive) {
                    for (int j = 0; j < this.enemyTanks.size(); j++) {
                        EnemyTank enemyTank = this.enemyTanks.get(j);
                        if (enemyTank.isLive) {
                            hitTank(shot, enemyTank);
                        }
                    }
                }
            }

            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                if (enemyTank.isLive) {
                    for (int j = 0; j < enemyTank.shots.size(); j++) {
                        Shot shot = enemyTank.shots.get(j);
                        if (shot.isLive) {
                            RoleTank roleTank = this.roleTank;
                            if (roleTank.isLive) {
                                hitRoleTank(shot, roleTank);
//                                hitRoleTank(shot, teamTank);
                            }
                            if (teamTank.isLive) {
//                                hitRoleTank(shot, roleTank);
                                hitRoleTank(shot, teamTank);
                            }
                        }
                    }
                }
            }

            this.repaint();
        }
    }
}
