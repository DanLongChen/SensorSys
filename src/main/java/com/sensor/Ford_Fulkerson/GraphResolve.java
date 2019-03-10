package com.sensor.Ford_Fulkerson;

import com.sensor.entity.Chromosome;
import com.sensor.entity.Gene;
import com.sensor.entity.MergeNode;
import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.CommonOps;

import javax.security.auth.Subject;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Paser on 2019/2/23.
 */
//图分解算法
public class GraphResolve {
    /**
     * 转化为线性标图(1、首先将点图转换为线图，2、找出线图中能够编码的点，3、将编码的点作为染色体传入，然后再进行遗传操作，4、得出结果进行验证)
     *
     * @param graph      原始的图
     * @param chromosome 获得的染色体
     * @return
     */

    /**
     * 首先获取网络所需的速率，网络边的数目，计算出A，B，F，若计算出的行列式不为0，那么表明是可行的
     */
    public static boolean startresolve(Graph graph,Chromosome chromosome) {
        List<MergeNode> list = getMergeNode(changeToLinearGraph(graph), false);//获得可能的编码节点（与染色体中的基因相对应）
        List<Integer> linearNode=new ArrayList<Integer>();
        for(Gene gene:chromosome.getList()){//获取基因代表的节点，也就是可能编码的节点
            linearNode.add(gene.getId());
        }

        Graph linearGraph = changeToLinearGraph(graph);//获取标记线图
        Graph reverseLinearGraph = reverseGraph(linearGraph);//获取标记线图的逆邻接表
//        reverseLinearGraph.bianli();
        int numE = linearGraph.getGLists().length;//获取边的数量
        DenseMatrix64F[] A = getGraphA(graph, 2, numE);
        DenseMatrix64F[] B = getGraphB(graph, 2, numE);
        DenseMatrix64F F = new DenseMatrix64F(numE, numE);
        int bitNum=0;
        for (int row = 0; row < numE; row++) {
            if (reverseLinearGraph.getGLists()[row].size()>=2 ) {
                for(int i=1;i<reverseLinearGraph.getGLists()[row].size();i++){//查找逆标记线图的每一行的节点
                    bitNum=0;
                    for (int col = 0; col < numE; col++) {//找出每一行节点对应的下标
                        if(reverseLinearGraph.getGLists()[row].get(i).getV1()==reverseLinearGraph.getGLists()[col].get(0).getV1()
                                && reverseLinearGraph.getGLists()[row].get(i).getV2()==reverseLinearGraph.getGLists()[col].get(0).getV2()
                                ){
                            if(linearNode.contains(row)){//表示是合并节点
                                for (Gene gene:chromosome.getList()){
                                    if(gene.getId()==row){
//                                        F.set(row, col, gene.getList().get(++bitNum)==true?1:0);
                                        F.set(row, col, gene.getList().get(bitNum++)==true?(Math.random()+0.1)*100:0);
                                    }
                                }
                            }else{
                                F.set(row, col, (Math.random()+0.1)*100);
                            }
                        }
                    }
                }
            }else{
                continue;
            }
        }
        DenseMatrix64F I= CommonOps.identity(numE,numE);//设置单位矩阵
        DenseMatrix64F sub=new DenseMatrix64F(numE,numE);//(I-F)的结果
//        System.out.println("sub: ");
        CommonOps.sub(I,F, sub);
//        System.out.println(sub);
        DenseMatrix64F inverse=new DenseMatrix64F(numE,numE);
        CommonOps.invert(sub,inverse);//(I-F)-1  求逆的结果
//        System.out.println("inverse: ");
//        System.out.println(inverse);

//        System.out.println("Test: ");
        DenseMatrix64F test=new DenseMatrix64F(numE,numE);
        CommonOps.mult(sub,inverse,test);
//        System.out.println(CommonOps.det(test));
        /**
         * 以上是矩阵公共部分
         */
        DenseMatrix64F trans=new DenseMatrix64F(numE, 2);
        CommonOps.transpose(B[0],trans);//求转置
//        System.out.println("trans: ");
//        System.out.println(trans);

        DenseMatrix64F temp=new DenseMatrix64F(2,numE);
        CommonOps.mult(A[0],inverse,temp);
        DenseMatrix64F result=new DenseMatrix64F(2,2);
        CommonOps.mult(temp,trans,result);
//        System.out.println("result: ");
//        System.out.println(result);

//        System.out.println(F.toString());
        double viable=CommonOps.det(result);
//        System.out.println(viable);
//        System.out.println("The judge is: "+(viable==0));
        return viable==0;
    }

    /**
     * 获取网络的数据矩阵A
     *
     * @param graph 节点网络
     * @param R     所需的速率
     * @param E     边的数目
     * @return
     */
    public static DenseMatrix64F[] getGraphA(Graph graph, int R, int E) {
        /**
         * 首先寻找入度为0的节点，这些节点是源点（首先查找节点的逆邻接表）
         */
        Graph reverseGraph = graph.reverseGraph();//获取逆邻接表
        LinkedList<Edge1>[] reverseList = reverseGraph.getGLists();
        List<Integer> nodeList = new ArrayList<Integer>();
        for (int i = 0; i < reverseList.length; i++) {
            if (reverseList[i].size() == 0) {
                nodeList.add(i);
            }
        }
        DenseMatrix64F[] result = new DenseMatrix64F[nodeList.size()];
        LinkedList<Edge1>[] list = changeToLinearGraph(graph).getGLists();
        for (int i = 0; i < nodeList.size(); i++) {
            int from = nodeList.get(i);
            result[i] = new DenseMatrix64F(R, E);
            for (int row = 0; row < R; row++) {
                for (int j = 0; j < list.length; j++) {
                    if (list[j].get(0).getV1() == from) {
                        result[i].set(row, j, (Math.random()+0.1)*100);
                    } else {
                        result[i].set(row, j, 0);
                    }
                }
            }
        }
        return result;
    }

    /**
     * 获取网络的数据矩阵数组B[]
     *
     * @param graph 节点网络
     * @param R     需要达到的速率
     * @param E     网络中边的数量
     * @return
     */
    public static DenseMatrix64F[] getGraphB(Graph graph, int R, int E) {
        //获取没有出度的节点（Edge1），也就是接收节点
        int length = graph.getGLists().length;
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < length; i++) {
            if (graph.getGLists()[i].size() == 0) {
                list.add(i);//首先找到出度为0的节点（点图）
            }
        }
        DenseMatrix64F[] result = new DenseMatrix64F[list.size()];//创建有d（接受节点）的矩阵数组，然后对这中的每一个矩阵进行验证，来确保所得的染色体是可行的
        LinkedList<Edge1>[] linearList = changeToLinearGraph(graph).getGLists();//获取点图对应的标记线图
        /**
         * 首先需要找到汇点，然后在标记线图中找到V2是汇点的边，然后求出矩阵
         */
        for (int i = 0; i < list.size(); i++) {
            result[i] = new DenseMatrix64F(R, E);
            int to = list.get(i);//获取汇点的id
            for (int r = 0; r < R; r++) {
                for (int j = 0; j < linearList.length; j++) {
                    if (linearList[j].get(0).getV2() == to) {
                        result[i].set(r, j, (Math.random()+0.1)*100);
                    } else {
                        result[i].set(r, j, 0);
                    }

                }
            }
        }
        return result;
    }

    /**
     * 获得标记线图(获取边的邻接表)
     *
     * @param graph
     * @return
     */
    public static Graph changeToLinearGraph(Graph graph) {
        LinkedList<Edge1>[] list = graph.getGLists();
        int length = list.length;
        int e = 0;//获取网络的边数
        for (int i = 0; i < length; i++) {
            if (list[i] == null) {
                continue;
            }
            e += list[i].size();
        }
        LinkedList<Edge1>[] linearList = new LinkedList[e];
        int flag = 0;
        for (int i = 0; i < length; i++) {
            if (list[i] == null) {
                continue;
            }
            for (int j = 0; j < list[i].size(); j++) {
                if (flag <= e) {
                    linearList[flag++] = new LinkedList<Edge1>();
                    linearList[flag - 1].add(new Edge1(list[i].get(j).getV1(), list[i].get(j).getV2(), list[i].get(j).getFlow(), list[i].get(j).getCapacity()));
                }
            }
        }
        for (int i = 0; i < e; i++) {
            if (linearList[i] != null) {
                int V2 = linearList[i].get(0).getV2();
                for (int j = 0; j < list[V2].size(); j++) {
                    linearList[i].add(new Edge1(list[V2].get(j).getV1(), list[V2].get(j).getV2(), list[V2].get(j).getFlow(), list[V2].get(j).getCapacity()));
                }
            }
        }
        Graph newGraph = new Graph(e);
        newGraph.setGLists(linearList);
        return newGraph;
    }

    /**
     * 获取边邻接表的逆邻接表
     *
     * @return
     */
    public static Graph reverseGraph(Graph graph) {
        LinkedList<Edge1>[] list = graph.getGLists();
        int length = list.length;
        LinkedList<Edge1>[] reverseList = new LinkedList[length];
        for (int i = 0; i < length; i++) {
            reverseList[i] = new LinkedList<Edge1>();

            if (list[i] == null) {
                continue;
            }
            reverseList[i].add(new Edge1(list[i].get(0).getV1(), list[i].get(0).getV2(), list[i].get(0).getFlow(), list[i].get(0).getCapacity()));
        }
        for (int i = 0; i < length; i++) {
            Edge1 temp = new Edge1(list[i].get(0).getV1(), list[i].get(0).getV2(), list[i].get(0).getFlow(), list[i].get(0).getCapacity());
            for (int j = 1; j < list[i].size(); j++) {
                int V1 = list[i].get(j).getV1();
                int V2 = list[i].get(j).getV2();
                for (int k = 0; k < reverseList.length; k++) {//循环找新表中有这个入度的节点
                    int rV1 = reverseList[k].get(0).getV1();
                    int rV2 = reverseList[k].get(0).getV2();
                    if (V1 == rV1 && V2 == rV2) {
                        reverseList[k].add(temp);
                    } else {
                        continue;
                    }
                }
            }
        }
        Graph result = new Graph(length);
        result.setGLists(reverseList);
        return result;
    }

    /**
     * 获取可能的编码节点(对于点图来说)
     *
     * @param graph(传入点图《不需要逆邻接表》，或者线图《不需要邻接表》) flag(true的时候是对点网络寻找编码节点，false的时候是对边网络寻找编码节点)
     * @return
     */
    public static List<MergeNode> getMergeNode(Graph graph, boolean flag) {
        int mergeNum = 0;
        List<MergeNode> list = new ArrayList<MergeNode>();
        if (flag) {
            Graph temp = null;
            temp = graph.reverseGraph();//获得逆邻接表
//            temp.bianli();//遍历节点
            mergeNum = 2;
            for (int i = 0; i < temp.getGLists().length; i++) {//找到所有可能的编码节点
                if (temp.getGLists()[i].size() >= mergeNum) {
                    MergeNode mergeNode = new MergeNode();
                    mergeNode.id = i;
                    mergeNode.in = temp.getGLists()[i].size();
                    mergeNode.out = graph.getGLists()[i].size();
                    list.add(mergeNode);
                }
            }
        } else {
            mergeNum = 3;
            graph=reverseGraph(graph);
            for (int i = 0; i < graph.getGLists().length; i++) {//找到所有可能的编码节点
                if (graph.getGLists()[i].size() >= mergeNum) {
                    MergeNode mergeNode = new MergeNode();
                    mergeNode.id = i;
                    mergeNode.in = graph.getGLists()[i].size() - 1;
                    mergeNode.out = 1;
                    list.add(mergeNode);
                }
            }
        }

        return list;
    }
}
