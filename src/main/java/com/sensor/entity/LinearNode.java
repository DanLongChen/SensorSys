package com.sensor.entity;

/**
 * Created by DanLongChen on 2019/3/8
 **/
public class LinearNode {
    private int from;
    private int to;


    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "边的起始点为: "+from+" 边的终点是: "+to;
    }
}
