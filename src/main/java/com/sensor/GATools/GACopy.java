package com.sensor.GATools;

import com.sensor.entity.Chromosome;
import com.sensor.entity.Gene;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DanLongChen on 2018/11/26
 **/
public class GACopy {//染色体复制
    public static void doCopy(List<Chromosome> beforeCopy,List<Chromosome> afterCopy) {
        afterCopy.addAll(GADeepCopy.deepCopyList(beforeCopy));
    }

    @Test
    public void test() {//验证深复制(要想开启测试，把private改为public)
        List<Chromosome> list = new ArrayList<Chromosome>();
        for (int i = 0; i < 10; i++) {
            Chromosome temp = new Chromosome(i, 0.09);
            for (int j = 0; j < 3; j++) {
                Gene gene = new Gene(2, 3);
                gene.Init();
                temp.list.add(gene);
            }
            GADecode.getScore(temp);
            list.add(temp);
        }
        List<Chromosome> newList=new ArrayList<Chromosome>();
        GACopy.doCopy(list,newList);
        System.out.println(list.toString());
        System.out.println(newList.toString());
        if(list.get(0).getList().get(0).getList().get(0)){
            list.get(0).getList().get(0).getList().set(0,false);
        }else{
            list.get(0).getList().get(0).getList().set(0,true);
        }
        System.out.println(list.toString());
        System.out.println(newList.toString());
    }
}
