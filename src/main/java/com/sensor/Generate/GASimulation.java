package com.sensor.Generate;

import com.sensor.GATools.*;
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
    private List<Chromosome> tList=new ArrayList<Chromosome>();//合并之后的种群
    private int mPopulation = 10;//种群数量
    private int fPopulation = 10;
    private double mMutationRatio = 0.09;//种群基础变异率
    private double fMutatioinRatio = 0.01;
    private double mutationRatio=0;//合并之后的基础变异率
    private double crossRatio = 0.8;//交叉率
    private int maxGeneration = 500;//最大代数
    private int zjGeneration=200;//种间杂交发生的代数
    /***
     * 染色体控制参数
     */
    private double neighborRatio = 0.2;//邻居列表占全体种群的比例
    private double K=0.9;//是否开启精英的阈值
    /***
     *退火参数控制
     */
    private double MTK=90.0;//f种群的初始温度
    private double FTK=90.0;//m种群的初始温度
    private double TK=90.0;//合并之后的初始温度
    private double TKDecline=0.88;//温度下降比例（整体有效）
    private int TKGeneration=3;//经过多少代最大值相似则下降温度

    /***
     * 初始化染色体种群
     */
    private void init() {
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
        int dGeneration=0;//当前代数
        int mSameGeneration=0;//m种群相似染色体的代数
        int fSameGeneration=0;//f种群相似染色体的代数
        int sameGeneration=0;//合并之后的染色体代数
        GASelection mSelection = new GASelection();//选择器
        GASelection fSelection = new GASelection();
        GASelection selection = new GASelection();

        outer:
        while(dGeneration<maxGeneration){
            GAFitness.allFitness(fList);
            GAFitness.allFitness(mList);//计算适应度
            int fMaxScore=GADecode.getMaxScore(fList);
            int mMaxScore=GADecode.getMaxScore(mList);


            if(dGeneration<zjGeneration){
                /**
                 * 选择过程
                 */
                mSelection.setOldList(mList);
                fSelection.setOldList(fList);
                mSelection.duSelection();//这里选用赌轮选择法
                fSelection.duSelection();
                /**
                 * 交叉过程（之后计算退火温度）
                 */
                GACross.doCross(mList,crossRatio,MTK);
                GACross.doCross(fList,crossRatio,FTK);
                /**
                 * 变异过程
                 */
                GAMutation.doMutation(mList,mMutationRatio,dGeneration);
                GAMutation.doMutation(fList,fMutatioinRatio,dGeneration);

                /**
                 * 退火温度设置
                 */
                if(GADecode.getMaxScore(mList)==fMaxScore){
                    mSameGeneration++;
                    if(mSameGeneration>=TKGeneration){
                        MTK*=TKDecline;
                        mSameGeneration=0;
                    }
                }
                if(GADecode.getMaxScore(fList)==mMaxScore){
                    fSameGeneration++;
                    if(fSameGeneration>=TKGeneration){
                        FTK*=TKDecline;
                        fSameGeneration=0;
                    }
                }
            }else{
                break outer;
            }
            dGeneration++;
        }
        /**
         * 种内遗传完成，开始种间遗传操作
         */
        tList.addAll(mList);
        tList.addAll(fList);

        while(dGeneration<maxGeneration){
            /**
             * 计算适应度
             */
            GAFitness.allFitness(tList);
            int maxScore=GADecode.getMaxScore(tList);//获取最好的适应度
            /**
             * 染色体选择
             */
            selection.setOldList(tList);
            selection.duSelection();//使用赌轮选择法
            /**
             * 交叉
             */
            GACross.doCross(tList,crossRatio,TK);
            /**
             * 变异
             */
            GAMutation.doMutation(tList,mutationRatio,dGeneration);
            if(GADecode.getMaxScore(mList)==maxScore){
                sameGeneration++;
                if(sameGeneration>=TKGeneration){
                    TK*=TKDecline;
                    sameGeneration=0;
                }
            }
            dGeneration++;
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
        chromosomeA.getTrust().add(1.0);//填充信赖域（与邻居列表是一一对应的）
    }
}
