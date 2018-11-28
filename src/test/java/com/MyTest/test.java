package com.MyTest;

import com.sensor.GATools.GAMutation;
import com.sensor.entity.Chromosome;
import com.sensor.entity.Gene;
import sun.swing.BakedArrayList;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DanLongChen on 2018/11/27
 **/
public class test {
    public static void main(String[] args) {
        List<Chromosome> list = new ArrayList<Chromosome>();
        for(int i=0;i<2;i++){
            Chromosome temp=new Chromosome(i,0.09);
            for(int j = 0;j<3;j++){
                Gene gene=new Gene(2,3);
                gene.Init();
                temp.list.add(gene);
            }
            list.add(temp);
        }
        for(int i=0;i<2;i++){
            List<Gene> g=list.get(i).getList();
            for(int j=0;j<3;j++){
                System.out.println(g.get(i).list.toString());
            }
            System.out.println();
        }
        System.out.println();
        GAMutation.doMutation(list,1);
        for(int i=0;i<2;i++){
            List<Gene> g=list.get(i).getList();
            for(int j=0;j<3;j++){
                System.out.println(g.get(i).list.toString());
            }
            System.out.println();
        }
    }
}
