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

/**
 * ��Ϸ������
 * @author ������
 *
 */
public class MainFrame extends JFrame implements ActionListener{
       
	private JMenuItem menuItem;
	private StartPanel startPanel;

	public MainFrame(){
		
		initFrame();
		
		addPanel();
	}
	

	private void addPanel() {
		//�˵���
		JMenuBar menuBar = new JMenuBar();
		//�˵�
		JMenu menu = new JMenu("��Ϸ(G)");
		menu.setMnemonic('G');
		menuItem = new JMenuItem("��ʼ����Ϸ(N)");
		JMenuItem exitGame = new JMenuItem("�˳���Ϸ(E)");
		exitGame.setMnemonic('E');
		//��Ӷ�������
		menuItem.addActionListener(this);
		menuItem.setActionCommand("newGame");
		exitGame.addActionListener(this);
		exitGame.setActionCommand("exitGame");
		
		menu.add(menuItem);
		menu.add(exitGame);
		menuBar.add(menu);
		startPanel = new StartPanel();
		Thread thread = new Thread(startPanel);
		thread.start();
		this.setJMenuBar(menuBar);
		
		this.add(startPanel);
	
		
	}

	//��ʼ������
	 private void initFrame() {
		this.setTitle("̹�˴�ս");
		this.setSize(600, 450);
		this.setLocation(200,200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
	      if(e.getActionCommand().equals("newGame")){
	    	  //��Ϸ���
	    	  Client client = new Client("127.0.0.1", 4321);
	    	  MainPanel mainPanel = new MainPanel(client);
	    	  mainPanel.repaint();
	    	  Thread thread = new Thread(mainPanel);
	    	  thread.start();
	    	  //�Ƴ���һ�����
	    	  this.remove(startPanel);
	    	  this.add(mainPanel);
	    	  this.addKeyListener(mainPanel);
	    	  //ˢ����ʾ�����
	    	  //�����ļ�
//	    	  Voice voice = new Voice("F:\\��Ϸ����\\voice.wav");
//	    	  voice.start();
	    	  this.setVisible(true);
	    	  
	      }else if(e.getActionCommand().equals("exitGame")){
	    	  System.exit(0);
	      }
	}


}
