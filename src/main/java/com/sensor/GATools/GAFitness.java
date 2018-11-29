package com.sensor.GATools;

import com.sensor.entity.Chromosome;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by DanLongChen on 2018/11/26
 **/
@Component
public class GAFitness {
    public static void sigleFitness(Chromosome chromosome) {//计算单个染色体适应度
        if (chromosome == null) {
            return;
        }
        GADecode.getScore(chromosome);//这里会写入染色体自带的score字段
    }

    public static void allFitness(List<Chromosome> list) {//计算染色体数组的适应度
        GADecode.getAllScore(list);
    }

    public static int getAllFitness(List<Chromosome> chromosomes) {//全部染色体适应度
        int sum = 0;
        if (chromosomes == null) {
            return sum;
        }
        for (Chromosome chromosome : chromosomes) {
            sum += chromosome.getScore();
        }
        return sum;
    }
}
