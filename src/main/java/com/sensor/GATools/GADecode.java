package com.sensor.GATools;

import com.sensor.entity.Chromosome;
import com.sensor.entity.Gene;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;
import org.thymeleaf.standard.processor.StandardInliningCDATASectionProcessor;

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

    /***
     * 计算单条染色体的适应度
     * @param chromosome（染色体）
     */
    public static void setScore(Chromosome chromosome) {
        if (chromosome == null) {
            return;
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
    }

    /***
     * 计算染色体数组的适应度
     * @param list（染色体数组）
     */
    public static void setAllScore(List<Chromosome> list) {
        if (list == null) {
            return;
        }
        List<Integer> integerList = new ArrayList<Integer>(list.size());
        for (Chromosome chromosome : list) {
            setScore(chromosome);
            integerList.add(chromosome.getScore());
        }
    }

    /***
     * 获得染色体数组中的最大适应度值
     * @param list（染色体数组）
     * @return
     */
    public static int getMaxScore(List<Chromosome> list) {
        List<Chromosome> temp = GADeepCopy.deepCopyList(list);//进行深拷贝，避免排序对原染色体数组产生影响
        Collections.sort(temp, new Comparator<Chromosome>() {
            @Override
            public int compare(Chromosome o1, Chromosome o2) {
                return o1.getScore() > o2.getScore() ? 1 : o1.getScore() == o2.getScore() ? 0 : -1;
            }
        });
        return temp.get(0).getScore();
    }

    /**
     * 获取单个染色体的适应度值
     *
     * @param chromosome（单个染色体）
     * @return
     */
    public static int getScore(Chromosome chromosome) {
        return chromosome.getScore();
    }

    /**
     * 获取染色体数组的适应度值和
     *
     * @param list（染色体数组）
     * @return
     */
    public static int getAllScore(List<Chromosome> list) {
        int sum = 0;
        for (Chromosome chromosome : list) {
            sum += chromosome.getScore();
        }
        return sum;
    }
    @Test
    public void test(){
        List<Chromosome> list = new ArrayList<Chromosome>();
        GATestTools.produceData(list,0.2);
        System.out.println(list);
        GADecode.getMaxScore(list);
        System.out.println(list);

    }
}
