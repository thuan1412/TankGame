package cn.qingyun.domain;

/**
 * �ӵ���
 * @author ������
 *
 */
public class Shot implements Runnable{
    
	//�ӵ�������
	int x,y;
	//�ӵ��ķ���
	int direct;
	//�ӵ����ٶ�
	int spend = 5;
	//�ж��߳��Ƿ���
	boolean isLive = true;
	
	public Shot(int x, int y,int direct) {
		this.x = x;
		this.y = y;
		this.direct = direct;
	}
	

	//���ݷ���ÿ��50�������λ��
	@Override
	public void run() {
		while(true){
				try {
					Thread.sleep(50);
					switch(this.direct){
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
				
				//�ж��ӵ��Ƿ����
				if(this.x < -2 ||this.x > 400 ||this.y < -2 ||this.y > 300){
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
