package com.sensor.GATools;

import com.sensor.entity.Chromosome;
import com.sensor.entity.Gene;
import sun.rmi.runtime.NewThreadAction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DanLongChen on 2018/11/26
 **/
public class GACross {//染色体交叉，只与自己的邻居队列中的染色体交叉
    public void doCross(List<Chromosome> list, double crossRatio){
        List<Chromosome> newList=new ArrayList<Chromosome>(list);
        for(Chromosome chromosome:list){
            int position=(int)Math.floor(Math.random()*chromosome.getNlist().size());//获得邻居队列中的染色体
            doSingleCross(newList,chromosome,list.get(GAFindChromosomeById.findById(list,position)),crossRatio);//参数说明（新的染色体数组，）
        }
    }
    public void doSingleCross(List<Chromosome> newList,Chromosome num1,Chromosome num2,double crossRatio){
        double ratio=Math.random();//计算当前是否可以交叉
        if(ratio<crossRatio){
            int pointA=((int)(num1.getList().size()*Math.random()))%num1.getList().size();//得到前交叉点
            int pointB=((int)(num2.getList().size()*Math.random()))%num2.getList().size();//得到后交叉点
            int min=Math.min(pointA,pointB);
            int max=Math.max(pointA,pointB);
            for(int i=min;i<=max;i++) {//交换
                Gene temp=num1.getList().get(i);
                num1.getList().set(i,num2.getList().get(i));
                num2.getList().set(i,temp);
            }
            newList.add(num1);
            newList.add(num2);
        }
    }
}
