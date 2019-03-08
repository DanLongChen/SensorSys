package com.sensor.Ford_Fulkerson;

import com.sensor.entity.Chromosome;
import com.sensor.entity.MergeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paser on 2019/2/23.
 */
//图分解算法
public class GraphResolve {
    /**
     * 进行图分解（1：找出可以分解的节点，2：进行分解）
     * @param graph 原始的图
     * @param chromosome    获得的染色体
     * @return
     */
    public static Graph startresolve(Graph graph, Chromosome chromosome){
        List<MergeNode> list=getMergeNode(graph);//获得可能的编码节点（与染色体中的基因相对应）
        return new Graph(10);

    }

    /**
     * 获取可能的编码节点
     * @param graph
     * @return
     */
    public static List<MergeNode> getMergeNode(Graph graph){
        Graph temp=graph.reverseGraph();
        temp.bianli();
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
