package com.sensor.GATools;

import com.sensor.entity.Chromosome;
import com.sensor.entity.Gene;

import java.util.List;

/**
 * Created by DanLongChen on 2018/11/29
 **/
public class GATestTools {
    public static void produceData(List<Chromosome> list){
        for (int i = 0; i < 15; i++) {
            Chromosome temp = new Chromosome(i, 0.09);
            for (int j = 0; j < 3; j++) {
                Gene gene = new Gene(2, 3);
                gene.Init();
                temp.list.add(gene);
            }
            GADecode.getScore(temp);
            list.add(temp);
        }
    }
}
