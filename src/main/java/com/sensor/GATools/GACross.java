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
    public static void doCross(List<Chromosome> list,double crossRatio,double TK){
        List<Chromosome> newList=new ArrayList<Chromosome>(list);
        for(Chromosome chromosome:list){
            int position=chromosome.getNlist().get((int)Math.floor(Math.random()*chromosome.getNlist().size()));//获得邻居队列中的染色体
            doSingleCross(newList,chromosome,list.get(GAFindChromosomeById.findById(list,position)).deepClone(),crossRatio,TK,position);//参数说明（新的染色体数组，）
        }
        list=newList;
        newList=null;
    }

    /***
     * 单个染色体的交叉,前面的交叉不影响后面的交叉，就是说如果前面的染色体更新了，后面的染色体染色体如果要和这个染色体进行交叉，那么还是和原来的染色体交叉
     * @param newList（新生代的染色体，用来存放交叉产生的染色体）
     * @param num1（染色体）
     * @param num2（num1的邻居队列中的染色体）
     * @param crossRatio（交叉率）
     * @param TK（当前温度，这个温度由主函数来控制）
     */
    public static void doSingleCross(List<Chromosome> newList,Chromosome num1,Chromosome num2,double crossRatio,double TK,int ID){
            double ratio=Math.random();//计算当前是否可以交叉
            Chromosome num1Temp=num1.deepClone();
            if(ratio<crossRatio){
                int pointA=((int)(num1.getList().size()*Math.random()))%num1.getList().size();//得到前交叉点
                int pointB=((int)(num2.getList().size()*Math.random()))%num2.getList().size();//得到后交叉点
                int min=Math.min(pointA,pointB);
                int max=Math.max(pointA,pointB);
                int i=min;
               /* System.out.println("染色体ID:"+num1.getId()+":"+"交叉位置："+min+"   "+max);
                System.out.println("被选中的邻居ID是："+ID);*/
                while (i<=max){//交换
                    Gene temp=num1.getList().get(i);
                    num1.getList().set(i,num2.getList().get(i));
                    num2.getList().set(i,temp);
                    i++;
                }
                int scoreNum1=GADecode.getScore(num1Temp);
                int score1=GADecode.getScore(num1);
                int score2=GADecode.getScore(num2);
                if(score1>=score2){
                    if(score1>scoreNum1){//添加新的染色体，这个时候需要对染色体的邻居队列(包括信赖域)进行清空，因为这是一条新的染色体，首先尝试不清空
                        newList.add(num1);
                    }else{
                        if(Math.random()<Math.exp(-(scoreNum1-score1)/TK)){
                            newList.add(num1);
                        }else{
                            newList.add(num1Temp);
                        }
                    }
                }else{
                    if(score2>scoreNum1){
                        newList.add(num2);
                    }else{
                        if(Math.random()<Math.exp(-(scoreNum1-score2)/TK)){
                            newList.add(num2);
                        }else{
                            newList.add(num1Temp);
                        }
                    }
                }
            }
    }
    @Test
    public void test(){
        List<Chromosome> list = new ArrayList<Chromosome>();
        double neiborRatio=0.2;
        GATestTools.produceData(list,neiborRatio);
        System.out.println(list);
        GACross cross = new GACross();
        cross.doCross(list,0.8,88.0);
        System.out.println(list);
    }
}
