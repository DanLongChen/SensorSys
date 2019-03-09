package com.sensor.Ford_Fulkerson;

import com.sensor.entity.Chromosome;
import com.sensor.entity.MergeNode;
import com.sun.corba.se.impl.orbutil.DenseIntMapImpl;
import org.ejml.data.DenseMatrix64F;

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
     * @param graph 原始的图
     * @param chromosome    获得的染色体
     * @return
     */
    public static Graph startresolve(Graph graph, Chromosome chromosome){
        List<MergeNode> list=getMergeNode(graph,false);//获得可能的编码节点（与染色体中的基因相对应）
        Graph linearGraph=changeToLinearGraph(graph);
        Graph reverseLinearGraph=reverseGraph(linearGraph);
        int numE=reverseLinearGraph.getGLists().length;

        System.out.println(list.toString());
        return null;
    }

    /**
     * 获取网络的数据矩阵A
     * @param graph 节点网络
     * @param R     所需的速率
     * @param E     边的数目
     * @return
     */
    public static DenseMatrix64F getGraphA(Graph graph,int R,int E){
        DenseMatrix64F result=new DenseMatrix64F(R,E);
        LinkedList<Edge1>[] list = graph.getGLists();
        int col=0;
        for(int row=0;row<R;row++){
            col=0;
            for(int j=0;j<list.length;j++){
                for(int k=0;k<list[j].size();k++){
                    if(list[j].get(k).getV1()==0){
                        result.set(row,col++,1);
                    }else{
                        result.set(row,col++,0);
                    }
                }
            }
        }
        return result;
    }

    /**
     * 获取网络的数据矩阵数组B[]
     * @param graph
     * @param R
     * @param E
     * @return
     */
    public static DenseMatrix64F[] getGraphB(Graph graph,int R,int E){
        //首先获取点网络的逆邻接矩阵，获得接收节点
        DenseMatrix64F[] result=new DenseMatrix64F[9];
        return result;
    }

    /**
     * 获得标记线图(获取边的邻接表)
     * @param graph
     * @return
     */
    public static Graph changeToLinearGraph(Graph graph){
        LinkedList<Edge1>[] list = graph.getGLists();
        int length=list.length;
        int e=0;//获取网络的边数
        for(int i=0;i<length;i++){
            if(list[i]==null){
                continue;
            }
            e+=list[i].size();
        }
        LinkedList<Edge1>[] linearList=new LinkedList[e];
        System.out.println(e);
        int flag=0;
        for(int i=0;i<length;i++){
            if(list[i]==null){
                continue;
            }
            for(int j=0;j<list[i].size();j++){
                if(flag<=e){
                    linearList[flag++]=new LinkedList<Edge1>();
                    linearList[flag-1].add(new Edge1(list[i].get(j).getV1(),list[i].get(j).getV2(),list[i].get(j).getFlow(),list[i].get(j).getCapacity()));
                }
            }
        }
        for(int i=0;i<e;i++){
            if(linearList[i]!=null){
                int V2=linearList[i].get(0).getV2();
                for(int j=0;j<list[V2].size();j++){
                    linearList[i].add(new Edge1(list[V2].get(j).getV1(),list[V2].get(j).getV2(),list[V2].get(j).getFlow(),list[V2].get(j).getCapacity()));
                }
            }
        }
        System.out.println(linearList);
        Graph newGraph=new Graph(e);
        newGraph.setGLists(linearList);
        return newGraph;
    }

    /**
     * 获取边邻接表的逆邻接表
     * @return
     */
    public static Graph reverseGraph(Graph graph){
        LinkedList<Edge1>[] list = graph.getGLists();
        int length=list.length;
        LinkedList<Edge1>[] reverseList=new LinkedList[length];
        for(int i=0;i<length;i++){
            reverseList[i]=new LinkedList<Edge1>();

            if(list[i]==null){
                continue;
            }
            reverseList[i].add(new Edge1(list[i].get(0).getV1(),list[i].get(0).getV2(),list[i].get(0).getFlow(),list[i].get(0).getCapacity()));
        }
        for(int i=0;i<length;i++){
            Edge1 temp=new Edge1(list[i].get(0).getV1(),list[i].get(0).getV2(),list[i].get(0).getFlow(),list[i].get(0).getCapacity());
            for(int j=1;j<list[i].size();j++){
                int V1=list[i].get(j).getV1();
                int V2=list[i].get(j).getV2();
                for(int k=0;k<reverseList.length;k++){//循环找新表中有这个入度的节点
                    int rV1=reverseList[k].get(0).getV1();
                    int rV2=reverseList[k].get(0).getV2();
                    if(V1==rV1 && V2==rV2){
                        reverseList[k].add(temp);
                    }else{
                        continue;
                    }
                }
            }
        }
        Graph result=new Graph(length);
        result.setGLists(reverseList);
        return result;
    }

    /**
     * 获取可能的编码节点(对于点图来说)
     * @param graph flag(true的时候是对点网络寻找编码节点，false的时候是对边网络寻找编码节点)
     * @return
     */
    public static List<MergeNode> getMergeNode(Graph graph,boolean flag){
        int mergeNum=0;
        List<MergeNode> list=new ArrayList<MergeNode>();
        if(flag){
            Graph temp = null;
            temp=graph.reverseGraph();//获得逆邻接表
            temp.bianli();//遍历节点
            mergeNum=2;
            for(int i=0;i<temp.getGLists().length;i++){//找到所有可能的编码节点
                if(temp.getGLists()[i].size()>=mergeNum){
                    MergeNode mergeNode=new MergeNode();
                    mergeNode.id=i;
                    mergeNode.in=temp.getGLists()[i].size();
                    mergeNode.out=graph.getGLists()[i].size();
                    list.add(mergeNode);
                }
            }
        }else{
            mergeNum=3;
            for(int i=0;i<graph.getGLists().length;i++){//找到所有可能的编码节点
                if(graph.getGLists()[i].size()>=mergeNum){
                    MergeNode mergeNode=new MergeNode();
                    mergeNode.id=i;
                    mergeNode.in=graph.getGLists()[i].size()-1;
                    mergeNode.out=1;
                    list.add(mergeNode);
                }
            }
        }

        return list;
    }
}
