package com.MyTest;

import javax.swing.text.Position;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by DanLongChen on 2018/11/27
 **/
public class testCross {
    public static void main(String[] args) {
        List<String> list1=new ArrayList<String>(Arrays.asList("1","2","3","4","5"));
        List<String> list2=new ArrayList<String>(Arrays.asList("6","7","8","9","10"));
        double rpoint=Math.random();
        int pointA=((int)(list1.size()*Math.random()))%list1.size();//得到前交叉点
        int pointB=((int)(list2.size()*Math.random()))%list1.size();//得到后交叉点
        int min=Math.min(pointA,pointB);
        int max=Math.max(pointA,pointB);
        System.out.println(min+" "+max);
//        List<String> temp1=new ArrayList<String>(list1.subList(min,max));
//        List<String> temp2=new ArrayList<String>(list2.subList(min,max));
//        System.out.println(temp1.toString());
//        System.out.println(temp2.toString());

        for(int i=min;i<=max;i++) {
            String temp=list1.get(i);
            list1.set(i,list2.get(i));
            list2.set(i,temp);
        }
        System.out.println(list1.toString());
        System.out.println(list2.toString());

    }
}
