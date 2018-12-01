package com.sensor.Generate;

import com.sensor.GATools.GACross;
import com.sensor.GATools.GAFitness;
import com.sensor.GATools.GASelection;
import com.sensor.entity.Chromosome;
import com.sensor.entity.Gene;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DanLongChen on 2018/11/26
 **/
public class GASimulation extends Simulation {
    /***
     * 种群控制参数
     */
    private List<Chromosome> mList = new ArrayList<Chromosome>();//两个种群
    private List<Chromosome> fList = new ArrayList<Chromosome>();
    private int mPopulation = 10;//种群数量
    private int fPopulation = 10;
    private double mMutationRatio = 0.09;//种群基础变异率
    private double fMutatioinRatio = 0.01;
    private double crossRatio = 0.8;//交叉率
    private int maxGeneration = 500;//最大代数
    /***
     * 染色体控制参数
     */
    private double neighborRatio = 0.2;//邻居列表占全体种群的比例
    private double TK=90.0;//初始温度
    private double getCrossRatio=0.88;//温度下降比例
    private double K=0.9;//是否开启精英的阈值

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
        GASelection selection = new GASelection();
        GACross corss=new GACross();
        while(dGeneration<maxGeneration){
            GAFitness.allFitness(mList);//计算适应度
            GAFitness.allFitness(fList);

            selection.setFlag(false);//染色体选择，验证是否开启了精英发法则
            selection.duSelection();


        }

    }

    public static void main(String[] args) {

    }

    /***
     * 初始化种群
     * @param list（种群数组）
     * @param population（人口数量）
     * @param nodeNumber（传入的网络信息）
     * @param ratio（初始化变异率）
     */
    private void initPopulation(List<Chromosome> list, int population, int nodeNumber, double ratio) {
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

    /***
     * 初始化种群的邻居队列和信赖域
     * @param list（种群数组）
     * @param neighborRatio（邻居占种群个数的比例）
     */
    private void initNeighbor(List<Chromosome> list, double neighborRatio) {
        int neighborNum = (int) Math.round(list.size() * neighborRatio);
        for (Chromosome chromosome : list) {
            for (int i = 0; i < neighborNum; i++) {
                int neibor = list.get((int) Math.floor(Math.random() * list.size())).getId();
                addNeighborToChromosome(chromosome, neibor);
            }
        }
    }

    private void addNeighborToChromosome(Chromosome chromosomeA, int point) {
        chromosomeA.getNlist().add(point);//填充邻居列表，同时初始化信赖域为1
        chromosomeA.getTrust().add(1.0);//与邻居列表是一一对应的
    }
}
