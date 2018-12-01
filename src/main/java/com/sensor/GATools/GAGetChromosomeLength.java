package com.sensor.GATools;

import com.sensor.entity.Chromosome;
import com.sensor.entity.Gene;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DanLongChen on 2018/12/1
 **/
public class GAGetChromosomeLength {
    /***
     * 获取整个染色体数组的bit位数
     * @param list(传入的染色体数组)
     * @return
     */
    public static int getAllLength(List<Chromosome> list){
        int sum=0;
        for(Chromosome chromosome:list){
            for(Gene gene:chromosome.getList()){
                sum+=gene.getList().size();
            }
        }
        return sum;
    }
    public static int getLength(Chromosome chromosome){
        int sum=0;
        for(Gene gene:chromosome.getList()){
            sum+=gene.getList().size();
        }
        return sum;
    }
    @Test
    public void test(){
        List<Chromosome> list = new ArrayList<Chromosome>();
        GATestTools.produceData(list,0.2);
        System.out.println(GAGetChromosomeLength.getAllLength(list));
        System.out.println(GAGetChromosomeLength.getLength(list.get(0)));
    }
}
