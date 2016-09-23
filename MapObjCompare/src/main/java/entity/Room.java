package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeshufeng on 2016/9/23.
 */
public class Room {
    private int id;
    private String roomName;
    private int size;
    private List<Box> boxList;

    public int getId() {
        return id;
    }
    public Room(){
        boxList=new ArrayList<>();
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Box> getBoxList() {
        return boxList;
    }

    public void setBoxList(List<Box> boxList) {
        this.boxList = boxList;
    }
}
