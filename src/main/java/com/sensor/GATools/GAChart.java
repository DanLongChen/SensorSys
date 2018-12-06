package com.sensor.GATools;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;

import javax.swing.*;
import java.awt.*;

/**
 * Created by DanLongChen on 2018/12/5
 **/
public class GAChart {

    public static CategoryDataset createDataset(String[] rowKeys,Integer[] colKeys,double[][] data) {
//        String[] rowKeys = {"Sort","man"};
//        String[] colKeys = {"HeapSort","ShellSort","BubbleSort","QuickSort","RadixSort","SimpleSort"};
//        Integer[] col={1,2,3,4,5,6};
//        double[][] data = {{1,2,3,4,5,6},{7,8,9,0,10,1}};
        return DatasetUtilities.createCategoryDataset(rowKeys, colKeys, data);//类别，横坐标，数据
//        return DatasetUtilities.createCategoryDataset("man mi","",data);//前面两个数据都会递增
    }
    //根据CategoryDataset创建JFreeChart对象
    public static JFreeChart createChart(CategoryDataset categoryDateset){

        // 创建JFreeChart对象：ChartFactory.createLineChart
        JFreeChart jfreechart = ChartFactory.createLineChart("GA", // 标题
                "Generation",         //categoryAxisLabel （category轴，横轴，X轴标签）
                "Fitness",      // valueAxisLabel（value轴，纵轴，Y轴的标签）
                categoryDateset,  //Dataset
                PlotOrientation.VERTICAL, true, // legend
                false,          //Tooltips
                false);        //URLs

        // 使用CategoryPlot设置各种参数。
        CategoryPlot plot = (CategoryPlot)jfreechart.getPlot();

        // 背景色 透明度
        plot.setBackgroundAlpha(0.5f);

        // 前景色 透明度
        plot.setForegroundAlpha(1.0f);

        // 其他设置 参考 CategoryPlot类
        LineAndShapeRenderer renderer = (LineAndShapeRenderer)plot.getRenderer();
        renderer.setBaseShapesVisible(true); // series 点（即数据点）可见
        renderer.setBaseLinesVisible(true); // series 点（即数据点）间有连线可见
        renderer.setUseSeriesOffset(true); // 设置偏移量
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);
        return jfreechart;
    }
    public static void doChart(String[] rowKeys,Integer[] colKeys,double[][] data){
        CategoryDataset dataset = createDataset(rowKeys,colKeys,data);//生成数据
        JFreeChart freeChart = createChart(dataset);//产生JFreeChart对象
        ChartPanel chartf = new ChartPanel(freeChart,true);
        JFrame jf = new JFrame();
        jf.add(chartf,BorderLayout.WEST);
        jf.setVisible(true);
        jf.setSize(700, 650);
        jf.setLocationRelativeTo(null);
    }
}
