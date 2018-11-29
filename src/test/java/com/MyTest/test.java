package com.MyTest;

import com.sensor.GATools.GADecode;
import com.sensor.GATools.GASelection;
import com.sensor.entity.Chromosome;
import com.sensor.entity.Gene;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DanLongChen on 2018/11/28
 **/
public class test {
    public static void main(String[] args) {//测试染色体选择模块
        List<Chromosome> list = new ArrayList<Chromosome>();
        for(int i=0;i<2;i++){
            Chromosome temp=new Chromosome(i,0.09);
            for(int j = 0;j<3;j++){
                Gene gene=new Gene(2,3);
                gene.Init();
                temp.list.add(gene);
            }
            GADecode.getScore(temp);
            list.add(temp);
        }
        GADecode.getAllScore(list);
        GASelection selection=new GASelection();
        selection.setList(list);
        selection.jinSelection();
    }
}
