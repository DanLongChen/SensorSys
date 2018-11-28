package com.sensor.Generate;

import com.sensor.entity.Chromosome;
import com.sensor.entity.Gene;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DanLongChen on 2018/11/26
 **/
public class GASimulation extends Simulation{
    public List<Chromosome> mList = new ArrayList<Chromosome>();
    public List<Chromosome> fList = new ArrayList<Chromosome>();
    int mPopulation=250;
    int fPopulation=250;
    double mMutationRatio=0.09;
    double fMutatioinRatio=0.01;
    double crossRatio=0.8;
    int maxGeneration=500;
    public void init(){

    }
    public void initPopulation(List<Chromosome> list, int population,int nodeNumber,double ratio){
        for(int i=0;i<population;i++){//初始化公种群的染色体
            Chromosome mchromosome=new Chromosome(i,ratio);
            for(int j=0;j<nodeNumber;j++){
                Gene gene=new Gene(2,3);//输入in和out，以后用数组代替
                gene.Init();
                mchromosome.getList().add(gene);
            }
            mList.add(mchromosome);
        }
    }
}
