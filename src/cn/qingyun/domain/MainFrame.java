package cn.qingyun.domain;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import client.Client;

public class MainFrame extends JFrame implements ActionListener {

    private JMenuItem menuItem;
    private StartPanel startPanel = new StartPanel();
    Client client;
    public int[] clientIds;

    public MainFrame() {
        initFrame();
        addPanel();
        this.client = new Client("127.0.0.1", 4321);
        this.client.startPanel = startPanel;
        this.client.mainFrame = this;
        System.out.println("start panel: " + startPanel);
        this.client.sendMessage("NEW_PLAYER");
    }

    private void addPanel() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        menu.setMnemonic('G');
        menuItem = new JMenuItem("New game(N)");
        JMenuItem exitGame = new JMenuItem("Exit(E)");
        exitGame.setMnemonic('E');

        menuItem.addActionListener(this);
        menuItem.setActionCommand("newGame");
        exitGame.addActionListener(this);
        exitGame.setActionCommand("exitGame");

        menu.add(menuItem);
        menu.add(exitGame);
        menuBar.add(menu);
        this.setJMenuBar(menuBar);
        this.add(startPanel);
    }

    private void initFrame() {
        this.setTitle("Tank game");
        this.setSize(600, 450);
        this.setLocation(200, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }

    public  void enterGame() {
        MainPanel mainPanel = new MainPanel(client, clientIds);

        this.client.mainPanel = mainPanel;

        mainPanel.repaint();
        Thread thread = new Thread(mainPanel);
        thread.start();

        this.remove(startPanel);
        this.add(mainPanel);
        this.addKeyListener(mainPanel);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("newGame")) {
            enterGame();
//            EnemyTank[] enemyTanks = {};
//            MainPanel mainPanel = new MainPanel(client, enemyTanks);
//
//            this.client.mainPanel = mainPanel;
//
//
//            mainPanel.repaint();
//            Thread thread = new Thread(mainPanel);
//            thread.start();
//
//            this.remove(startPanel);
//            this.add(mainPanel);
//            this.addKeyListener(mainPanel);
////	    	  Voice voice = new Voice("F:\\��Ϸ����\\voice.wav");
////	    	  voice.start();
//            this.setVisible(true);

        } else if (e.getActionCommand().equals("exitGame")) {
            System.exit(0);
        }
    }


}
