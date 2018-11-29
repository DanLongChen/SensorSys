package com.sensor.GATools;

import com.sensor.entity.Chromosome;
import com.sensor.entity.Gene;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DanLongChen on 2018/11/26
 **/
@Component
public class GADecode {//最后输出结果

    /* public static int getScore(Chromosome chromosome) {
         if (chromosome == null) {
             return 0;
         }
         int score=0;
         for (Gene gene:chromosome.getList()) {
             for(Boolean b:gene.getList()){
                 score<<=1;
                 if(b){
                     score+=1;
                 }
             }
         }
         chromosome.setScore(score);
         System.out.println(score);
         return score;
     }*/
    public static int getScore(Chromosome chromosome) {//计算但条染色体适应度
        if (chromosome == null) {
            return 0;
        }
        int score = 0;
        for (Gene gene : chromosome.getList()) {
            for (Boolean b : gene.getList()) {
                if (b) {
                    score += 1;
                } else {
                    score += 0;
                }
            }
        }
        chromosome.setScore(score);
        return score;
    }

    public static void getAllScore(List<Chromosome> list) {
        if (list == null) {
            return;
        }
        List<Integer> integerList = new ArrayList<Integer>(list.size());
        for (Chromosome chromosome : list) {
            getScore(chromosome);
            integerList.add(chromosome.getScore());
        }
//        System.out.println(integerList.toString());

    }
}
