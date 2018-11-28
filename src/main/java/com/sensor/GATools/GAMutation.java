package com.sensor.GATools;

import com.sensor.entity.Chromosome;
import com.sensor.entity.Gene;

import java.util.List;

/**
 * Created by DanLongChen on 2018/11/27
 **/
public class GAMutation {
    public static void doMutation(List<Chromosome> list, double mutationRatio){
        double ratio=Math.random();
        if(ratio<mutationRatio){
            int listPoint=(int)Math.floor(list.size()*Math.random());//寻找编译的染色体位置
            Chromosome chromosome=list.get(listPoint);
            int geneListPoint=(int)Math.floor(chromosome.getList().size()*Math.random());//寻找变异的基因位置
            Gene gene=chromosome.getList().get(geneListPoint);
            int genePoint=(int)Math.floor(gene.getList().size()*Math.random());//寻找基因中需要变异的基因位
            boolean temp=gene.getList().get(genePoint);
            if(temp){
                gene.getList().set(genePoint,false);
            }else{
                gene.getList().set(genePoint,true);
            }
        }else{
            return;
        }
    }

}
