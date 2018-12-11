package com.sensor.GATools;

import com.sensor.entity.Chromosome;
import com.sensor.entity.Gene;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by DanLongChen on 2018/11/26
 **/
public class GACross {//染色体交叉，只与自己的邻居队列中的染色体交叉

    /***
     *
     * @param list（染色体数组）
     * @param crossRatio（交叉率）
     * @param TK（当前温度）
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void doMyGACross(List<Chromosome> list, double crossRatio, double TK) {
        List<Chromosome> newList = GADeepCopy.deepCopyList(list);//元染色体数组的备份
        for (Chromosome chromosome : list) {
            int position = chromosome.getNlist().get((int) Math.floor(Math.random() * chromosome.getNlist().size()));//获得邻居队列中的染色体(染色体ID)
//            System.out.println("position:"+position);
            CanShuHuaSingleCross(list, chromosome, newList.get(GAFindChromosomeById.findById(newList, position)).deepClone(), crossRatio);//这里邻居列表的染色体从原数组拿取
        }
    }

    /***
     *
     * @param list（染色体数组）
     * @param crossRatio（交叉概率）
     *
     * SGA采用参数化均匀交叉
     */
    public static void doSGACross(List<Chromosome> list, double crossRatio) {
        List<Chromosome> newList = GADeepCopy.deepCopyList(list);//作为备份
        for (Chromosome chromosome : list) {
            int position = (int) Math.floor(Math.random() * list.size());//从列表随机取一个数
//            System.out.println("position: "+position);
            CanShuHuaSingleCross(list, chromosome, newList.get(position).deepClone(), crossRatio);//第二条染色体从原来的染色体数组拿，而不是交叉之后的染色体数组
        }
    }

    /***
     *
     * @param list（染色体数组）
     * @param crossRatio（交叉概率）
     * @param TK（退火温度）
     * 采用正交表来交叉
     */
    public static void doMGACross(List<Chromosome> list, double crossRatio, double TK) {
        List<Chromosome> newList = GADeepCopy.deepCopyList(list);//原染色体数组的备份
        for (Chromosome chromosome : list) {
            int position = chromosome.getNlist().get((int) Math.floor(Math.random() * chromosome.getNlist().size()));//获得邻居队列中的染色体(染色体ID)
//            System.out.println("position:"+position);
            doZJBSingleCross(list, chromosome, newList.get(GAFindChromosomeById.findById(newList, position)).deepClone(), crossRatio);//这里邻居列表的染色体从原数组拿取
        }
    }

    /***
     * 单个染色体的交叉,前面的交叉不影响后面的交叉，就是说如果前面的染色体更新了，后面的染色体染色体如果要和这个染色体进行交叉，那么还是和原来的染色体交叉
     * @param newList（新生代的染色体，用来存放交叉产生的染色体）
     * @param num1（染色体）
     * @param num2（num1的邻居队列中的染色体）
     * @param crossRatio（交叉率）
     * @param TK（当前温度，这个温度由主函数来控制）
     */
    public static void doSingleCross(List<Chromosome> newList, Chromosome num1, Chromosome num2, double crossRatio, double TK) {
        double ratio = Math.random();//计算当前是否可以交叉
        Chromosome num1Temp = num1.deepClone();//num1的拷贝，用于做交叉
        if (ratio < crossRatio) {
            int pointA = ((int) (num1.getList().size() * Math.random())) % num1.getList().size();//得到前交叉点
            int pointB = ((int) (num2.getList().size() * Math.random())) % num2.getList().size();//得到后交叉点
            int min = Math.min(pointA, pointB);
            int max = Math.max(pointA, pointB);
//                System.out.println("交叉点："+min+" "+max);
            int i = min;
            while (i <= max) {//交换
                Gene temp = num1Temp.getList().get(i);
                num1Temp.getList().set(i, num2.getList().get(i));
                num2.getList().set(i, temp);
                i++;
            }
            GADecode.setScore(num1Temp);//重新计算分数
            GADecode.setScore(num2);
            int scoreNum1 = GADecode.getScore(num1);//num1原来的分数
            int score1 = GADecode.getScore(num1Temp);
            int score2 = GADecode.getScore(num2);
//                System.out.println("三者分数：scoreNum1 score1 score2 :"+scoreNum1+" "+score1+" "+score2);
            if (score1 <= score2) {//这个分数越小越好
                if (score1 < scoreNum1) {//添加新的染色体，这个时候需要对染色体的邻居队列(包括信赖域)进行清空，因为这是一条新的染色体，首先尝试不清空
//                        System.out.println("num1："+num1+" , num1temp:"+num1Temp);
                    GAReplace.doReplace(newList, num1, num1Temp);
                } else {
//                        System.out.println("doS1<S2 else");
                    if (Math.random() < Math.exp(-(scoreNum1 - score1) / TK)) {
                        GAReplace.doReplace(newList, num1, num1Temp);
                    }
                }
            } else {
                if (score2 < scoreNum1) {
//                        System.out.println("num1："+num1+" , num2:"+num2);
                    GAReplace.doReplace(newList, num1, num2);
                } else {
//                        System.out.println("doS1>S2 else");
                    if (Math.random() < Math.exp(-(scoreNum1 - score2) / TK)) {
                        GAReplace.doReplace(newList, num1, num2);
                    }
                }
            }
        }
//        System.out.println("交叉之后的染色体数组："+newList);
    }


    /***
     * 参数化交叉
     *
     * @param newList（染色体数组）
     * @param num1（第一个染色体）
     * @param num2（第二个染色体）
     * @param crossRatio（交叉概率）
     */
    public static void CanShuHuaSingleCross(List<Chromosome> newList, Chromosome num1, Chromosome num2, double crossRatio){
        double ratio=Math.random();
        Chromosome num1Temp=num1.deepClone();
        if(ratio<=crossRatio){
            for(int i=0;i<num1.getList().size();i++){
                double geneRatio=Math.random();
                if(geneRatio<=crossRatio){//如果小于这个概率就进行交换基因，否则进行交换
//                    System.out.println("第："+i+" 个位置进行交叉！");
                    Gene temp = num1Temp.getList().get(i);
                    num1Temp.getList().set(i, num2.getList().get(i));
                    num2.getList().set(i, temp);
                }else{
                    continue;
                }
            }
            /***
             * 交叉结束，选取最好的那条染色体
             */
            GADecode.setScore(num1Temp);//重新计算分数
            GADecode.setScore(num2);
            int scoreNum1 = GADecode.getScore(num1);//num1原来的分数
            int score1 = GADecode.getScore(num1Temp);
            int score2 = GADecode.getScore(num2);
//            System.out.println("num1原来的值："+scoreNum1+" score1的值："+score1+" score2的值："+score2);
            if(score1<=score2 && score1<scoreNum1){
//                System.out.println("score1 最好");
                GAReplace.doReplace(newList,num1,num1Temp);
            }
            if(score2<score1 && score2<scoreNum1){
//                System.out.println("score2 最好");
                GAReplace.doReplace(newList,num1,num2);
            }

        }
    }

    public static void doZJBSingleCross(List<Chromosome> newList, Chromosome num1, Chromosome num2, double crossRatio){
        int[][] ZJTable=new int[][]{{0,0,0},{0,1,1},{1,0,1},{1,1,0}};//构造正交表
        double ratio=Math.random();
        if(ratio<crossRatio){

            int pointA = ((int) (num1.getList().size() * Math.random())) % num1.getList().size();//得到前交叉点
            int pointB = ((int) (num2.getList().size() * Math.random())) % num2.getList().size();//得到后交叉点
            while(pointA==pointB){//一定要得到不同的两个点，两个点将染色体分成三段
               pointA = ((int) (num1.getList().size() * Math.random())) % num1.getList().size();//得到前交叉点
                pointB = ((int) (num2.getList().size() * Math.random())) % num2.getList().size();//得到后交叉点
            }
            int min = Math.min(pointA, pointB);
            int max = Math.max(pointA, pointB);
//            System.out.println("中的是："+num1.getId()+"切割的位置："+min+" "+max);
            Chromosome num1Temp=num1.deepClone();
            /***
             * 需要被分段的染色体，先做分段
             */
            List<Gene> listA=num1Temp.getList();
            List<Gene> listB=num2.getList();

            List<Chromosome> chromosomeList=new ArrayList<Chromosome>();//装载正交表正交产生的四条染色体
            chromosomeList.add(new Chromosome(0,0));
            chromosomeList.add(new Chromosome(0,0));
            chromosomeList.add(new Chromosome(0,0));
            chromosomeList.add(new Chromosome(0,0));

            for(int i=0;i<ZJTable.length;i++){//循环四个正交表的行
                int[] temp=ZJTable[i];
                for(int j=0;j<temp.length;j++){
                    if(j==0 && temp[j]==0){
                        chromosomeList.get(i).getList().addAll(new ArrayList<Gene>(listA.subList(0,min)));
                    }
                    if(j==0 && temp[j]==1){
                        chromosomeList.get(i).getList().addAll(new ArrayList<Gene>(listB.subList(0,min)));
                    }

                    if(j==1 && temp[j]==0){
                        chromosomeList.get(i).getList().addAll(new ArrayList<Gene>(listA.subList(min,max)));
                    }
                    if(j==1 && temp[j]==1){
                        chromosomeList.get(i).getList().addAll(new ArrayList<Gene>(listB.subList(min,max)));
                    }

                    if(j==2 && temp[j]==0){
                        chromosomeList.get(i).getList().addAll(new ArrayList<Gene>(listA.subList(max,listA.size())));
                    }
                    if(j==2 && temp[j]==1){
                        chromosomeList.get(i).getList().addAll(new ArrayList<Gene>(listB.subList(max,listA.size())));
                    }
                }
            }
            GADecode.setAllScore(chromosomeList);
//            System.out.println(chromosomeList);
            Collections.sort(chromosomeList, new Comparator<Chromosome>() {
                @Override
                public int compare(Chromosome o1, Chromosome o2) {
                   return o1.getScore()>o2.getScore()?1:o1.getScore()==o2.getScore()?0:-1;
                }
            });


            GAReplace.doReplace(newList,num1,chromosomeList.get(0));//替换旧的

        }
    }

    @Test
    public void test() {
        List<Chromosome> list = new ArrayList<Chromosome>();
        double neiborRatio = 0.2;
        GATestTools.produceData(list, neiborRatio);
        System.out.println(list);
        GACross cross = new GACross();
        cross.doSGACross(list, 1.0);
        System.out.println(list);
    }
}
