package com.sensor.GATools;

import com.sensor.entity.Chromosome;
import com.sensor.entity.Gene;

import java.util.List;

/**
 * Created by DanLongChen on 2018/11/29
 **/
public class GATestTools {
    /***
     *
     * @param list
     * @param neiborRatio
     *
     * 邻居列表不能重复，也不添加自己，trust值初始化为1，随后按照信赖函数调整
     */
    public static void produceData(List<Chromosome> list,double neiborRatio){
        for (int i = 0; i < 10; i++) {//加载假数据
            Chromosome temp = new Chromosome(i, 0.09);
            for (int j = 0; j < 3; j++) {
                Gene gene = new Gene(2, 3);
                gene.Init();
                temp.list.add(gene);
            }
            GADecode.getScore(temp);

            list.add(temp);
        }
        int num=(int) Math.floor(neiborRatio*list.size());
        for (Chromosome chromosome:list){//填充邻居列表并且添加信赖域
            if(chromosome.getNlist().size()<num){
                while(chromosome.getNlist().size()<num){
                    int neiborNum=(int) Math.floor(Math.random()*list.size());
                    if(chromosome.getNlist().contains(neiborNum) || neiborNum==chromosome.getId()){//已经有的话重新筛选(还有就是不能把自己加入邻居队列)
                        continue;
                    }else{
                        chromosome.getNlist().add(neiborNum);
                        chromosome.getTrust().add(1.0);
                    }
                }
            }
        }
    }
}
