package com.sensor.Generate;

import com.sensor.GATools.GADecode;
import com.sensor.GATools.GAFindChromosomeById;
import com.sensor.GATools.GAFitness;
import com.sensor.entity.Chromosome;
import com.sensor.entity.Gene;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DanLongChen on 2018/11/26
 **/
public class GASimulation extends Simulation {
    private List<Chromosome> mList = new ArrayList<Chromosome>();//两个种群
    private List<Chromosome> fList = new ArrayList<Chromosome>();
    private int mPopulation = 10;//种群数量
    private int fPopulation = 10;
    private double mMutationRatio = 0.09;//种群基础变异率
    private double fMutatioinRatio = 0.01;
    private double crossRatio = 0.8;//交叉率
    private int maxGeneration = 500;//最大代数
    private double neighborRatio = 0.2;//邻居列表占全体种群的比例
    private double TK=90.0;//初始温度
    private double getCrossRatio=0.88;//温度下降比例

    private void init() {//初始化主类
        initPopulation(mList, mPopulation, 6, mMutationRatio);//初始化每个种群
        initPopulation(fList, fPopulation, 6, fMutatioinRatio);
        /*
        初始化邻居队列
         */
        initNeighbor(mList, neighborRatio);
        initNeighbor(fList, neighborRatio);
    }
    @Test
    public void doGA(){
        GASimulation gaSimulation = new GASimulation();
        gaSimulation.init();//初始化阶段
        int dGeneration=0;
        while(dGeneration<maxGeneration){
            GAFitness.allFitness(mList);//计算适应度
            GAFitness.allFitness(fList);


        }

    }

    public static void main(String[] args) {

    }

    private void initPopulation(List<Chromosome> list, int population, int nodeNumber, double ratio) {//要初始化的种群，种群大小，传入的网络信息（这里暂时用数字表示），初始化变异率
        for (int i = 0; i < population; i++) {//初始化公种群的染色体
            Chromosome mchromosome = new Chromosome(i, ratio);
            for (int j = 0; j < nodeNumber; j++) {
                Gene gene = new Gene(2, 3);//输入in和out，以后用数组代替
                gene.Init();//初始化基因
                mchromosome.getList().add(gene);
            }
            list.add(mchromosome);
        }
    }

    private void initNeighbor(List<Chromosome> list, double neighborRatio) {
        int neighborNum = (int) Math.round(list.size() * neighborRatio);
        for (Chromosome chromosome : list) {
            for (int i = 0; i < neighborNum; i++) {
                int point = (int) Math.floor(Math.random() * list.size());
                addNeighborToChromosome(chromosome, point);
            }
        }
    }

    private void addNeighborToChromosome(Chromosome chromosomeA, int point) {
        chromosomeA.getNlist().add(point);
    }
}
