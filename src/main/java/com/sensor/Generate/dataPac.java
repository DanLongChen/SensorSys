package com.sensor.Generate;

import java.util.List;

public class dataPac {
    private int id;
    private double x;
    private double y;
    private double nsend_;
    private double nrecv_;
    private int state;
    private int sendId;
    private int revId;
    private Packets data;
    private int type;
    private int radius;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getNsend_() {
        return nsend_;
    }

    public void setNsend_(double nsend_) {
        this.nsend_ = nsend_;
    }

    public double getNrecv_() {
        return nrecv_;
    }

    public void setNrecv_(double nrecv_) {
        this.nrecv_ = nrecv_;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getSendId() {
        return sendId;
    }

    public void setSendId(int sendId) {
        this.sendId = sendId;
    }

    public int getRevId() {
        return revId;
    }

    public void setRevId(int revId) {
        this.revId = revId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Packets getData() {
        return data;
    }

    public void setData(Packets data) {
        this.data = data;
    }

}
