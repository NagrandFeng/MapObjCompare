package mapObj;

import entity.Ball;
import entity.Box;
import entity.Room;

import java.util.*;

/**
 * @Author  yeshufeng
 * @nickName 大肥猫
 * @date 2016/9/23
 */

/**
 * 背景:现在有两间屋子，里面各有若干盒子(盒子标记了号码和名字),盒子中有若干小球(每个小球都标注了名字),
 * 现在需要一个算法,找出两间屋中，是否有相同的盒子，不同的盒子需要标注记号
 * and 盒子里面是否有相同名字的小球，不同名字的需要标注记号
 * 也就是比较两间屋子盒子以及盒子内小球的差异
 */
public class MapCompareObject {

    public static final Integer ONLY_ROOM1=-1;
    public static final Integer ONLY_ROOM2=0;
    public static final Integer BOTH=1;

    public static void main(String[] args) {
        Room room1=createRoomData();
        System.out.println("room1------------");
        printBox(roomToMapObj(room1));
        System.out.println("------------");
        Room room2=createRoomData();
        System.out.println("room2------------");
        printBox(roomToMapObj(room2));
        System.out.println("------------");
        Map<String,List<Ball>> result=setBallFlag(roomToMapObj(room1),roomToMapObj(room2));
        print(1,result);
        print(0,result);
        print(-1,result);

    }
    public static void print(int flag,Map<String,List<Ball>> data){
        switch (flag){
            case -1:
                System.out.println("room1没有,room2有的");
                printByFlag(-1,data);
                break;
            case 0:
                System.out.println("room1有,room2没有的");
                printByFlag(0,data);
                break;
            case 1:
                System.out.println("room1和room2都有的");
                printByFlag(1,data);
                break;
        }
    }
    public static void printByFlag(int flag,Map<String,List<Ball>> data){
        for (Map.Entry<String, List<Ball>> entry : data.entrySet()) {
            String key = entry.getKey();
            List<Ball> values = entry.getValue();
            for (Ball value : values) {
                if(value.getBallFlag()==flag){
                    System.out.println("\t"+key+"----"+value.getBallName());
                }
            }
        }
    }
    public static void printBox(Map<String,List<Ball>> data){
        for (Map.Entry<String, List<Ball>> entry : data.entrySet()) {
            String key = entry.getKey();
            List<Ball> values = entry.getValue();
            System.out.println(key+":");
            for (Ball value : values) {
                System.out.println("----"+value.getBallName());
            }
        }
    }


    /**
     * 比较两个Map中的数据差异性
     *
     * @param ballMapRoom1    来自nsqd的Map数据,格式为key[topicName]-value[channelName]
     * @param ballMapRoom2 来自DB的map数据,格式为 key[topicName]-value[DmsChannel]
     * @return 已经设置好标致的dmsChannelMap数据集合
     */
    public static Map<String, List<Ball>> setBallFlag(Map<String, List<Ball>> ballMapRoom1, Map<String, List<Ball>> ballMapRoom2) {

        // map中的数据转成String字符串存入SET,字符串组装格式为 box:ball
        Set<String> room1Balls = new HashSet<>();
        for (Map.Entry<String, List<Ball>> entry : ballMapRoom1.entrySet()) {
            String key = entry.getKey();
            List<Ball> values = entry.getValue();
            for (Ball value : values) {
                String boxAndBall = key + ":" + value.getBallName();
                room1Balls.add(boxAndBall);
            }
        }

        Set<String> room2Balls = new HashSet<>();
        for (Map.Entry<String, List<Ball>> entry : ballMapRoom2.entrySet()) {
            String key = entry.getKey();
            List<Ball> values = entry.getValue();
            for (Ball value : values) {
                value.setBallFlag(1);// 默认设置为1
                String boxAndball = key + ":" + value.getBallName();
                room2Balls.add(boxAndball);
            }
        }

        Set<String> result = new HashSet<>();

        result.clear();
        result.addAll(room1Balls);
        result.removeAll(room2Balls);

        if (result.size() != 0) {
            for (String str : result) {
                String[] tempStr = str.split(":");
                // 字符串组装格式为box:ball
                // index=0,取topicName
                String boxName = tempStr[0];
                // index=1,取channelName
                String ballName = tempStr[1];
                // room1的盒子里有,room2的盒子里没有
                if (ballMapRoom2.containsKey(boxName)) {
                    // 如果room2中是存在此盒子的,则直接往这个盒子下的list添加数据(小球数据)
                    Ball ball=new Ball();
                    ball.setBallName(ballName);
                    ball.setBallFlag(0);
                    ballMapRoom2.get(boxName).add(ball);
                } else {
                    // 如果room2中不存在此盒子,则重新创建好后添加入map里
                    Ball ball=new Ball();
                    ball.setBallFlag(0);
                    ball.setBallName(ballName);
                    List<Ball> box=new ArrayList<>();
                    box.add(ball);
                    ballMapRoom2.put(boxName,box);
                }
            }
        }

        result.clear();
        result.addAll(room2Balls);
        result.removeAll(room1Balls);
        if (result.size() != 0) {
            for (String str : result) {
                String[] tempStr = str.split(":");
                String boxName = tempStr[0];
                String ballName = tempStr[1];
                // room2有,room1没有
                List<Ball> box=ballMapRoom2.get(boxName);
                for (Ball ball : box) {
                    if (ballName.equals(ball.getBallName())) {
                        ball.setBallFlag(-1);
                    }
                }
            }
        }

        return ballMapRoom2;
    }

    public static Room createRoomData(){
        //造房子
        Room room=new Room();
        //造盒子-若干盒子(1-4个盒子)
        int boxNum=getRandomBetween(4,8);
        for (int i=0;i<boxNum;i++){
            Box box=new Box();
            box.setBoxName("box"+i);
            //造球-若干球(1-3个球，随机)
            int ballNum=getRandomBetween(3,9);
            for (int j = 0; j < ballNum; j++) {
                Ball ball=new Ball();
                ball.setBallName("ball"+j);
                box.getBallList().add(ball);
            }
            room.getBoxList().add(box);
        }
        return room;
    }

    //取1-5的随机数
    public static int getRandomBetween(int min,int max){
        Random random=new Random();
        return  random.nextInt(max)%(max-min+1) + min;
    }
    public static Map<String, List<Ball>> roomToMapObj(Room room){
        Map<String, List<Ball>> ballMapRoomData=new HashMap<>();
        List<Box> boxList=room.getBoxList();
        for (Box box : boxList) {
            String key=box.getBoxName();
            List<Ball> ballList=box.getBallList();
            ballMapRoomData.put(key,ballList);
        }
        return ballMapRoomData;
    }
}
