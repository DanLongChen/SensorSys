package com.sensor.entity;

/**
 * Created by Paser on 2019/2/23.
 */
public class MergeNode {
    public int id;
    public int in;
    public int out;

    @Override
    public String toString() {
        return super.toString()+"id: "+id+" in: "+in+" out: "+out+"\n";
    }
}
