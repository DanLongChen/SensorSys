package com.sensor.GATools;

import com.sensor.entity.Chromosome;

import java.util.List;

/**
 * Created by DanLongChen on 2018/11/28
 **/
public class GAFindChromosomeById {
    /***
     *
     * @param list（染色体数组）
     * @param id（染色体ID）
     * @return（返回点额是染色体在数组中的下标）
     */
    public static int findById(List<Chromosome> list,int id){
        if(list==null){
            return Integer.MAX_VALUE;
        }
        for(int i=0;i<list.size();i++){
            Chromosome temp=list.get(i);
            if(temp.getId()==id){//找到的情况
                return i;//返回最近一个找到的染色体数组的ID值
            }
        }
        return Integer.MAX_VALUE;//没找到或者list为空的情况
    }
}
