package com.sensor.Ford_Fulkerson;

import com.sensor.entity.Chromosome;
import com.sensor.entity.MergeNode;
import com.sun.javafx.geom.Edge;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;

import java.util.ArrayList;
import java.util.Collections;
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
        List<MergeNode> list=getMergeNode(graph);//获得可能的编码节点（与染色体中的基因相对应）
        System.out.println(list.toString());
        return null;
    }

    /**
     * 获得标记线图
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
     * 获取可能的编码节点
     * @param graph
     * @return
     */
    public static List<MergeNode> getMergeNode(Graph graph){
        Graph temp=graph.reverseGraph();//获得逆邻接表
        temp.bianli();//遍历节点
        List<MergeNode> list=new ArrayList<MergeNode>();
        for(int i=0;i<temp.getGLists().length;i++){//找到所有可能的编码节点
            if(temp.getGLists()[i].size()>=2){
                MergeNode mergeNode=new MergeNode();
                mergeNode.id=i;
                mergeNode.in=temp.getGLists()[i].size();
                mergeNode.out=graph.getGLists()[i].size();
                list.add(mergeNode);
            }
        }
        return list;
    }
}