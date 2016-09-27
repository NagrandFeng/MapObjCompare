package mapObj;

import com.google.common.collect.Sets;
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

    public static final Integer ONLY_ROOM2=-1;
    public static final Integer ONLY_ROOM1=0;
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
        setAllFlagEqualTo(1,roomToMapObj(room2));
        Map<String,List<Ball>> result=setBallFlag(roomToMapObj(room1),roomToMapObj(room2));
        System.out.println("-----使用Set集合去重复-------");
        print(ONLY_ROOM1,result);
        print(BOTH,result);
        print(ONLY_ROOM2,result);
        setAllFlagEqualTo(1,roomToMapObj(room2));
        Map<String,List<Ball>> result2=setBallFlagByGoogleSets(roomToMapObj(room1),roomToMapObj(room2));
        System.out.println("-----使用google Sets.difference方法------");
        print(ONLY_ROOM1,result2);
        print(BOTH,result2);
        print(ONLY_ROOM2,result2);

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
            values.stream().filter(value -> value.getBallFlag() == flag).forEach(value -> {
                System.out.println("\t" + key + "----" + value.getBallName());
            });
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
     * @param ballMapRoom1    room1
     * @param ballMapRoom2    room2
     * @return 已经设置好Ball标识的map数据集合
     */
    public static Map<String, List<Ball>> setBallFlag(Map<String, List<Ball>> ballMapRoom1, Map<String, List<Ball>> ballMapRoom2) {

        // map中的数据转成String字符串存入SET,字符串组装格式为 box:ball
        Set<String> room1Balls = ballMapRoomToSet(ballMapRoom1);


        //默认设置为1
        setAllFlagEqualTo(1,ballMapRoom2);
        Set<String> room2Balls=ballMapRoomToSet(ballMapRoom2);



        Set<String> result = new HashSet<>();

        //Set集合去重复操作
        result.clear();
        result.addAll(room1Balls);
        result.removeAll(room2Balls);

        if (result.size() != 0) {
            for (String boxAndBall : result) {
                setFlagEqualToZero(ballMapRoom2,boxAndBall);
            }
        }

        result.clear();
        result.addAll(room2Balls);
        result.removeAll(room1Balls);
        if (result.size() != 0) {
            for (String boxAndBall : result) {
                setFlagEqualToFu1(ballMapRoom2,boxAndBall);
            }
        }

        return ballMapRoom2;
    }

    /**
     * 另一种实现方式,同样的思路
     * @param ballMapRoom1 房间1
     * @param ballMapRoom2 房间2
     * @return Map 类型的数据
     */
    public static Map<String, List<Ball>> setBallFlagByGoogleSets(Map<String, List<Ball>> ballMapRoom1, Map<String, List<Ball>> ballMapRoom2) {
        // map中的数据转成String字符串存入SET,字符串组装格式为 box:ball
        Set<String> room1Balls = ballMapRoomToSet(ballMapRoom1);

        //默认设置为1
        setAllFlagEqualTo(1,ballMapRoom2);
        Set<String> room2Balls=ballMapRoomToSet(ballMapRoom2);

        Set<String> onlyRoom1=Sets.difference(room1Balls,room2Balls);
        Set<String> onlyRoom2=Sets.difference(room2Balls,room1Balls);

        for (String boxAndBall : onlyRoom1) {
            setFlagEqualToZero(ballMapRoom2,boxAndBall);
        }
        for (String boxAndBall:onlyRoom2) {
            setFlagEqualToFu1(ballMapRoom2,boxAndBall);
        }
        return ballMapRoom2;
    }



    public static Room createRoomData(){
        //造房子
        Room room=new Room();
        //造盒子-若干盒子(1-4个盒子，随机)
        int boxNum=getRandomBetween(1,4);
        for (int i=0;i<boxNum;i++){
            Box box=new Box();
            box.setBoxName("box"+i);
            //造球-若干球(1-3个球，随机)
            int ballNum=getRandomBetween(1,4);
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

    /**
     * 将Room对象转换成Map型的盒子-球列表数据
     * @param room
     * @return
     */
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

    /**
     * 将Map型的盒子-球列表数据转换成 元素为字符串类型，格式为boxName:ballName的Set集合
     * @param ballMapRoom Map型的盒子-球列表
     * @return 元素为字符串类型，格式为boxName:ballName的Set集合
     */
    public static Set<String> ballMapRoomToSet(Map<String,List<Ball>> ballMapRoom){
        Set<String> setBallMap= Sets.newHashSet();
        for (Map.Entry<String, List<Ball>> entry : ballMapRoom.entrySet()) {
            String key = entry.getKey();
            List<Ball> values = entry.getValue();
            for (Ball value : values) {
                String boxAndBall = key + ":" + value.getBallName();
                setBallMap.add(boxAndBall);
            }
        }
        return setBallMap;
    }

    /**
     * 设置小球的状态
     * @param flag 需要设置的状态
     * @param ballMapRoom 盒子-球 的集合
     */
    public static Map<String,List<Ball>> setAllFlagEqualTo(int flag,Map<String,List<Ball>> ballMapRoom){
        for (Map.Entry<String, List<Ball>> entry : ballMapRoom.entrySet()) {
            List<Ball> values = entry.getValue();
            for (Ball value : values) {
                value.setBallFlag(flag);
            }
        }
        return ballMapRoom;
    }

    /**
     * 设置小球的状态为0
     * @param ballMapRoom 盒子-球集合
     * @param boxAndBall 指定的盒子和小球名称组合的字符串
     */
    public static Map<String,List<Ball>> setFlagEqualToZero(Map<String,List<Ball>> ballMapRoom,String boxAndBall){
        String[] temp=boxAndBall.split(":");
        String boxName=temp[0];
        String ballName=temp[1];
        if (ballMapRoom.containsKey(boxName)) {
            // 如果room2中是存在此盒子的,则直接往这个盒子下的list添加数据(小球数据)
            Ball ball=new Ball();
            ball.setBallName(ballName);
            ball.setBallFlag(0);
            ballMapRoom.get(boxName).add(ball);
        } else {
            // 如果room2中不存在此盒子,则重新创建好后添加入map里
            Ball ball=new Ball();
            ball.setBallFlag(0);
            ball.setBallName(ballName);
            List<Ball> box=new ArrayList<>();
            box.add(ball);
            ballMapRoom.put(boxName,box);
        }
        return ballMapRoom;
    }

    /**
     * 设置小球的状态为1
     * @param ballMapRoom 盒子-球集合
     * @param boxAndBall 指定的盒子和小球名称组合的字符串
     * @return
     */
    public static Map<String,List<Ball>> setFlagEqualToFu1(Map<String,List<Ball>> ballMapRoom,String boxAndBall){
        String[] temp=boxAndBall.split(":");
        String boxName=temp[0];
        String ballName=temp[1];
        for (Ball ball : ballMapRoom.get(boxName)) {
            if (ballName.equals(ball.getBallName())) {
                ball.setBallFlag(-1);
            }
        }
        return ballMapRoom;
    }
}
