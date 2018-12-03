package com.sensor.entity;

import com.sensor.Generate.Packets;

import java.util.List;

/**
 * Created by DanLongChen on 2018/12/3
 * 数据包类
 **/
public class GAPackets extends Packets {
    private String data;//数据包中包含的额数据
    private List<Integer> list;//包含哪些节点（这个数据包经过了哪些节点）

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }
}
