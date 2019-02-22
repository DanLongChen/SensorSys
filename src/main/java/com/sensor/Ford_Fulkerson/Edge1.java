package com.sensor.Ford_Fulkerson;

/**
 * Created by Paser on 2019/2/22.
 */
//网络中的边
public class Edge1 {
        private int v1;
        private int v2;
        private int capacity;
        private int flow;

        public Edge1(int v1,int v2,int flow,int capacity){
            this.v1 = v1;
            this.v2 = v2;
            this.capacity = capacity;
            this.flow = flow;
        }

        public int getV1(){
            return v1;
        }

        public int getV2(){
            return v2;
        }

        public int getCapacity(){
            return capacity;
        }

        public int getFlow(){
            return flow;
        }

        public void setFlow(int f){
            flow = f;
        }
    }

