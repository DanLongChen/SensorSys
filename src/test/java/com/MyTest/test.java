package com.MyTest;

import com.sensor.GATools.GACross;
import com.sensor.GATools.GADecode;
import com.sensor.GATools.GASelection;
import com.sensor.GATools.GATestTools;
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
        double neiborRatio = 0.2;
        GATestTools.produceData(list, neiborRatio);
        System.out.println(list);
        GACross cross = new GACross();
        cross.doSGACross(list, 1.0);
        System.out.println(list);
    }
}
