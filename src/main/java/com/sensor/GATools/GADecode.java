package com.sensor.GATools;

import com.sensor.entity.Chromosome;
import com.sensor.entity.Gene;
import org.springframework.stereotype.Component;

import java.util.*;

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

    public static void getAllScore(List<Chromosome> list) {//计算染色体数组中的每一条染色体的适应度值
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
    public static int getMaxScore(List<Chromosome> list){//获取染色体数组中的最大的适应度
        List<Chromosome> temp=GADeepCopy.deepCopyList(list);
        Collections.sort(temp, new Comparator<Chromosome>() {
            @Override
            public int compare(Chromosome o1, Chromosome o2) {
                return o1.getScore()>o2.getScore()?-1:o1.getScore()==o2.getScore()?0:1;
            }
        });
        return temp.get(0).getScore();
    }
}
