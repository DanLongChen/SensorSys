package com.sensor.Ford_Fulkerson;

/**
 * Created by Paser on 2019/2/22.
 */

public class TestMain {

    public static void main(String[] args){
        test3();
    }

    private static void test(){
        Graph graph = new Graph(6);
        Edge1[] edges = new Edge1[9];

        edges[0] = new Edge1(0,1,0,16);
        edges[1] = new Edge1(0,2,0,13);
        edges[2] = new Edge1(1,3,0,12);
        edges[3] = new Edge1(2,1,0,4);
        edges[4] = new Edge1(2,4,0,14);
        edges[5] = new Edge1(3,2,0,9);
        edges[6] = new Edge1(3,5,0,20);
        edges[7] = new Edge1(4,3,0,7);
        edges[8] = new Edge1(4,5,0,4);
//        edges[0]=new Edge1(0,1,0,40);
//        edges[1]=new Edge1(0,3,0,20);
//        edges[2]=new Edge1(1,3,0,20);
//        edges[3]=new Edge1(1,2,0,30);
//        edges[4]=new Edge1(2,3,0,10);

        for(int i = 0;i<9;i++)
            graph.insertEdge(edges[i]);

        graph.MaxFlow();
        graph.showResult();
    }

    public static void test2(){
        Graph graph = new Graph(6);

        Edge1[] edges = new Edge1[9];
        edges[0] = new Edge1(0,1,4,16);
        edges[1] = new Edge1(0,2,0,13);
        edges[2] = new Edge1(1,3,4,12);
        edges[3] = new Edge1(2,1,0,4);
        edges[4] = new Edge1(2,4,4,14);
        edges[5] = new Edge1(3,2,4,9);
        edges[6] = new Edge1(3,5,0,20);
        edges[7] = new Edge1(4,3,0,7);
        edges[8] = new Edge1(4,5,4,4);

        for(int i = 0;i<9;i++)
            graph.insertEdge(edges[i]);

//        graph.bianli();

        graph.MaxFlow();
        graph.showResult();
    }
    public static void test3(){
        Graph graph = new Graph(6);
        Edge1[] edges = new Edge1[9];

        edges[0] = new Edge1(0,1,0,16);
        edges[1] = new Edge1(0,2,0,13);
        edges[2] = new Edge1(1,3,0,12);
        edges[3] = new Edge1(2,1,0,4);
        edges[4] = new Edge1(2,4,0,14);
        edges[5] = new Edge1(3,2,0,9);
        edges[6] = new Edge1(3,5,0,20);
        edges[7] = new Edge1(4,3,0,7);
        edges[8] = new Edge1(4,5,0,4);
        for(int i = 0;i<9;i++)
            graph.insertEdge(edges[i]);
//        graph.reverseGraph().bianli();
    }
}


