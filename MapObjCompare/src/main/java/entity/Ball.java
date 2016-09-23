package entity;

/**
 * Created by yeshufeng on 2016/9/23.
 */
public class Ball {
    private int ballNum;
    private int weight;
    private String ballName;

    private int ballFlag;

    public int getBallNum() {
        return ballNum;
    }

    public void setBallNum(int ballNum) {
        this.ballNum = ballNum;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getBallName() {
        return ballName;
    }

    public void setBallName(String ballName) {
        this.ballName = ballName;
    }

    public int getBallFlag() {
        return ballFlag;
    }

    public void setBallFlag(int ballFlag) {
        this.ballFlag = ballFlag;
    }
}
