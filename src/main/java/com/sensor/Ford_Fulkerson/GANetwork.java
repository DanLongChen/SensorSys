package com.sensor.Ford_Fulkerson;

/**
 * Created by Paser on 2019/2/20.
 */
public class GANetwork {
    public static Graph getNetwork(){
//        return Onetwork();
        return initNetwork(15);
//        return test();
    }
    public static Graph test(){
        Graph graph=new Graph(5);
        Edge1[] edge1=new Edge1[8];
        edge1[0]=new Edge1(0,1,0,1);
        edge1[1]=new Edge1(0,2,0,1);
        edge1[2]=new Edge1(0,4,0,1);
        edge1[3]=new Edge1(1,2,0,1);
        edge1[4]=new Edge1(1,3,0,1);
        edge1[5]=new Edge1(2,3,0,1);
        edge1[6]=new Edge1(2,4,0,1);
        edge1[7]=new Edge1(3,4,0,1);
        for(int i=0;i<8;i++){
            graph.insertEdge(edge1[i]);
        }
//        graph.bianli();
        return graph;
    }
    public static Graph initNetwork(int copyNumber){
        if(copyNumber==3){
            return TNetwork();
        }
        if(copyNumber==7){
            return SNetwork();
        }
        if(copyNumber==15){
            return FNetwork();
        }
        return null;
    }
    public static Graph Onetwork(){//一次复制网络
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
        edge1[8]=new Edge1(3,4,0,1);
        for(int i=0;i<9;i++){
            graph.insertEdge(edge1[i]);
        }

        return graph;
    }
    public static Graph TNetwork(){//三次复制网络
        Graph graph=new Graph(19);
        Edge1[] edge1=new Edge1[27];
        edge1[0]=new Edge1(0,1,0,1);
        edge1[1]=new Edge1(0,2,0,1);
        edge1[2]=new Edge1(1,3,0,1);
        edge1[3]=new Edge1(2,3,0,1);
        edge1[4]=new Edge1(1,5,0,1);
        edge1[5]=new Edge1(3,4,0,1);
        edge1[6]=new Edge1(2,6,0,1);
        edge1[7]=new Edge1(4,5,0,1);
        edge1[8]=new Edge1(4,6,0,1);

        edge1[9]=new Edge1(5,7,0,1);
        edge1[10]=new Edge1(5,8,0,1);
        edge1[11]=new Edge1(7,11,0,1);
        edge1[12]=new Edge1(8,11,0,1);
        edge1[13]=new Edge1(7,15,0,1);
        edge1[14]=new Edge1(11,13,0,1);
        edge1[15]=new Edge1(8,16,0,1);
        edge1[16]=new Edge1(13,15,0,1);
        edge1[17]=new Edge1(13,16,0,1);

        edge1[18]=new Edge1(6,9,0,1);
        edge1[19]=new Edge1(6,10,0,1);
        edge1[20]=new Edge1(9,12,0,1);
        edge1[21]=new Edge1(10,12,0,1);
        edge1[22]=new Edge1(9,17,0,1);
        edge1[23]=new Edge1(12,14,0,1);
        edge1[24]=new Edge1(10,18,0,1);
        edge1[25]=new Edge1(14,17,0,1);
        edge1[26]=new Edge1(14,18,0,1);





//        edge1[5]=new Edge1(2,6,0,1);
//        edge1[6]=new Edge1(4,5,0,1);
//        edge1[7]=new Edge1(4,6,0,1);
//        edge1[8]=new Edge1(3,4,0,1);
//        edge1[10]=new Edge1(5,7,0,1);
//        edge1[11]=new Edge1(5,8,0,1);
//        edge1[12]=new Edge1(7,11,0,1);
//        edge1[13]=new Edge1(8,11,0,1);
//        edge1[14]=new Edge1(7,15,0,1);
//        edge1[15]=new Edge1(11,13,0,1);
//        edge1[16]=new Edge1(11,13,0,1);
//        edge1[17]=new Edge1(8,16,0,1);
//        edge1[18]=new Edge1(13,15,0,1);
//        edge1[19]=new Edge1(13,16,0,1);
//        edge1[20]=new Edge1(6,9,0,1);
//        edge1[21]=new Edge1(6,10,0,1);
//        edge1[22]=new Edge1(9,12,0,1);
//        edge1[23]=new Edge1(10,12,0,1);
//        edge1[24]=new Edge1(9,17,0,1);
//        edge1[25]=new Edge1(12,14,0,1);
//        edge1[26]=new Edge1(12,14,0,1);
//        edge1[27]=new Edge1(10,18,0,1);
//        edge1[28]=new Edge1(14,17,0,1);
//        edge1[29]=new Edge1(14,18,0,1);
        for(int i=0;i<27;i++){
            graph.insertEdge(edge1[i]);
        }
//        graph.bianli();
        return graph;
    }
    public static Graph SNetwork(){//7次复制网络
        Graph graph=new Graph(43);
        Edge1[] edge1=new Edge1[63];
        edge1[0]=new Edge1(0,1,0,1);
        edge1[1]=new Edge1(0,2,0,1);
        edge1[2]=new Edge1(1,3,0,1);
        edge1[3]=new Edge1(2,3,0,1);
        edge1[4]=new Edge1(1,5,0,1);
        edge1[5]=new Edge1(3,4,0,1);
        edge1[6]=new Edge1(2,6,0,1);
        edge1[7]=new Edge1(4,5,0,1);
        edge1[8]=new Edge1(4,6,0,1);

        edge1[9]=new Edge1(5,7,0,1);
        edge1[10]=new Edge1(5,8,0,1);
        edge1[11]=new Edge1(7,11,0,1);
        edge1[12]=new Edge1(8,11,0,1);
        edge1[13]=new Edge1(7,15,0,1);
        edge1[14]=new Edge1(11,13,0,1);
        edge1[15]=new Edge1(8,16,0,1);
        edge1[16]=new Edge1(13,15,0,1);
        edge1[17]=new Edge1(13,16,0,1);

        edge1[18]=new Edge1(6,9,0,1);
        edge1[19]=new Edge1(6,10,0,1);
        edge1[20]=new Edge1(9,12,0,1);
        edge1[21]=new Edge1(10,12,0,1);
        edge1[22]=new Edge1(9,17,0,1);
        edge1[23]=new Edge1(12,14,0,1);
        edge1[24]=new Edge1(10,18,0,1);
        edge1[25]=new Edge1(14,17,0,1);
        edge1[26]=new Edge1(14,18,0,1);

        edge1[27]=new Edge1(15,19,0,1);
        edge1[28]=new Edge1(15,20,0,1);
        edge1[29]=new Edge1(19,27,0,1);
        edge1[30]=new Edge1(20,27,0,1);
        edge1[31]=new Edge1(19,35,0,1);
        edge1[32]=new Edge1(27,31,0,1);
        edge1[33]=new Edge1(20,36,0,1);
        edge1[34]=new Edge1(31,35,0,1);
        edge1[35]=new Edge1(31,36,0,1);

        edge1[36]=new Edge1(16,21,0,1);
        edge1[37]=new Edge1(16,22,0,1);
        edge1[38]=new Edge1(21,28,0,1);
        edge1[39]=new Edge1(22,28,0,1);
        edge1[40]=new Edge1(21,37,0,1);
        edge1[41]=new Edge1(28,32,0,1);
        edge1[42]=new Edge1(22,38,0,1);
        edge1[43]=new Edge1(32,37,0,1);
        edge1[44]=new Edge1(32,38,0,1);

        edge1[45]=new Edge1(17,23,0,1);
        edge1[46]=new Edge1(17,24,0,1);
        edge1[47]=new Edge1(23,29,0,1);
        edge1[48]=new Edge1(24,29,0,1);
        edge1[49]=new Edge1(23,39,0,1);
        edge1[50]=new Edge1(29,33,0,1);
        edge1[51]=new Edge1(24,40,0,1);
        edge1[52]=new Edge1(33,39,0,1);
        edge1[53]=new Edge1(33,40,0,1);

        edge1[54]=new Edge1(18,25,0,1);
        edge1[55]=new Edge1(18,26,0,1);
        edge1[56]=new Edge1(25,30,0,1);
        edge1[57]=new Edge1(26,30,0,1);
        edge1[58]=new Edge1(25,41,0,1);
        edge1[59]=new Edge1(30,34,0,1);
        edge1[60]=new Edge1(26,42,0,1);
        edge1[61]=new Edge1(34,41,0,1);
        edge1[62]=new Edge1(34,42,0,1);
        for(int i=0;i<63;i++){
            graph.insertEdge(edge1[i]);
        }
        return graph;


    }
    public static Graph FNetwork(){//15次复制网络
        Graph graph=new Graph(91);
        Edge1[] edge1=new Edge1[135];
        edge1[0]=new Edge1(0,1,0,1);
        edge1[1]=new Edge1(0,2,0,1);
        edge1[2]=new Edge1(1,3,0,1);
        edge1[3]=new Edge1(2,3,0,1);
        edge1[4]=new Edge1(1,5,0,1);
        edge1[5]=new Edge1(3,4,0,1);
        edge1[6]=new Edge1(2,6,0,1);
        edge1[7]=new Edge1(4,5,0,1);
        edge1[8]=new Edge1(4,6,0,1);

        edge1[9]=new Edge1(5,7,0,1);
        edge1[10]=new Edge1(5,8,0,1);
        edge1[11]=new Edge1(7,11,0,1);
        edge1[12]=new Edge1(8,11,0,1);
        edge1[13]=new Edge1(7,15,0,1);
        edge1[14]=new Edge1(11,13,0,1);
        edge1[15]=new Edge1(8,16,0,1);
        edge1[16]=new Edge1(13,15,0,1);
        edge1[17]=new Edge1(13,16,0,1);

        edge1[18]=new Edge1(6,9,0,1);
        edge1[19]=new Edge1(6,10,0,1);
        edge1[20]=new Edge1(9,12,0,1);
        edge1[21]=new Edge1(10,12,0,1);
        edge1[22]=new Edge1(9,17,0,1);
        edge1[23]=new Edge1(12,14,0,1);
        edge1[24]=new Edge1(10,18,0,1);
        edge1[25]=new Edge1(14,17,0,1);
        edge1[26]=new Edge1(14,18,0,1);

        edge1[27]=new Edge1(15,19,0,1);
        edge1[28]=new Edge1(15,20,0,1);
        edge1[29]=new Edge1(19,27,0,1);
        edge1[30]=new Edge1(20,27,0,1);
        edge1[31]=new Edge1(19,35,0,1);
        edge1[32]=new Edge1(27,31,0,1);
        edge1[33]=new Edge1(20,36,0,1);
        edge1[34]=new Edge1(31,35,0,1);
        edge1[35]=new Edge1(31,36,0,1);

        edge1[36]=new Edge1(16,21,0,1);
        edge1[37]=new Edge1(16,22,0,1);
        edge1[38]=new Edge1(21,28,0,1);
        edge1[39]=new Edge1(22,28,0,1);
        edge1[40]=new Edge1(21,37,0,1);
        edge1[41]=new Edge1(28,32,0,1);
        edge1[42]=new Edge1(22,38,0,1);
        edge1[43]=new Edge1(32,37,0,1);
        edge1[44]=new Edge1(32,38,0,1);

        edge1[45]=new Edge1(17,23,0,1);
        edge1[46]=new Edge1(17,24,0,1);
        edge1[47]=new Edge1(23,29,0,1);
        edge1[48]=new Edge1(24,29,0,1);
        edge1[49]=new Edge1(23,39,0,1);
        edge1[50]=new Edge1(29,33,0,1);
        edge1[51]=new Edge1(24,40,0,1);
        edge1[52]=new Edge1(33,39,0,1);
        edge1[53]=new Edge1(33,40,0,1);

        edge1[54]=new Edge1(18,25,0,1);
        edge1[55]=new Edge1(18,26,0,1);
        edge1[56]=new Edge1(25,30,0,1);
        edge1[57]=new Edge1(26,30,0,1);
        edge1[58]=new Edge1(25,41,0,1);
        edge1[59]=new Edge1(30,34,0,1);
        edge1[60]=new Edge1(26,42,0,1);
        edge1[61]=new Edge1(34,41,0,1);
        edge1[62]=new Edge1(34,42,0,1);

        edge1[63]=new Edge1(35,43,0,1);
        edge1[64]=new Edge1(35,44,0,1);
        edge1[65]=new Edge1(43,59,0,1);
        edge1[66]=new Edge1(44,59,0,1);
        edge1[67]=new Edge1(43,75,0,1);
        edge1[68]=new Edge1(59,67,0,1);
        edge1[69]=new Edge1(44,76,0,1);
        edge1[70]=new Edge1(67,75,0,1);
        edge1[71]=new Edge1(67,76,0,1);

        edge1[72]=new Edge1(36,45,0,1);
        edge1[73]=new Edge1(36,46,0,1);
        edge1[74]=new Edge1(45,60,0,1);
        edge1[75]=new Edge1(46,60,0,1);
        edge1[76]=new Edge1(45,77,0,1);
        edge1[77]=new Edge1(60,68,0,1);
        edge1[78]=new Edge1(46,78,0,1);
        edge1[79]=new Edge1(68,77,0,1);
        edge1[80]=new Edge1(68,78,0,1);

        edge1[81]=new Edge1(37,47,0,1);
        edge1[82]=new Edge1(37,48,0,1);
        edge1[83]=new Edge1(47,61,0,1);
        edge1[84]=new Edge1(48,61,0,1);
        edge1[85]=new Edge1(47,79,0,1);
        edge1[86]=new Edge1(61,69,0,1);
        edge1[87]=new Edge1(48,80,0,1);
        edge1[88]=new Edge1(69,79,0,1);
        edge1[89]=new Edge1(69,80,0,1);

        edge1[90]=new Edge1(38,49,0,1);
        edge1[91]=new Edge1(38,50,0,1);
        edge1[92]=new Edge1(49,62,0,1);
        edge1[93]=new Edge1(50,62,0,1);
        edge1[94]=new Edge1(49,81,0,1);
        edge1[95]=new Edge1(62,70,0,1);
        edge1[96]=new Edge1(50,82,0,1);
        edge1[97]=new Edge1(70,81,0,1);
        edge1[98]=new Edge1(70,82,0,1);

        edge1[99]=new Edge1(39,51,0,1);
        edge1[100]=new Edge1(39,52,0,1);
        edge1[101]=new Edge1(51,63,0,1);
        edge1[102]=new Edge1(52,63,0,1);
        edge1[103]=new Edge1(51,83,0,1);
        edge1[104]=new Edge1(63,71,0,1);
        edge1[105]=new Edge1(52,84,0,1);
        edge1[106]=new Edge1(71,83,0,1);
        edge1[107]=new Edge1(71,84,0,1);

        edge1[108]=new Edge1(40,53,0,1);
        edge1[109]=new Edge1(40,54,0,1);
        edge1[110]=new Edge1(53,64,0,1);
        edge1[111]=new Edge1(54,64,0,1);
        edge1[112]=new Edge1(53,85,0,1);
        edge1[113]=new Edge1(64,72,0,1);
        edge1[114]=new Edge1(54,86,0,1);
        edge1[115]=new Edge1(72,85,0,1);
        edge1[116]=new Edge1(72,86,0,1);

        edge1[117]=new Edge1(41,55,0,1);
        edge1[118]=new Edge1(41,56,0,1);
        edge1[119]=new Edge1(55,65,0,1);
        edge1[120]=new Edge1(56,65,0,1);
        edge1[121]=new Edge1(55,87,0,1);
        edge1[122]=new Edge1(65,73,0,1);
        edge1[123]=new Edge1(56,88,0,1);
        edge1[124]=new Edge1(73,87,0,1);
        edge1[125]=new Edge1(73,88,0,1);

        edge1[126]=new Edge1(42,57,0,1);
        edge1[127]=new Edge1(42,58,0,1);
        edge1[128]=new Edge1(57,66,0,1);
        edge1[129]=new Edge1(58,66,0,1);
        edge1[130]=new Edge1(57,89,0,1);
        edge1[131]=new Edge1(66,74,0,1);
        edge1[132]=new Edge1(58,90,0,1);
        edge1[133]=new Edge1(74,89,0,1);
        edge1[134]=new Edge1(74,90,0,1);
        for(int i=0;i<135;i++){
            graph.insertEdge(edge1[i]);
        }
        return graph;




    }
    public void TrNetwork(){//31次复制网络

    }

}
