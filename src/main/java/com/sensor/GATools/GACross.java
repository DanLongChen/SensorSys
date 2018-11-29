package com.sensor.GATools;

import com.sensor.entity.Chromosome;
import com.sensor.entity.Gene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DanLongChen on 2018/11/26
 **/
public class GACross {//染色体交叉，只与自己的邻居队列中的染色体交叉
    public void doCross(List<Chromosome> list, double crossRatio,double TK){
        List<Chromosome> newList=new ArrayList<Chromosome>(list);
        for(Chromosome chromosome:list){
            int position=(int)Math.floor(Math.random()*chromosome.getNlist().size());//获得邻居队列中的染色体
            doSingleCross(newList,chromosome,list.get(GAFindChromosomeById.findById(list,position)),crossRatio,TK);//参数说明（新的染色体数组，）
        }
    }
    public void doSingleCross(List<Chromosome> newList,Chromosome num1,Chromosome num2,double crossRatio,double TK){
        try {
            double ratio=Math.random();//计算当前是否可以交叉
            Chromosome num1Temp=num1.deepClone();
            if(ratio<crossRatio){
                int pointA=((int)(num1.getList().size()*Math.random()))%num1.getList().size();//得到前交叉点
                int pointB=((int)(num2.getList().size()*Math.random()))%num2.getList().size();//得到后交叉点
                int min=Math.min(pointA,pointB);
                int max=Math.max(pointA,pointB);
                int i=min;
                while (i<=max){//交换
                    Gene temp=num1.getList().get(i);
                    num1.getList().set(i,num2.getList().get(i));
                    num2.getList().set(i,temp);
                }
                int scoreNum1=GADecode.getScore(num1Temp);
                int score1=GADecode.getScore(num1);
                int score2=GADecode.getScore(num2);
                if(score1>=score2){
                    if(score1>scoreNum1){//添加新的染色体，这个时候需要对染色体的邻居队列(包括信赖域)进行清空，因为这是一条新的染色体，首先尝试不清空
//                        num1.getNlist().clear();
//                        num1.getTrust().clear();
                        newList.add(num1);
                    }else{
                        if(Math.random()<Math.exp(-(scoreNum1-score1)/TK)){
//                            num1.getNlist().clear();
//                            num1.getTrust().clear();
                            newList.add(num1);
                        }else{
                            newList.add(num1Temp);
                        }
                    }
                }else{
                    if(score2>scoreNum1){
//                        num2.getNlist().clear();
//                        num2.getTrust().clear();
                        newList.add(num2);
                    }else{
                        if(Math.random()<Math.exp(-(scoreNum1-score2)/TK)){
//                            num2.getNlist().clear();
//                            num2.getTrust().clear();
                            newList.add(num2);
                        }else{
                            newList.add(num1Temp);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void neiborHandler(List<Chromosome> list,double neiborRatio){//邻居队列整理
        int i=0;
        while (i<list.size()) {
            Chromosome temp=list.get(i);

            i++;
        }
    }
}
