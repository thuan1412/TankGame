package cn.qingyun.domain;

/**
 * ��Ϸ��Ϣ
 * @author ������
 *
 */
public class Message {

	public  static int enemyTankNums = 20;  //����̹����
	public  static int roleTankNums = 3;    //���̹����
	public  static int hitTankNums = 0;     //�����̹����
	
	//����һ������̹��������һ
	public static void downEnemyTankNums(){
		enemyTankNums--;
	}
	
	//����һ�����̹��������һ
	public static void downRoleTankNums(){
		roleTankNums--;
	}
	
	//����һ������̹�ˣ����̹������1
	public static void addHitTankNumus(){
		hitTankNums++;
	}
	
	public static int getEnemyTankNums() {
		return enemyTankNums;
	}
	public static void setEnemyTankNums(int enemyTankNums) {
		Message.enemyTankNums = enemyTankNums;
	}
	
	public static int getRoleTankNums() {
		return roleTankNums;
	}

	public static void setRoleTankNums(int roleTankNums) {
		Message.roleTankNums = roleTankNums;
	}

	public static int getHitTankNums() {
		return hitTankNums;
	}
	public static void setHitTankNums(int hitTankNums) {
		Message.hitTankNums = hitTankNums;
	}
	
	
}
