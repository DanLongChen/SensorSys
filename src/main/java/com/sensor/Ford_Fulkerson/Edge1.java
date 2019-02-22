package com.sensor.Ford_Fulkerson;

/**
 * Created by Paser on 2019/2/22.
 */
//网络中的边
public class Edge1 {
    private int v1 = 0;//边的两个节点
    private int v2 = 0;
    private int capacity = 0;//边的容量
    private int flow = 0;//边中的流

    public Edge1(int v1, int v2, int capacity, int flow) {
        this.v1 = v1;
        this.v2 = v2;
        this.capacity = capacity;
        this.flow = flow;
    }

    public void setV1(int v1) {
        this.v1 = v1;
    }

    public int getV1() {
        return this.v1;
    }

    public void setV2(int v2) {
        this.v2 = v2;
    }

    public int getV2() {
        return this.v2;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public void setFlow(int flow) {
        this.flow = flow;
    }

    public int getFlow() {
        return this.flow;
    }
}
