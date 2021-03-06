package com.sensor.GATools;

import com.sensor.entity.Chromosome;
import com.sensor.entity.Gene;
import com.sensor.entity.MergeNode;

import java.util.Arrays;
import java.util.List;

/**
 * Created by DanLongChen on 2018/11/26
 **/
public class GAInit {
    /***
     *
     * @param list（染色体数组）
     * @param population（初始化的人口数）
     * @param mergeNodeList（节点数）
     */
    public static void SGAInit(List<Chromosome> list, int population, List<MergeNode> mergeNodeList) {
        int last=0;
        for (int i = 0; i < population*0.9; i++) {
            Chromosome SGAChromosome = new Chromosome(i, 0);
            for (int j = 0; j < mergeNodeList.size(); j++) {
                if (mergeNodeList.get(j).out != 0) {
                    Gene gene = new Gene(mergeNodeList.get(j).id,mergeNodeList.get(j).in, mergeNodeList.get(j).out);//输入in和out，以后用数组代替
                    gene.Init();//初始化基因
                    SGAChromosome.getList().add(gene);
                }
            }
            list.add(SGAChromosome);
            last=i;
        }
        /**
         * 添加全为1的染色体
         */
        for(int j=last+1;j<population;j++){

            Chromosome add = new Chromosome(j, 0);
            for (int i = 0; i < mergeNodeList.size(); i++) {
                if(mergeNodeList.get(i).out!=0) {
                    Gene gene = new Gene(mergeNodeList.get(i).id,mergeNodeList.get(i).in, mergeNodeList.get(i).out);
                    for (int out = 0; out < gene.getGeneOut(); out++) {
                        Boolean[] in = new Boolean[gene.getGeneIn()];
                        for (int gIn = 0; gIn < gene.getGeneIn(); gIn++) {
                            in[gIn] = true;
                        }
                        gene.getList().addAll(Arrays.asList(in));
                    }
                    add.getList().add(gene);
                }
            }
            list.add(add);
        }
    }

    /***
     * 初始化种群
     * @param list（种群数组）
     * @param population（人口数量）
     * @param mergeNodeList（传入的网络信息）
     * @param ratio（个体初始化变异率）
     */
    public static void initPopulation(List<Chromosome> list, int population, List<MergeNode> mergeNodeList, double ratio) {
        int last=0;
        for (int i = 0; i < population*0.9; i++) {//初始化种群的染色体
            Chromosome mchromosome = new Chromosome(i, ratio);
            for (int j = 0; j < mergeNodeList.size(); j++) {
                if (mergeNodeList.get(j).out != 0) {
                    Gene gene = new Gene(mergeNodeList.get(j).id,mergeNodeList.get(j).in, mergeNodeList.get(j).out);//输入in和out，以后用数组代替
                    gene.Init();//初始化基因
                    mchromosome.getList().add(gene);
                }
            }
            list.add(mchromosome);
            last=i;
        }

        for(int j=last+1;j<population;j++){

            Chromosome add = new Chromosome(j, 0);
            for (int i = 0; i < mergeNodeList.size(); i++) {
                if(mergeNodeList.get(i).out!=0) {
                    Gene gene = new Gene(mergeNodeList.get(i).id,mergeNodeList.get(i).in, mergeNodeList.get(i).out);
                    for (int out = 0; out < gene.getGeneOut(); out++) {
                        Boolean[] in = new Boolean[gene.getGeneIn()];
                        for (int gIn = 0; gIn < gene.getGeneIn(); gIn++) {
                            in[gIn] = true;
                        }
                        gene.getList().addAll(Arrays.asList(in));
                    }
                    add.getList().add(gene);
                }
            }
            list.add(add);
        }
    }

    /***
     * 作为测试，加入全为1的染色体，原则上这个时候网络要能达到最大流
     * @param list
     * @param population
     * @param mergeNodeList
     * @param ratio
     */
    public static void testPopulation(List<Chromosome> list, int population, List<MergeNode> mergeNodeList, double ratio) {
        for (int i = 0; i < population; i++) {//初始化种群的染色体
            Chromosome mchromosome = new Chromosome(i, ratio);
            for (int j = 0; j < mergeNodeList.size(); j++) {
                if (mergeNodeList.get(j).out != 0) {
                    Gene gene = new Gene(mergeNodeList.get(j).id,mergeNodeList.get(j).in, mergeNodeList.get(j).out);//输入in和out，以后用数组代替
                    for (int out = 0; out < gene.getGeneOut(); out++) {
                        Boolean[] in = new Boolean[gene.getGeneIn()];
                        for (int gIn = 0; gIn < gene.getGeneIn(); gIn++) {
                            in[gIn] = true;
                        }
                        gene.getList().addAll(Arrays.asList(in));
                    }
                    mchromosome.getList().add(gene);
                }
            }
            list.add(mchromosome);
        }
    }

    /***
     * 初始化种群的邻居队列和信赖域
     * @param list（种群数组）
     * @param neighborRatio（邻居占种群个数的比例）
     */
    public static void initNeighbor(List<Chromosome> list, double neighborRatio) {
        int neighborNum = (int) Math.round(list.size() * neighborRatio);//获得染色体邻居队列的个数
        for (Chromosome chromosome : list) {//对于染色体数组中的每一条染色体来说
            for (int i = 0; i < neighborNum; i++) {
                int neibor = list.get((int) Math.floor(Math.random() * list.size())).getId();
                addNeighborToChromosome(chromosome, neibor);
            }
        }
    }

    public static void addNeighborToChromosome(Chromosome chromosomeA, int point) {
        chromosomeA.getNlist().add(point);//填充邻居列表，同时初始化信赖域为1
        chromosomeA.getTrust().add(0.0);//填充信赖域（与邻居列表是一一对应的）
    }
}
