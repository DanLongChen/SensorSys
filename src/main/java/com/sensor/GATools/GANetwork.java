package com.sensor.GATools;

import com.sensor.Ford_Fulkerson.Edge1;
import com.sensor.Ford_Fulkerson.Graph;
import org.junit.jupiter.api.Test;

/**
 * Created by Paser on 2019/2/20.
 */
public class GANetwork {

    public void initNetwork(int copyNumber){
        if(copyNumber==3){
            TNetwork();
        }
        if(copyNumber==7){
            SNetwork();
        }
        if(copyNumber==15){
            FNetwork();
        }else{
            TrNetwork();
        }
    }
    public void Onetwork(){//一次复制网络
        Graph graph=new Graph(7);
        Edge1[] edge1=new Edge1[9];
        edge1[0]=new Edge1(0,1,0,1);
        edge1[1]=new Edge1(0,2,0,1);
        edge1[2]=new Edge1(1,3,0,1);
        edge1[3]=new Edge1(2,3,0,1);
        edge1[4]=new Edge1(1,5,0,1);
        edge1[5]=new Edge1(2,6,0,1);
        edge1[6]=new Edge1(4,5,0,1);
        edge1[7]=new Edge1(4,6,0,1);
        edge1[8]=new Edge1(3,4,0,2);
        for(int i=0;i<9;i++){
            graph.insertEdge(edge1[i]);
        }
        graph.bianli();
        graph.MaxFlow();
        graph.showResult();
    }
    public void TNetwork(){//三次复制网络
        Graph graph=new Graph(19);
        Edge1[] edge1=new Edge1[30];
        edge1[0]=new Edge1(0,1,0,1);
        edge1[1]=new Edge1(0,2,0,1);
        edge1[2]=new Edge1(1,3,0,1);
        edge1[3]=new Edge1(2,3,0,1);
        edge1[4]=new Edge1(1,5,0,1);
        edge1[5]=new Edge1(2,6,0,1);
        edge1[6]=new Edge1(4,5,0,1);
        edge1[7]=new Edge1(4,6,0,1);
        edge1[8]=new Edge1(3,4,0,1);
        edge1[9]=new Edge1(3,4,0,1);
        edge1[10]=new Edge1(5,7,0,1);
        edge1[11]=new Edge1(5,8,0,1);
        edge1[12]=new Edge1(7,11,0,1);
        edge1[13]=new Edge1(8,11,0,1);
        edge1[14]=new Edge1(7,15,0,1);
        edge1[15]=new Edge1(11,13,0,1);
        edge1[16]=new Edge1(11,13,0,1);
        edge1[17]=new Edge1(8,16,0,1);
        edge1[18]=new Edge1(13,15,0,1);
        edge1[19]=new Edge1(13,16,0,1);
        edge1[20]=new Edge1(6,9,0,1);
        edge1[21]=new Edge1(6,10,0,1);
        edge1[22]=new Edge1(9,12,0,1);
        edge1[23]=new Edge1(10,12,0,1);
        edge1[24]=new Edge1(9,17,0,1);
        edge1[25]=new Edge1(12,14,0,1);
        edge1[26]=new Edge1(12,14,0,1);
        edge1[27]=new Edge1(10,18,0,1);
        edge1[28]=new Edge1(14,17,0,1);
        edge1[29]=new Edge1(14,18,0,1);
        for(int i=0;i<30;i++){
            graph.insertEdge(edge1[i]);
        }
        graph.MaxFlow();
        graph.showResult();


    }
    public void SNetwork(){//7次复制网络

    }
    public void FNetwork(){//15次复制网络

    }
    public void TrNetwork(){//31次复制网络

    }

    public static void main(String[] args) {
        new GANetwork().Onetwork();
    }
}
