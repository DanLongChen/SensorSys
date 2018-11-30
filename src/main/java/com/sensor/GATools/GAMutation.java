package com.sensor.GATools;

import com.sensor.entity.Chromosome;
import com.sensor.entity.Gene;

import java.util.List;

/**
 * Created by DanLongChen on 2018/11/27
 **/
public class GAMutation {
    public static void doMutation(List<Chromosome> list, boolean flag,double Md,int generation,int generationMax){//染色体数组，整体变异率条件是否满足，整体染色体的变异率，当前代数，总代数
        for(Chromosome chromosome:list){//对染色体中的每一条染色体执行变异，然后调整其变异率
            double ratio=Math.random();
            if(ratio<=chromosome.getRatio()){
                int geneListPoint=(int)Math.floor(chromosome.getList().size()*Math.random());//寻找变异的基因位置
                Gene gene=chromosome.getList().get(geneListPoint);
                int genePoint=(int)Math.floor(gene.getList().size()*Math.random());//寻找基因中需要变异的基因位
                boolean temp=gene.getList().get(genePoint);
                if(temp){
                    gene.getList().set(genePoint,false);
                }else{
                    gene.getList().set(genePoint,true);
                }
            }
            chromosome.setRatio(Md+0.02*(1-chromosome.getScore()/GADecode.getMaxScore(list))+0.01*1/generation);//按照公式变更变异率

        }
    }

}
