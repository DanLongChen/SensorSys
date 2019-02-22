package com.sensor.Ford_Fulkerson;

import java.util.*;
/**
 * Created by Paser on 2019/2/22.
 */
//残存网络
public class Gf {

    private int vNum;
    private int eNum;
    private LinkedList<Edge2>[] GLists;

    public Gf(int n){
        vNum = n;
        eNum = 0;
        GLists = new LinkedList[n];

        for(int i = 0;i<n;i++)
            GLists[i] = new LinkedList<>();
    }

    public void insertEdge(Edge2 e){
        int v1 = e.getV1();
        GLists[v1].add(e);
        eNum++;
    }

    /**
     * 返回一条增广路径
     * @return
     */
    public LinkedList<Integer> augmentingPath(){

        LinkedList<Integer> list = new LinkedList<>();
        Queue<Integer> queue = new LinkedList<>();
        int[] reached = new int[vNum];
        int[] preNode = new int[vNum];
        for(int i = 0;i<vNum;i++){
            reached[i] = 0;
            preNode[i] = -1;
        }
        preNode[0] = -1;

        reached[0] = 1;
        queue.add(0);
        while(!queue.isEmpty()){//没有循环起来
            int now = queue.poll();

            LinkedList<Edge2> inlist = (LinkedList<Edge2>) GLists[now].clone();

            while(!inlist.isEmpty()){

                Edge2 e = inlist.pop();
                int v2 = e.getV2();

                if(reached[v2]==0){
                    queue.add(v2);
                    reached[v2] = 1;
                    preNode[v2] = now;
                }
            }
        }

        for(int i = 0;i<vNum;i++){
            System.out.println(reached[i]+"     "+preNode[i]);
        }

        if(reached[vNum-1]==0){
            //System.out.println("here");
            return list;

        }

        int pointnum = vNum-1;
        while(pointnum!=-1){
            list.add(0, pointnum);
            pointnum = preNode[pointnum];
        }

        return list;
    }

    /**
     * 根据增广路径得到需要调整的值
     * @param list
     * @return
     */
    public int changeNum(LinkedList<Integer> list){
        if(list.equals(null))
            return 0;
        int minchange = 1000;
        int v1 = 0;
        for(int i = 1;i<list.size();i++){
            int v2 = list.get(i);
            LinkedList<Edge2> elist = (LinkedList<Edge2>) GLists[v1].clone();
            Edge2 edge = elist.pop();
            while(edge.getV2()!=v2){
                edge = elist.pop();
            }
            if(minchange>edge.getFlow())
                minchange = edge.getFlow();

            v1 = v2;
        }

        return minchange;
    }

    public void bianli(){
        System.out.println("残存网络 共 "+vNum+" 个顶点， "+eNum+" 条边");
        for(int i = 0;i<vNum;i++){
            if(GLists[i].size()==0){
                System.out.println(i+"没有后继");
                continue;
            }
            for(int j = 0;j<GLists[i].size();j++){
                Edge2 e = GLists[i].get(j);
                System.out.println("[ "+e.getV1()+" , "+e.getV2()+" , "+e.getFlow()+" ]");
            }
        }
    }
}


