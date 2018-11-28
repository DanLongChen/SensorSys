package com.sensor.GATools;

import com.sensor.entity.Chromosome;
import jdk.nashorn.internal.runtime.linker.LinkerCallSite;

import java.util.Arrays;
import java.util.List;

/**
 * Created by DanLongChen on 2018/11/28
 **/
public class GAFindChromosomeById {
    public static int findById(List<Chromosome> list,int id){
        if(list==null){
            return Integer.MAX_VALUE;
        }
        for(int i=0;i<list.size();i++){
            Chromosome temp=list.get(i);
            if(temp.getId()==id){
                return i;
            }
        }
        return Integer.MAX_VALUE;
    }
}
