package com.sensor.GATools;

import com.sensor.Ford_Fulkerson.GANetwork;
import com.sensor.Ford_Fulkerson.GraphResolve;
import com.sensor.entity.Chromosome;
import com.sensor.entity.Gene;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;
import org.thymeleaf.standard.processor.StandardInliningCDATASectionProcessor;

import java.util.*;
import java.util.zip.CheckedOutputStream;

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
     * 计算单条染色体的适应度(节点个数)
     * @param chromosome（染色体）
     */
    public static void setScore(Chromosome chromosome) {
        if (chromosome == null) {
            return;
        }
        int score = 0;
        int count = 0;//中间计数器
        for (Gene gene : chromosome.getList()) {
            /*for (Boolean b : gene.getList()) {
                if (b) {
                    score += 1;
                } else {
                    score += 0;
                }
            }*/
            for (int i = 0; i <gene.getList().size(); i++) {
                if (gene.getList().get(i) == true) {
                    count++;
                }
                if (count == 2) {
                    score++;//有两个输入连接，就表示需要编码
                    count = 0;
                    break;//跳出这次循环
                }
//                if (i % gene.getGeneIn() == 0) {//计算第二个输出节点
//                    count = 0;
//                    System.out.println("lalal");
//                }
            }
        }
        if (GraphResolve.startresolve(GANetwork.getNetwork(), chromosome)) {//表明不是所有的接收节点都能达到最大流
            score+=10000;
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
     * 获得染色体数组中的最大适应度值（分数最小的）
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
//        temp.clear();
        return temp.get(0).getScore();
    }

    /**
     * 获取染色体的平均分数
     * @param list
     * @return
     */
    public static double getAvgScore(List<Chromosome> list){
        double avg=0;
        int length=list.size();
        int sum=0;
        for (int i=0;i<list.size();i++){
            sum+=list.get(i).getScore();
        }
        avg=sum/length;
        return avg;
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
    public void test() {
        List<Chromosome> list = new ArrayList<Chromosome>();
        GATestTools.produceData(list, 0.2);
        System.out.println(list);
        GADecode.setAllScore(list);
        System.out.println(list);

    }
}
