# MapObjCompare简介
##故事背景
 现在存在两间屋子，里面各有若干盒子(每个盒子标记了名字并且每个盒子名称不重复),盒子中有若干小球(盒子下每个小球都标注了名字，并且同一个
 盒子下小球名称不重复)。
 两间屋子的盒子可能重名，盒子内的小球也可能重名
 
 现在需要一个算法,比较两间屋子盒子以及盒子内小球的差异
 换种说法，也就是找出两间屋中，是否有相同的盒子，盒子里面相同名字的小球，不同小球名字的需要标注记号
 也就是找出这些小球中
    a)在房间1里但不在房间2里的
    b)不在房间1里但在房间2里的
    c)房间1和房间2都有的

##备注
这是我在工作中遇到的一个问题，然后转换成这种抽象的，想找出一种方法来解决类似的问题

在我的代码里，这里只是提供一种解决此问题的思路,欢迎大大们指点


##footer
created by 大肥猫 
datetime in 2016-9-23 18:01:56


 
