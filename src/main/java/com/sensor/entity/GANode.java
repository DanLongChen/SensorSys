package com.sensor.entity;

import com.sensor.Generate.*;

import java.util.List;

/**
 * Created by DanLongChen on 2018/11/26
 **/
public class GANode extends Nodes {
    private int id; //唯一标示节点
    private int data;
    private Point point;  //节点的位置信息
    private NodeStat nodeStat; //节点的接受和发送包的统计信息
    private List<Packets> space; // 存储空间，每个包存储的是节点的id（可为一，可为多）
    private List<SensorNodes> neigh; // 邻居集合
    private int state; // 状态标志位，可为1与0，分别表示正常与损坏
}
