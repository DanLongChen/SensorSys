package com.sensor.GATools;

import com.sensor.entity.Chromosome;
import com.sensor.entity.Gene;
import org.springframework.stereotype.Component;

/**
 * Created by DanLongChen on 2018/11/26
 **/
@Component
public class GADecode {//最后输出结果
    public static int getScore(Chromosome chromosome) {
        if (chromosome == null) {
            return 0;
        }
        int score=0;
        for (Gene gene:chromosome.getList()) {
            for(Boolean b:gene.getList()){
                score<<=1;
                if(b){
                    score+=1;
                }
            }
        }
        chromosome.setScore(score);
        return score;
    }
}
