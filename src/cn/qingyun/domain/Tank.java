package cn.qingyun.domain;

import javax.swing.JPanel;

/**
 * ̹�����û��趨̹�˵�λ�� 
 * @author ������
 *
 */
public class Tank  {
	
	//̹�˳���λ��
	int x; 
	int y;
	
	//����̹�˵��ٶ�
	int speed = 3;
	
	//����̹���ƶ��ķ��� 0:��  1����  2����  3����
	int direct = 0;
	
	//����̹�˵���ɫ
	int Color = 0;
	
	public Tank(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	//���Ʒ���
	public void moveUp(){
		y -= speed;
	}
	
	//���Ʒ���
	public void moveDown(){
		y += speed;
	}
	
	//���Ʒ���
	public void moveLeft(){
		x -= speed;
	}
	
	//���Ʒ���
	public void moveRight(){
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
