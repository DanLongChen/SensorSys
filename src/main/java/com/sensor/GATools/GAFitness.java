package com.sensor.GATools;

import com.sensor.entity.Chromosome;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by DanLongChen on 2018/11/26
 **/
@Component
public class GAFitness {
    public static void getSigleFitness(Chromosome chromosome){//单个染色体适应度
        int sum=0;
        if(chromosome==null){
            return;
        }
        GADecode.getScore(chromosome);
    }
    public static int getAllFitness(List<Chromosome> chromosomes){//全部染色体适应度
        int sum=0;
        if(chromosomes==null){
            return sum;
        }
        for(Chromosome chromosome:chromosomes){
            sum+=chromosome.getScore();
        }
        return sum;
    }
}
