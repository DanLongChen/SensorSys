package com.sensor.GATools;

import com.sensor.entity.Chromosome;

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
    //    private List<Chromosome> newList = new ArrayList<Chromosome>();
    private double sum = 0;//适应度总和
    private double gSum = 0;//归一化的sum
    private List<Double> singleRatioList = new ArrayList<Double>();//个体选择概率
    private List<Double> sumRatioList = new ArrayList<Double>();//累积概率
    private boolean flag = false;//表示是否开启精英原则
    private double ratio = 0.3;//锦标赛选择的时候随机选择的个体所占的比例
    private double neiborRatio=0.3;//邻居队列长度（这里设置默认值为0.2，也可以自己传入来设置）

    /**
     * 赌轮选择法
     */
    public void duSelection(List<Chromosome> oldList) {//需设置新旧染色体数组，是否开启精英原则
        sum=0;//重新清空
        gSum=0;
        List<Chromosome> newList = new ArrayList<Chromosome>();
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
            singleRatioList.add((sum / oldList.get(i).getScore()) / gSum);//归一化(个体选择概率)
            if (i == 0) {
                sumRatioList.add(singleRatioList.get(0));
            } else {
                sumRatioList.add(sumRatioList.get(i - 1) + singleRatioList.get(i));//累加概率数组最后一个是一
            }
        }
        if (flag) {//开启精英原则的时候最好的染色体直接进入下一代
            newList.add(oldList.get(best).deepClone());
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
                    newList.add(oldList.get(j).deepClone());
                    break;
                }
            }
        }

        singleRatioList.clear();//清空列表
        sumRatioList.clear();

        oldList.clear();//清空传入的那个地址的内容
        oldList.addAll(newList);//再加入新内容（保证原地址的内存中有数据）

    }

    /**
     * 锦标赛选择法
     */
    public void jinSelection(List<Chromosome> oldList) {
        List<Chromosome> newList = new ArrayList<Chromosome>();
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
            newList.add(oldList.get(GAFindChromosomeById.findById(oldList, best)).deepClone());
            oldList.remove(oldList.get(GAFindChromosomeById.findById(oldList, best)));
//            System.out.println(newList.toString());
            num -= 1;
        }
        List<Chromosome> temp = new ArrayList<Chromosome>();
        int chooseNum = (int) Math.round(num * ratio);//要选择的n个数
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < chooseNum; j++) {
                int get = (int) Math.floor(Math.random() * oldList.size());//随机选择个体
                temp.add(oldList.get(get));
            }
            Collections.sort(temp, new Comparator<Chromosome>() {//使用sort排序的话原list会乱掉，所以使用了一个中间list来执行
                @Override
                public int compare(Chromosome o1, Chromosome o2) {
                    return o1.getScore() > o2.getScore() ? 1 : o1.getScore() == o2.getScore() ? 0 : -1;
                }
            });
//            System.out.println("temp数组："+temp);
            newList.add(temp.get(0).deepClone());//需要进行深拷贝（浅拷贝的话会出现两个相同的对象同时出现在一个染色体数组的情况）
            temp.clear();
        }
        oldList.clear();
        oldList.addAll(newList);
    }

    /**
     * 更新邻居列表
     *
     * @param list
     * @param neiborRatio
     */
    public void neiborCare(List<Chromosome> list, double neiborRatio) {//调整染色体的邻居队列和信赖值（在染色体选择结束之后进行）
        int num = (int) Math.floor(list.size() * neiborRatio);
        int number = 0;
        for (Chromosome chromosome : list) {
            for (int i=0;i<chromosome.getNlist().size();i++) {//首先查看邻居队列中的染色体还能不能找到（不能找到则剔除）
                if (GAFindChromosomeById.findById(list, chromosome.getNlist().get(i)) == Integer.MAX_VALUE) {//说明要找的邻居ID没在对应的列表里面，那么需要移除
//                    System.out.println("移除邻居的染色体："+chromosome);
                    chromosome.getNlist().remove(i);
                    chromosome.getTrust().remove(i);
                } else {//能找到的情况下，调整邻居列表中的ID值（因为原染色体数组中的ID值在这一代中发生了改变）
                    int changeNeiborID=chromosome.getNlist().get(i);
                    chromosome.getNlist().set(i,GAFindChromosomeById.findById(list,changeNeiborID));//把第i个值的ID改变掉(这里返回的是为那个ID的染色体在数组中的下标)
                }
            }
        }
        for(Chromosome chromosome:list){
            /**
             * 这里染色体ID先不改变，因为移除邻居队列中的ID值的时候需要判断ID
             */
//            chromosome.setId(number++);
            if (chromosome.getNlist().size() < num) {//邻居列表的个数不够的情况(添加元素，直到到达指定的个数)
                while (chromosome.getNlist().size()<num){
                    int neiborAddPoint=(int) Math.floor(Math.random()*list.size());
                    if(chromosome.getNlist().contains(neiborAddPoint) || neiborAddPoint==chromosome.getId()){//若有重复则重新插入
                        continue;
                    }
                    /***
                     * 双方都要插入（a是b的邻居，那么b也是a的邻居）
                     */
                    chromosome.getNlist().add(neiborAddPoint);//当前的染色体加入其他染色体作为邻居
                    chromosome.getTrust().add(0.0);

                    list.get(neiborAddPoint).getNlist().add(list.indexOf(chromosome));//邻居把当前染色体加入
                    list.get(neiborAddPoint).getTrust().add(0.0);

                }
            }
        }
        for(Chromosome chromosome:list){
            chromosome.setId(number++);
        }
    }

    /**
     * 用于重新设置染色体ID
     * @param list（染色体数组）
     */
    public void idSort(List<Chromosome> list){
        int number=0;
        for(Chromosome chromosome:list){//重新设置ID
            chromosome.setId(number++);
        }
    }

    @Test
    public void test() {//测试模块
        List<Chromosome> list = new ArrayList<Chromosome>();
        double neiborRatio=0.2;
        GATestTools.produceData(list,neiborRatio);
        System.out.println("生成的数据\n"+list.toString());
        GASelection selection = new GASelection();
        selection.setRatio(0.2);
        selection.setFlag(true);
        selection.jinSelection(list);
        System.out.println("选择的数据：\n"+list);
        selection.neiborCare(list,selection.getNeiborRatio());
        System.out.println(list);

    }

    public List<Chromosome> getOldList() {
        return oldList;
    }

    public void setOldList(List<Chromosome> oldList) {
        this.oldList = oldList;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public double getgSum() {
        return gSum;
    }

    public void setgSum(double gSum) {
        this.gSum = gSum;
    }
    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public double getNeiborRatio() {
        return neiborRatio;
    }

    public void setNeiborRatio(double neiborRatio) {
        this.neiborRatio = neiborRatio;
    }
}
