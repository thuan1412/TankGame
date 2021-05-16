package cn.qingyun.domain;
import java.util.Vector;

public class TeamTank extends Tank implements Runnable {

    //int spend = 1;
    boolean isStop = true;

    public Vector<Shot> shots = new Vector<Shot>();

    Vector<TeamTank> teamTanks = new Vector<TeamTank>();
    public TeamTank(int x, int y) {
        super(x, y);
    }


    @Override
    public void run() {

    }

    public int getDirect() {
        return direct;
    }

//
//    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
//        this.enemyTanks = enemyTanks;
//    }

     public void setTeamTanks(Vector<TeamTank> teamTanks){
        this.teamTanks = teamTanks;
     }
    public void setDirect(int direct) {
        this.direct = direct;
    }
}
