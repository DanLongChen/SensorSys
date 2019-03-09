package com.sensor.Generate;

import com.sensor.Ford_Fulkerson.GANetwork;
import com.sensor.Ford_Fulkerson.Graph;
import com.sensor.Ford_Fulkerson.GraphResolve;
import com.sensor.GATools.*;
import com.sensor.entity.Chromosome;
import com.sensor.entity.MergeNode;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by DanLongChen on 2018/11/26
 **/
public class GASimulation extends Simulation {
    /***
     * MyGA种群控制参数
     */
    private List<Chromosome> mList = new ArrayList<Chromosome>();//两个种群
    private List<Chromosome> fList = new ArrayList<Chromosome>();
    private List<Chromosome> tList = new ArrayList<Chromosome>();//合并之后的种群
    private int mPopulation = 100;//种群数量
    private int fPopulation = 100;
    private double mMutationRatio = 0.09;//种群基础变异率
    private double fMutatioinRatio = 0.01;
    private double mutationRatio = 0.09;//合并之后的基础变异率
    private double crossRatio = 0.8;//交叉率
    private int maxGeneration = 50;//最大代数
    private int zjGeneration = 15;//种间杂交发生的代数
    /***
     * 染色体控制参数
     */
    private double neighborRatio = 0.8;//邻居列表占全体种群的比例
    private double K = 0.9;//是否开启精英的阈值
    /***
     *退火参数控制
     */
    private double MTK = 90.0;//f种群的初始温度
    private double FTK = 90.0;//m种群的初始温度
    private double TK = 90.0;//合并之后的初始温度
    private double TKDecline = 0.88;//温度下降比例（整体有效）
    private int TKGeneration = 3;//经过多少代最大值相似则下降温度


    /***
     * SGA种群控制参数
     */
    private List<Chromosome> SGAList = new ArrayList<Chromosome>();//初始化染色体数组
    private int SGAPopulation = 200;//初始化种群数量
    private double SGAMutationRatio = 0.01;//变异概率
    private double SGACrossRatio = 0.8;//交叉概率
    private int SGAmaxGeneration = 50;//最大代数

    /**
     * MGA种群控制参数
     */
    private List<Chromosome> MGAList = new ArrayList<Chromosome>();//初始化染色体数组
    private int MGAPopulation = 200;//种群大小
    private double MGAMutationRatio = 0.09;//变异概率
    private double MGACrossRatio = 0.8;//交叉概率
    private int MGAmaxGeneration = 50;//最大代数

    /***
     * 初始化染色体种群
     */
    @Test
    public void initMyGA() {
        /***
         * 网络图部分
         */
        Graph graph= GANetwork.initNetwork(3);
        List<MergeNode> list= GraphResolve.getMergeNode(graph,false);//获得可能的编码节点
        /**
         * 清空染色体数组
         */
        mList.clear();
        fList.clear();
        tList.clear();

        GAInit.initPopulation(mList, mPopulation, list, mMutationRatio);//初始化每个种群
        GAInit.initPopulation(fList, fPopulation, list, fMutatioinRatio);
        /**
         *
         *  初始化邻居队列
         *
         **/
        GAInit.initNeighbor(mList, neighborRatio);
        GAInit.initNeighbor(fList, neighborRatio);
        /**
         * 计算分数
         */
        GADecode.setAllScore(mList);
        GADecode.setAllScore(fList);
    }

    private void initSGA() {
        Graph graph= GANetwork.initNetwork(3);
        List<MergeNode> list= GraphResolve.getMergeNode(graph, false);//获得可能的编码节点
        /**
         * 清空染色体数组
         */
        SGAList.clear();

        GAInit.SGAInit(SGAList, SGAPopulation, list);

        GADecode.setAllScore(SGAList);
    }

    private void initMGA() {
        Graph graph= GANetwork.initNetwork(3);
        List<MergeNode> list= GraphResolve.getMergeNode(graph,false);//获得可能的编码节点
        MGAList.clear();

        GAInit.initPopulation(MGAList, MGAPopulation, list, 0);

        GAInit.initNeighbor(MGAList, neighborRatio);

        GADecode.setAllScore(MGAList);
    }

    /***
     *
     * @param list（用于存储每一代中最好的个体的分数）
     */
    @Test
    public void doMyGA(List<Integer> list) {
        initMyGA();//初始化


        int dGeneration = 0;//当前代数
        int mSameGeneration = 0;//m种群相似染色体的代数
        int fSameGeneration = 0;//f种群相似染色体的代数
        int sameGeneration = 0;//合并之后相似的染色体代数
        GASelection mSelection = new GASelection();//选择器
        GASelection fSelection = new GASelection();
        GASelection selection = new GASelection();
        mSelection.setNeiborRatio(neighborRatio);
        fSelection.setNeiborRatio(neighborRatio);
        selection.setNeiborRatio(neighborRatio);
        selection.setFlag(true);

        outer:
        while (dGeneration < maxGeneration) {
            GAFitness.allFitness(fList);
            GAFitness.allFitness(mList);//计算适应度
            int fMaxScore = GADecode.getMaxScore(fList);
            int mMaxScore = GADecode.getMaxScore(mList);


            if (dGeneration < zjGeneration) {
                /**
                 * 选择过程
                 */
//                mSelection.setOldList(mList);
//                fSelection.setOldList(fList);
                mSelection.jinSelection(mList);//这里选用赌轮选择法
                fSelection.jinSelection(fList);
                mSelection.neiborCare(mList, mSelection.getNeiborRatio());
                fSelection.neiborCare(fList, fSelection.getNeiborRatio());
                /**
                 * 交叉过程（之后计算退火温度）
                 */
                GACross.doMyGACross(mList, crossRatio, MTK);
                GACross.doMyGACross(fList, crossRatio, FTK);
                /**
                 * 变异过程(整体变异率还没有改变)
                 */
                GAMutation.doMyGAMutation(mList, mMutationRatio, dGeneration,fMaxScore,neighborRatio);
                GAMutation.doMyGAMutation(fList, fMutatioinRatio, dGeneration,mMaxScore,neighborRatio);

                /**
                 * 退火温度设置
                 */
                if (GADecode.getMaxScore(mList) == fMaxScore) {
                    mSameGeneration++;
                    if (mSameGeneration >= TKGeneration) {
                        MTK *= TKDecline;
                        mSameGeneration = 0;
                    }
                }
                if (GADecode.getMaxScore(fList) == mMaxScore) {
                    fSameGeneration++;
                    if (fSameGeneration >= TKGeneration) {
                        FTK *= TKDecline;
                        fSameGeneration = 0;
                        mMutationRatio+=1/maxGeneration;
                        fMutatioinRatio+=1/maxGeneration;
                    }
                }else{
                    mMutationRatio-=1/maxGeneration;
                    fMutatioinRatio-=1/maxGeneration;
                }
            } else {
                break outer;
            }
            System.out.println("当前代数：" + dGeneration + " f最佳染色体得分：" + GADecode.getMaxScore(fList) + " m最佳染色体得分：" + GADecode.getMaxScore(mList));
            list.add(Math.max(GADecode.getMaxScore(fList), GADecode.getMaxScore(fList)));
            dGeneration++;
        }
        TK=(MTK+FTK)/2;
//        mutationRatio=(fMutatioinRatio+mMutationRatio)/2;
        /**
         * 种内遗传完成，开始种间遗传操作
         */
        tList.addAll(mList);
        tList.addAll(fList);

        while (dGeneration < maxGeneration) {
            /**
             * 计算适应度
             */
            GAFitness.allFitness(tList);
            int maxScore = GADecode.getMaxScore(tList);//获取最好的适应度
            /**
             * 染色体选择
             */
//            selection.setOldList(tList);
            selection.jinSelection(tList);//使用赌轮选择法
            selection.neiborCare(tList, selection.getNeiborRatio());
            /**
             * 交叉
             */
            GACross.doMyGACross(tList, crossRatio, TK);
            /**
             * 变异
             */
            GAMutation.doMyGAMutation(tList, mutationRatio, dGeneration,maxScore,neighborRatio);
            if (GADecode.getMaxScore(mList) == maxScore) {
                sameGeneration++;
                if (sameGeneration >= TKGeneration) {
                    TK *= TKDecline;
                    sameGeneration = 0;
                    mutationRatio+=1/maxGeneration;
                }
            }else{
                mutationRatio-=1/maxGeneration;
            }
            System.out.println("当前代数：" + dGeneration + " 整合后最佳分数：" + GADecode.getMaxScore(tList)+"平均得分："+GADecode.getAvgScore(tList));
            list.add(GADecode.getMaxScore(tList));
            dGeneration++;
        }


    }

    @Test
    public void doSGA(List<Integer> list) {//SGA的每一代都会开启精英原则
        initSGA();
        int dGeneration = 0;//当前代数
        int sameGeneration = 0;//相似染色体代数
        GASelection selection = new GASelection();//选择器
        selection.setFlag(false);
        while (dGeneration < SGAmaxGeneration) {
            /**
             * 计算适应度
             */
            GAFitness.allFitness(SGAList);
            int maxScore = GADecode.getMaxScore(SGAList);//获取最好的适应度
            /**
             *染色体选择
             */
            selection.jinSelection(SGAList);
            selection.idSort(SGAList);
            /**
             * 染色体交叉
             */
            GACross.doSGACross(SGAList, SGACrossRatio);
            /**
             * 染色体变异
             */
            GAMutation.doSGAMutation(SGAList, SGAMutationRatio);


            if (GADecode.getMaxScore(SGAList) == maxScore) {
                sameGeneration++;
                if (sameGeneration >= TKGeneration) {
                    TK *= TKDecline;
                    sameGeneration = 0;
                }
            }
            System.out.println("当前代数：" + dGeneration + " 最好染色体得分：" + GADecode.getMaxScore(SGAList)+"平均得分："+GADecode.getAvgScore(SGAList));
            list.add(GADecode.getMaxScore(SGAList));
            dGeneration++;

        }
//        System.out.println("最佳染色体种群："+SGAList);

    }

    @Test
    public void doMGA(List<Integer> list,List<Integer> generationList) {
        initMGA();
        int dGeneration = 0;//当前代数
        int sameGeneration = 0;//相似染色体代数
        GASelection selection = new GASelection();
        selection.setFlag(true);
        while (dGeneration < MGAmaxGeneration) {
            /**
             * 计算适应度
             */
            GAFitness.allFitness(MGAList);
            int maxScore = GADecode.getMaxScore(MGAList);//获取最好的适应度
            /**
             *染色体选择
             */
            selection.jinSelection(MGAList);
            selection.neiborCare(MGAList, selection.getNeiborRatio());
            /**
             * 染色体交叉
             */
            GACross.doMGACross(MGAList, MGACrossRatio, TK);
            /**
             * 染色体变异
             */
            GAMutation.doMGAMutation(MGAList, MGAMutationRatio,neighborRatio);


            if (GADecode.getMaxScore(MGAList) == maxScore) {
                sameGeneration++;
                if (sameGeneration >= TKGeneration) {
                    TK *= TKDecline;
                    sameGeneration = 0;
                }
            }
            System.out.println("当前代数：" + dGeneration + " 最好染色体得分：" + GADecode.getMaxScore(MGAList)+"平均得分："+GADecode.getAvgScore(MGAList));
            list.add(GADecode.getMaxScore(MGAList));
            generationList.add(dGeneration);
            dGeneration++;
        }
    }

    @Test
    public void doAllGA() {
        List<Integer> MyGAList = new ArrayList<Integer>();
        List<Integer> generationList = new ArrayList<Integer>();
        List<Integer> SGAList = new ArrayList<Integer>();
        List<Integer> MGAList = new ArrayList<Integer>();
        doMyGA(MyGAList);
        doSGA(SGAList);
        doMGA(MGAList,generationList);
        String[] rowKeys = {"MyGA","SGA", "MGA"};
        Integer[] colKeys = new Integer[generationList.size()];
        generationList.toArray(colKeys);
        double[][] data=new double[3][MGAList.size()];
        for(int i=0;i<MyGAList.size();i++){
            data[0][i]=MyGAList.get(i);
        }
        System.out.println(SGAList);
        System.out.println(MGAList);
        for(int i=0;i<SGAList.size();i++){
            data[1][i]=SGAList.get(i);
        }
        for(int i=0;i<MGAList.size();i++){
            data[2][i]=MGAList.get(i);
        }
        GAChart.doChart(rowKeys, colKeys, data);
        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        GASimulation simulation=new GASimulation();
//        simulation.doAllGA();
        Graph graph=GANetwork.test();
//        System.out.println(Arrays.toString(GraphResolve.getMergeNode(GraphResolve.reverseGraph(GraphResolve.changeToLinearGraph(graph)),false).toArray()));
//        System.out.println(GraphResolve.getGraphA(graph,2,GraphResolve.changeToLinearGraph(graph).getGLists().length).toString());
        GraphResolve.getGraphB(graph,2,10);

    }
}
