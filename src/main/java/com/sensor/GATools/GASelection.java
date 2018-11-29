package com.sensor.GATools;

import com.sensor.entity.Chromosome;
import com.sensor.entity.Gene;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by DanLongChen on 2018/11/26
 **/
@Component
public class GASelection {//染色体选择（赌轮选择）
    private List<Chromosome> oldList = new ArrayList<Chromosome>();//染色体数组
    private List<Chromosome> newList = new ArrayList<Chromosome>();
    private double sum = 0;//适应度总和
    private double gSum = 0;//归一化的sum
    private List<Double> singleRatioList = new ArrayList<Double>();//个体选择概率
    private List<Double> sumRatioList = new ArrayList<Double>();//累积概率
    private boolean flag = false;//表示是否开启精英原则
    private double ratio = 0.0;//锦标赛选择的时候随机选择的个体所占的比例

    /**
     * 赌轮选择法
     */
    public void duSelection() {
        int num = oldList.size();
        int bestScore = oldList.get(0).getScore();//得到最好的适应度值
        int best = 0;//最好适应度值的染色体在list中的下标
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
//            System.out.println(oldList.get(best));
            oldList.remove(best);//同时最优染色体被禁止进入赌轮选择
            sumRatioList.remove(best);
            num -= 1;
        }
//        System.out.println(sumRatioList.toString());
        for (int i = 0; i < num; i++) {//进行选择
            double ratio = Math.random();
            for (int j = 0; j < sumRatioList.size(); j++) {
                if (ratio < sumRatioList.get(j)) {
                    newList.add(oldList.get(j));
                    break;
                }
            }
        }

    }

    /**
     * 锦标赛选择法
     */
    public void jinSelection() {
        int num = oldList.size();
        int bestScore = oldList.get(0).getScore();//得到最好的适应度值
        int best = 0;//最好适应度值的染色体的ID
        for (Chromosome chromosome : oldList) {
            int score = chromosome.getScore();
            if (score < bestScore) {
                bestScore = score;
                best = chromosome.getId();
            }
        }
        if (flag) {
            newList.add(oldList.get(GAFindChromosomeById.findById(oldList, best)));
            oldList.remove(oldList.get(GAFindChromosomeById.findById(oldList, best)));
//            System.out.println(newList.toString());
            num -= 1;
        }
        List<Chromosome> temp = new ArrayList<Chromosome>();
        int chooseNum = (int) Math.floor(num * ratio);//要选择的n个数
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < chooseNum; j++) {
                int get = (int) Math.floor(Math.random() * oldList.size());//随机选择个体
                temp.add(oldList.get(get));
            }
            Collections.sort(temp, new Comparator<Chromosome>() {
                @Override
                public int compare(Chromosome o1, Chromosome o2) {
                    return o1.getScore()>o2.getScore()?1:o1.getScore()==o2.getScore()?0:-1;
                }
            });
            newList.add(temp.get(0));
            temp.clear();
        }
    }
    public void neiborCare(List<Chromosome> list,double neiborRatio){
        int num=(int) Math.floor(list.size()*neiborRatio);
        for(Chromosome chromosome:list){
            for (int i:chromosome.getNlist()){
                if(GAFindChromosomeById.findById(list,i)==Integer.MAX_VALUE){//说明要找的linjuID没在对应的列表里面，那么需要移除
                    chromosome.getNlist().remove(i);
                }else{
                    continue;
                }
            }
        }
    }

    @Test
    public void test() {//测试模块
        List<Chromosome> list = new ArrayList<Chromosome>();
        GATestTools.produceData(list);
        GADecode.getAllScore(list);
        GASelection selection = new GASelection();
        selection.setList(list);
        selection.setRatio(0.2);
        selection.setFlag(true);
        selection.jinSelection();
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

    public List<Chromosome> getOldList() {
        return oldList;
    }

    public void setOldList(List<Chromosome> oldList) {
        this.oldList = oldList;
    }

    public double getgSum() {
        return gSum;
    }

    public void setgSum(double gSum) {
        this.gSum = gSum;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }
}
