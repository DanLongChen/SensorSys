package com.sensor.GATools;

import com.sensor.entity.Chromosome;

import java.util.List;

/**
 * Created by DanLongChen on 2018/12/4
 **/
public class GAReplace {
    public static void doReplace(List<Chromosome> list,Chromosome oldOne,Chromosome newOne){//替换原来的染色体（新染色体保持和原来的染色体的ID，邻居队列和信赖域相同）
        if(list==null){
            return;
        }
        newOne.setId(oldOne.getId());//替换ID
        newOne.getNlist().clear();//替换邻居队列
        newOne.getNlist().addAll(oldOne.getNlist());
        newOne.getTrust().clear();//替换信赖域
        newOne.getTrust().addAll(oldOne.getTrust());
        for(int i=0;i<list.size();i++){
            if(list.get(i).equals(oldOne)){
                list.set(i,newOne);
            }
        }
    }
}
