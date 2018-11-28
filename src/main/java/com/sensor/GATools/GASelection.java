package com.sensor.GATools;

import com.sensor.entity.Chromosome;
import com.sensor.entity.Gene;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DanLongChen on 2018/11/26
 **/
@Component
public class GASelection {//染色体选择（赌轮选择）
    private List<Chromosome> oldList = new ArrayList<Chromosome>();//染色体数组
    private double sum = 0;//适应度总和
    private double gSum = 0;//归一化的sum
    private List<Double> singleRatioList = new ArrayList<Double>();//个体选择概率
    private List<Double> sumRatioList = new ArrayList<Double>();//累积概率
    private boolean flag = false;//表示是否开启精英原则
    private List<Chromosome> newList = new ArrayList<Chromosome>();

    public void startSelection() {
        int bestScore = oldList.get(0).getScore();//得到最好的适应度值
        int best = 0;//最好适应度值的染色体在List中的ID
        for (Chromosome chromosome : oldList) {
            int score = chromosome.getScore();
            sum += score;
            if (score < bestScore) {
                bestScore = score;
                best = oldList.indexOf(chromosome);
            }
        }
        for (int i = 0; i < oldList.size(); i++) {
            gSum += sum / oldList.get(i).getScore();
        }
        for (int i = 0; i < oldList.size(); i++) {//计算个体选择概率和累加概率
            singleRatioList.add((sum / oldList.get(i).getScore()) / gSum);//归一化
            if (i == 0) {
                sumRatioList.add(singleRatioList.get(0));
            } else {
                sumRatioList.add(sumRatioList.get(i - 1) + singleRatioList.get(i));//累加概率数组最后一个是一
            }
        }
        if (flag) {//开启精英原则的时候最好的染色体直接进入下一代
            newList.add(oldList.get(best));
        }
        System.out.println(sumRatioList.toString());
        for (int i = 0; i < oldList.size(); i++) {//进行选择
            double ratio = Math.random();
            for (int j = 0; j < sumRatioList.size(); j++) {
                if (ratio < sumRatioList.get(j)) {
                    newList.add(oldList.get(j));
                    break;
                }
            }
        }

    }

    @Test
    public void test() {//测试模块
        List<Chromosome> list = new ArrayList<Chromosome>();
        for (int i = 0; i < 100; i++) {
            Chromosome temp = new Chromosome(i, 0.09);
            for (int j = 0; j < 3; j++) {
                Gene gene = new Gene(2, 3);
                gene.Init();
                temp.list.add(gene);
            }
            GADecode.getScore(temp);
            list.add(temp);
        }
        GADecode.getAllScore(list);
        GASelection selection = new GASelection();
        selection.setList(list);
        selection.startSelection();
        System.out.println(selection.getNewList().toString());
    }

    public List<Chromosome> getList() {
        return oldList;
    }

    public void setList(List<Chromosome> oldList) {
        this.oldList = oldList;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public List<Double> getSingleRatioList() {
        return singleRatioList;
    }

    public void setSingleRatioList(List<Double> singleRatioList) {
        this.singleRatioList = singleRatioList;
    }

    public List<Double> getSumRatioList() {
        return sumRatioList;
    }

    public void setSumRatioList(List<Double> sumRatioList) {
        this.sumRatioList = sumRatioList;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<Chromosome> getNewList() {
        return newList;
    }

    public void setNewList(List<Chromosome> newList) {
        this.newList = newList;
    }
}
