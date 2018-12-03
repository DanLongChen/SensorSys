package com.MyTest;

import com.sensor.GATools.GASelection;
import com.sensor.GATools.GATestTools;
import com.sensor.entity.Chromosome;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paser on 2018/12/3.
 */
public class testSelection {
    public static void main(String[] args) {
        List<Chromosome> list = new ArrayList<Chromosome>();
        double neiborRatio=0.2;
        GATestTools.produceData(list,neiborRatio);
        System.out.println("生成的数据\n"+list);
        GASelection selection = new GASelection();
        selection.duSelection(list);
        System.out.println("选择之后的数组\n"+list);
    }
}
