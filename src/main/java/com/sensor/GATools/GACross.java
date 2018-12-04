package com.sensor.GATools;

import com.sensor.entity.Chromosome;
import com.sensor.entity.Gene;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
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
    public static void doMyGACross(List<Chromosome> list,double crossRatio,double TK){
        List<Chromosome> newList=GADeepCopy.deepCopyList(list);//元染色体数组的备份
        for(Chromosome chromosome:list){
            int position=chromosome.getNlist().get((int)Math.floor(Math.random()*chromosome.getNlist().size()));//获得邻居队列中的染色体(染色体ID)
//            System.out.println("position:"+position);
            doSingleCross(list,chromosome,newList.get(GAFindChromosomeById.findById(newList,position)).deepClone(),crossRatio,TK);//这里邻居列表的染色体从原数组拿取
        }
    }
    public static void doSGACross(List<Chromosome> list,double crossRatio,double TK){
        List<Chromosome> newList=GADeepCopy.deepCopyList(list);//作为备份
        for(Chromosome chromosome:list){
            int position=(int) Math.floor(Math.random()*list.size());//从列表随机取一个数
//            System.out.println("position: "+position);
            doSingleCross(list,chromosome,newList.get(position).deepClone(),crossRatio,TK);//第二条染色体从原来的染色体数组拿，而不是交叉之后的染色体数组
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
    public static void doSingleCross(List<Chromosome> newList,Chromosome num1,Chromosome num2,double crossRatio,double TK){
            double ratio=Math.random();//计算当前是否可以交叉
            Chromosome num1Temp=num1.deepClone();//num1的拷贝，用于做交叉
            if(ratio<crossRatio){
                int pointA=((int)(num1.getList().size()*Math.random()))%num1.getList().size();//得到前交叉点
                int pointB=((int)(num2.getList().size()*Math.random()))%num2.getList().size();//得到后交叉点
                int min=Math.min(pointA,pointB);
                int max=Math.max(pointA,pointB);
//                System.out.println("交叉点："+min+" "+max);
                int i=min;
                while (i<=max){//交换
                    Gene temp=num1Temp.getList().get(i);
                    num1Temp.getList().set(i,num2.getList().get(i));
                    num2.getList().set(i,temp);
                    i++;
                }
                GADecode.setScore(num1Temp);//重新计算分数
                GADecode.setScore(num2);
                int scoreNum1=GADecode.getScore(num1);//num1原来的分数
                int score1=GADecode.getScore(num1Temp);
                int score2=GADecode.getScore(num2);
//                System.out.println("三者分数：scoreNum1 score1 score2 :"+scoreNum1+" "+score1+" "+score2);
                if(score1<=score2){//这个分数越小越好
                    if(score1<scoreNum1){//添加新的染色体，这个时候需要对染色体的邻居队列(包括信赖域)进行清空，因为这是一条新的染色体，首先尝试不清空
//                        System.out.println("num1："+num1+" , num1temp:"+num1Temp);
                        GAReplace.doReplace(newList,num1,num1Temp);
                    }else{
//                        System.out.println("doS1<S2 else");
                        if(Math.random()<Math.exp(-(scoreNum1-score1)/TK)){
                            GAReplace.doReplace(newList,num1,num1Temp);
                        }
                    }
                }else{
                    if(score2<scoreNum1){
//                        System.out.println("num1："+num1+" , num2:"+num2);
                        GAReplace.doReplace(newList,num1,num2);
                    }else{
//                        System.out.println("doS1>S2 else");
                        if(Math.random()<Math.exp(-(scoreNum1-score2)/TK)){
                            GAReplace.doReplace(newList,num1,num2);
                        }
                    }
                }
            }
//        System.out.println("交叉之后的染色体数组："+newList);
    }


    @Test
    public void test(){
        List<Chromosome> list = new ArrayList<Chromosome>();
        double neiborRatio=0.2;
        GATestTools.produceData(list,neiborRatio);
        System.out.println(list);
        GACross cross = new GACross();
        cross.doMyGACross(list,1.0,88.0);
        System.out.println(list);
    }
}
