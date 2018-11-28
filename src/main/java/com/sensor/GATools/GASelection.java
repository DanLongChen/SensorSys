package com.sensor.GATools;

import com.sensor.entity.Chromosome;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DanLongChen on 2018/11/26
 **/
@Component
public class GASelection {
    private List<Chromosome> list;//染色体数组
    private double sum = 0;//适应度总和
    private List<Double> singleRatioList;//个体选择概率
    private List<Double> sumRatioList;//累积概率
    private boolean flag;//表示是否开启精英原则
    private List<Chromosome> newList=new ArrayList<Chromosome>();

    public void startSelection() {
        int bestScore=list.get(0).getScore();//得到最好的适应度值
        int best=0;//最好适应度值的染色体的ID
        for(Chromosome chromosome:list){
            int score=chromosome.getScore();
            sum+=score;
            if(score<bestScore){
                bestScore=score;
                best=list.indexOf(chromosome);
            }
        }
        for(int i=0;i<list.size();i++){//计算个体选择概率和累加概率
            singleRatioList.add(list.get(i).getScore()/sum);
            if(i==0){
                sumRatioList.add(singleRatioList.get(0));
            }else{
                sumRatioList.add(sumRatioList.get(i-1)+singleRatioList.get(i));
            }

        }
        if(flag){//开启精英原则的时候最好的染色体直接进入下一代
            newList.add(list.get(best));
        }
        for(int i=0;i<list.size()/2;i++){//进行选择
            double ratio=Math.random();
            for(int j=0;j<sumRatioList.size();j++){
                if(ratio<sumRatioList.get(j)){
                    newList.add(list.get(j));
                    break;
                }
            }
        }

    }

    public List<Chromosome> getList() {
        return list;
    }

    public void setList(List<Chromosome> list) {
        this.list = list;
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
