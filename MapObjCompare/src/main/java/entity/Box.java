package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeshufeng on 2016/9/23.
 */
public class Box {
    private int id;
    private String boxName;
    private int weight;
    private List<Ball> ballList;
    public int getId() {
        return id;
    }
    public Box(){
        ballList=new ArrayList<>();
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getBoxName() {
        return boxName;
    }

    public void setBoxName(String boxName) {
        this.boxName = boxName;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<Ball> getBallList() {
        return ballList;
    }

    public void setBallList(List<Ball> ballList) {
        this.ballList = ballList;
    }
}
