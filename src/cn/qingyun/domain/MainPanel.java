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
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import client.Client;

public class MainPanel extends JPanel implements KeyListener,Runnable{
	RoleTank roleTank = null;
	
	int flag = 1;
	int roleTankOver = 0;
	int enemyTankOver = 0;
	
	Vector<EnemyTank> enemyTanks = new Vector<EnemyTank>();
	
	int enemyTankNum = 3;
	
	//��������ͼƬ
	Image image1 = null;
	Image image2 = null;
	Image image3 = null;
	
	Socket connection = null;
	DataOutputStream outToServer = null;
	BufferedReader inFromServer = null;
	
	//���屣�汬ը��ļ���
	Vector<Bobm> bobms = new Vector<Bobm>();
	Client client = null;
	public  MainPanel(Client client){
		roleTank = new RoleTank(200,270);    
//		Client client = new Client("127.0.0.1", 4321);
		this.client = client;
		try {
			System.out.println("Out to server");
			client.outToServer.writeBytes("mew mew mew" + '\n');
		} catch (IOException e) {
			e.printStackTrace();
		}
		//��ʼ������̹��
		for(int i = 0;i < enemyTankNum;i++){
			EnemyTank enemyTank = new EnemyTank((i+1)*50,0);
			//������̹����ӵ�̹�������
			enemyTank.setEnemyTanks(enemyTanks);
			
			enemyTank.setColor(0);
			enemyTank.setDirect(1);
			//��������̹���߳�
			Thread thread = new Thread(enemyTank);
			thread.start();
			//��ӵ���̹�˵��ӵ�
			Shot shot = new Shot(enemyTank.getX()+10,enemyTank.getY()+30,enemyTank.direct);
			//�ӵ���ӵ��ӵ�������
			enemyTank.shots.add(shot);
			//���������ӵ��߳�
			Thread threadShot = new Thread(shot);
			threadShot.start();
			
			enemyTanks.add(enemyTank);
		}
		
		//��ʼ����ըͼƬ
//		try {
//			image1 = ImageIO.read(new File("bomb_1.gif"));
//			image2 = ImageIO.read(new File("bomb_2.gif"));
//			image3 = ImageIO.read(new File("bomb_3.gif"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
//		image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
//		image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));
		ImageIcon icon = new ImageIcon(Panel.class.getResource("/bomb_1.gif"));
		image1 = icon.getImage();
		ImageIcon icon2 = new ImageIcon(Panel.class.getResource("/bomb_2.gif"));
		image2 = icon2.getImage();
		ImageIcon icon3 = new ImageIcon(Panel.class.getResource("/bomb_3.gif"));
		image3 = icon3.getImage();
	}
	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		//���ñ��������ɫ
		g.fillRect(0, 0, 400, 300);
		
		//����̹�˵ķ���
		if(roleTank.isLive){
			drawTank(roleTank.getX(),roleTank.getY(),g,roleTank.getDirect(),1);
		}
		
		//������Ϸ��Ϣ
		drawTank(60, 320, g, 0, 0);
		drawTank(430, 60, g, 0, 0);
		drawTank(130, 320, g, 0, 1);
		g.setColor(Color.black);
		Font font = new Font("����",Font.BOLD,30);
		g.drawString(Message.enemyTankNums+"", 90, 340);
		g.drawString(Message.roleTankNums+"", 160, 340);
		Font font2 = new Font("����",Font.BOLD,26);
		g.setFont(font2);
	    g.drawString("���в�������", 430, 30);
	    g.drawString(Message.hitTankNums+"", 460, 85);
		
		//��������̹��
		for(int i = 0;i < enemyTanks.size();i++){
			EnemyTank enemyTank = enemyTanks.get(i);
			if(enemyTank.isLive){
				//��������̹��
				drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 0);
				//���������ӵ�
				for(int j = 0;j < enemyTank.shots.size();j++){
					Shot shot = enemyTank.shots.get(j);
					if(shot.isLive){
						g.draw3DRect(shot.getX(),shot.getY(), 2, 2, false);
					}else{
						//�Ƴ������ĵ����ӵ�
						enemyTank.shots.remove(shot);
					}
					
				}
			}
		}
		
		//�����ӵ�����
		for(int i = 0;i <this.roleTank.shots.size();i++){
			Shot myShot = this.roleTank.shots.get(i);
			if(myShot != null && myShot.isLive == true){
				g.setColor(Color.yellow);
				g.draw3DRect(myShot.getX(),myShot.getY(), 2, 2, false);
			}
			
			//�Ƴ����粻�����ӵ�
			if(myShot.isLive == false){
				this.roleTank.shots.remove(myShot);
			}
		}
		

		//������ըЧ��
		for(int i = 0;i < bobms.size();i++){
			Bobm bobm = bobms.get(i);
			   if(bobm.isLive){
				   if(bobm.bobmLife > 6){
						g.drawImage(image1, bobm.getX(),bobm.getY(), 30, 30, this);
					}else if(bobm.bobmLife > 3){
						g.drawImage(image2, bobm.getX(),bobm.getY(), 30, 30, this);
					}else{
						g.drawImage(image3, bobm.getX(),bobm.getY(), 30, 30, this);
					}
			   }
				//����ֵ��1
				bobm.bobmDown();
				
				if(bobm.isLive == false){
					bobms.remove(bobm);
				}
		}
		
		
	}

	//�ж��ӵ��Ƿ���е���̹�˵ķ���
	private void hitTank(Shot shot,EnemyTank enemyTank){
		switch (enemyTank.direct) {
		case 0:
		case 1:
			  //�ж��Ƿ����(���·���)
			  if(shot.getX() > enemyTank.getX() && shot.getX() < enemyTank.getX()+20 && shot.getY() > enemyTank.getY() && shot.getY() < enemyTank.getY()+30){
				  //�ӵ�����
				  shot.isLive = false;
				  //����̹������
				  enemyTank.isLive = false;
				  //����̹��������1
				  Message.downEnemyTankNums();
				  //��Ҵ�����̹������1
				  Message.addHitTankNumus();
				  //����һ����ը��
				  Bobm bobm = new Bobm(enemyTank.getX(),enemyTank.getY());
				  //��ӵ���ը������
				  bobms.add(bobm);
				  
				  enemyTankOver++;
				  if(enemyTankOver < 18){
					  EnemyTank newEnemyTank = new EnemyTank(280,0);
						//������̹����ӵ�̹�������
					  newEnemyTank.setEnemyTanks(enemyTanks);
						
					  newEnemyTank.setColor(0);
					  newEnemyTank.setDirect(1);
						//��������̹���߳�
						Thread thread = new Thread(newEnemyTank);
						thread.start();
						//��ӵ���̹�˵��ӵ�
						Shot newShot = new Shot(newEnemyTank.getX()+10,newEnemyTank.getY()+30,newEnemyTank.direct);
						//�ӵ���ӵ��ӵ�������
						newEnemyTank.shots.add(newShot);
						//���������ӵ��߳�
						Thread threadShot = new Thread(newShot);
						threadShot.start();
						
						enemyTanks.add(newEnemyTank);
				  }else if(enemyTankOver == 20){
					  JOptionPane.showConfirmDialog(this, "��õ��������������еĵ���̹�ˣ�");
				  }
				 
			  }
			break;
		case 2:
		case 3:
			 //�ж��Ƿ����(���ҷ���)
			if(shot.getX() > enemyTank.getX() && shot.getX() < enemyTank.getX() + 30 && shot.getY() > enemyTank.getY() && shot.getY() < enemyTank.getY() +20){
				 //�ӵ�����
				  shot.isLive = false;
				  //����̹������
				  enemyTank.isLive = false;
				  //����̹��������1
				  Message.downEnemyTankNums();
				  //��Ҵ�����̹������1
				  Message.addHitTankNumus();
				  //����һ����ը��
				  Bobm bobm = new Bobm(enemyTank.getX(),enemyTank.getY());
				  //��ӵ���ը������
				  bobms.add(bobm);
				  
				  enemyTankOver++;
				  if(enemyTankOver < 18){
					  EnemyTank newEnemyTank = new EnemyTank(280,0);
						//������̹����ӵ�̹�������
					  newEnemyTank.setEnemyTanks(enemyTanks);
						
					  newEnemyTank.setColor(0);
					  newEnemyTank.setDirect(1);
						//��������̹���߳�
						Thread thread = new Thread(newEnemyTank);
						thread.start();
						//��ӵ���̹�˵��ӵ�
						Shot newShot = new Shot(newEnemyTank.getX()+10,newEnemyTank.getY()+30,newEnemyTank.direct);
						//�ӵ���ӵ��ӵ�������
						newEnemyTank.shots.add(newShot);
						//���������ӵ��߳�
						Thread threadShot = new Thread(newShot);
						threadShot.start();
						
						enemyTanks.add(newEnemyTank);
				  }else if(enemyTankOver == 20){
					  JOptionPane.showConfirmDialog(this, "��õ��������������еĵ���̹�ˣ�");
				  }
				 
				
			}
			break;
		}
	}
	
	//�жϵ����ӵ��Ƿ�������̹�˵ķ���
	private void hitRoleTank(Shot shot, RoleTank roleTank2) {
		switch (roleTank2.direct) {
		case 0:
		case 1:
			  //�ж��Ƿ����(���·���)
			  if(shot.getX() > roleTank2.getX() && shot.getX() < roleTank2.getX()+20 && shot.getY() > roleTank2.getY() && shot.getY() < roleTank2.getY()+30){
				  //�ӵ�����
				  shot.isLive = false;
				  //����̹������
				  roleTank2.isLive = false;
				  //���̹������1
				  Message.downRoleTankNums();
				 
				  //����һ����ը��
				  Bobm bobm = new Bobm(roleTank2.getX(),roleTank2.getY());
				  //��ӵ���ը������
				  bobms.add(bobm);
				  
				//�ж�����Ƿ�����3��
				  roleTankOver++;
				 if(roleTankOver < 3){
					 roleTank = new RoleTank(100,100);
				 }else{
					 JOptionPane.showConfirmDialog(this, "������̫���ˣ�����");
				 }
				
			  }
			break;
		case 2:
		case 3:
			 //�ж��Ƿ����(���ҷ���)
			if(shot.getX() > roleTank2.getX() && shot.getX() < roleTank2.getX() + 30 && shot.getY() > roleTank2.getY() && shot.getY() < roleTank2.getY() +20){
				 //�ӵ�����
				  shot.isLive = false;
				  //����̹������
				  roleTank2.isLive = false;
				  
				  //���̹������1
				  Message.downRoleTankNums();
				
				  //����һ����ը��
				  Bobm bobm = new Bobm(roleTank2.getX(),roleTank2.getY());
				  //��ӵ���ը������
				  bobms.add(bobm);
				//�ж�����Ƿ�����3��
				  roleTankOver++;
				 if(roleTankOver < 3){
					 roleTank = new RoleTank(100,100);
				 }else{
					 JOptionPane.showConfirmDialog(this, "������̫���ˣ�����");
				 }
				 
			}
			break;
		}
	}
	
	
	//����̹�˵ķ���
	private void drawTank(int x, int y, Graphics g, int direct, int type) {
		
		switch(type){   //������ɫ
		case 0:
			g.setColor(Color.cyan);
			break;
		case 1:
			g.setColor(Color.yellow);
			break;
		}
		
		switch(direct){   //���÷���
		case 0:            //����
			//������߾���
			g.fill3DRect(x, y, 5, 30, false);
			//�����ұ߾���
			g.fill3DRect(x+15, y, 5, 30, false);
			//�����м����
			g.fill3DRect(x+5, y+5, 10, 20, false);
			//�����м��Բ
			g.fillOval(x+5,y+10, 10, 10);
			//������
			g.drawLine(x+10, y, x+10, y+10);
			break;
		case 1:           //����
			//������߾���
			g.fill3DRect(x, y, 5, 30, false);
			//�����ұ߾���
			g.fill3DRect(x+15, y, 5, 30, false);
			//�����м����
			g.fill3DRect(x+5, y+5, 10, 20, false);
			//�����м��Բ
			g.fillOval(x+5,y+10, 10, 10);
			//������
			g.drawLine(x+10, y+20, x+10, y+30);
			break;
		case 2:         //����
			//������߾���
			g.fill3DRect(x-5, y+20, 30, 5, false);
			//�����ұ߾���
			g.fill3DRect(x-5, y+5, 30, 5, false);
			//�����м����
			g.fill3DRect(x, y+10, 20, 10, false);
			//�����м��Բ
			g.fillOval(x+5,y+10, 10, 10);
			//������
			g.drawLine(x+5, y+15, x-5, y+15);
			break;
		case 3:          //����
			//������߾���
			g.fill3DRect(x-5, y+20, 30, 5, false);
			//�����ұ߾���
			g.fill3DRect(x-5, y+5, 30, 5, false);
			//�����м����
			g.fill3DRect(x, y+10, 20, 10, false);
			//�����м��Բ
			g.fillOval(x+5,y+10, 10, 10);
			//������
			g.drawLine(x+15, y+15, x+25, y+15);
			break;
			
		}   
	}

	//����ѹ�� WΪ���ϣ�SΪ���£�AΪ����DΪ����
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == 38 && this.roleTank.isStop == true){
			this.roleTank.setDirect(0);
			try {
				this.client.outToServer.writeBytes("UP" + '\n');
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			this.roleTank.moveUp();
		}else if(e.getKeyCode() == 40 && this.roleTank.isStop == true){
			this.roleTank.setDirect(1);
			this.roleTank.moveDown();
			try {
				this.client.outToServer.writeBytes("DOWN" + '\n');
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}else if(e.getKeyCode() == 37 && this.roleTank.isStop == true){
			this.roleTank.setDirect(2);
			this.roleTank.moveLeft();
			try {
				this.client.outToServer.writeBytes("LEFT" + '\n');
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}else if(e.getKeyCode() == 39 && this.roleTank.isStop == true){
			this.roleTank.setDirect(3);
			this.roleTank.moveRight();
			try {
				this.client.outToServer.writeBytes("RIGHT" + '\n');
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}  
		
		if(e.getKeyCode() == 10 && this.roleTank.isStop == true){
			if(this.roleTank.shots.size() < 5 && this.roleTank.isLive == true ){
				this.roleTank.shotRole();
			}	
		}
		
		if(e.getKeyChar() == KeyEvent.VK_SPACE){
			if(flag % 2 != 0){
				this.roleTank.speed = 0;
				this.roleTank.isStop = false;
				 for(int i = 0;i < enemyTanks.size();i++){
			        	EnemyTank enemyTank = enemyTanks.get(i);
			        	enemyTank.speed = 0;
			        	enemyTank.isStop = false;
			        	for(int j = 0;j < enemyTank.shots.size();j++){
			        		Shot shot = enemyTank.shots.get(j);
			        		shot.spend = 0;
			        	}
			        }
				flag ++;
			}else{
				this.roleTank.speed = 1;
				this.roleTank.isStop = true;
				 for(int i = 0;i < enemyTanks.size();i++){
			        	EnemyTank enemyTank = enemyTanks.get(i);
			        	enemyTank.speed = 1;
			        	enemyTank.isStop = true;
			        	for(int j = 0;j < enemyTank.shots.size();j++){
			        		Shot shot = enemyTank.shots.get(j);
			        		shot.spend = 3;
			        	}
			        }
				flag ++;
			}
			
		}
		this.repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}


	//ÿ��100�����ػ�һ��ͼ���߳�
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(50);
				this.repaint();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//�����ж��Ƿ���е���̹��
			for( int i = 0;i < this.roleTank.shots.size();i++){
				Shot shot = this.roleTank.shots.get(i);
				if(shot.isLive){
					for( int j = 0;j < this.enemyTanks.size();j++){
						EnemyTank enemyTank = this.enemyTanks.get(j);
						if(enemyTank.isLive){
							hitTank(shot, enemyTank);
						}
					}
				}
			}
			
			//�жϵ���̹���Ƿ�����ҵ�̹��
			for(int i = 0;i < enemyTanks.size();i++){
				EnemyTank enemyTank = enemyTanks.get(i);
				if(enemyTank.isLive){
					for(int j = 0;j < enemyTank.shots.size();j++){
						Shot shot = enemyTank.shots.get(j);
						if(shot.isLive){
							RoleTank roleTank = this.roleTank;
							if(roleTank.isLive){
								hitRoleTank(shot,roleTank);
							}
						}
					}
				}
			}
			
			
			this.repaint();
			
		}
		
		
	}


}
