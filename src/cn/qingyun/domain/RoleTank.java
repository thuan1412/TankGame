package cn.qingyun.domain;

import java.util.Vector;


/**
 * ����̹��ʵ��
 * @author ������
 *
 */
public class RoleTank extends Tank{

	//ʵ���ӵ���
	Shot shot = null;
	//����ӵ��ļ���
	Vector<Shot> shots = new Vector<Shot>();
	//�ж�̹���Ƿ���
	boolean isLive = true;
	//�ж��Ƿ���ͣ
	boolean isStop = true;
	
	//ʵ�ָ���
	public RoleTank(int x, int y) {
		super(x, y);
		
	}
    
	//�����ӵ�����
	public void shotRole(){
		//���ݷ����ж��ӵ���λ��
		switch(this.getDirect()){
		case 0:
			//����һ���ӵ�
			shot = new Shot(this.getX()+8,this.getY()-10,this.getDirect());
			//��ӵ�������
			shots.add(shot);
			break;
		case 1:
			shot = new Shot(this.getX()+10,this.getY()+32,this.getDirect());
			shots.add(shot);
			break;
		case 2:
			shot = new Shot(this.getX()-10,this.getY()+12,this.getDirect());
			shots.add(shot);
			break;
		case 3:
			shot = new Shot(this.getX()+30,this.getY()+12,this.getDirect());
			shots.add(shot);
			break;
		}
		//�����ӵ��߳�
		Thread t = new Thread(shot);
		t.start();
		
	}
}
