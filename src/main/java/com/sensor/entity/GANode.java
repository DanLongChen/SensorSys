package com.sensor.entity;

import com.sensor.Generate.*;

import java.util.List;

/**
 * Created by DanLongChen on 2018/11/26
 * 网络节点类
 **/
public class GANode extends Nodes {
    private int id; //唯一标示节点
    private String data;//节点的数据
    private Point point;//节点位置信息
    private List<GANode> neibor;//邻居
    private boolean state;//是否损坏

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public List<GANode> getNeibor() {
        return neibor;
    }

    public void setNeibor(List<GANode> neibor) {
        this.neibor = neibor;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
