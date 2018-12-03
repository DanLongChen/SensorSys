package com.sensor.GATools;

import com.sensor.entity.Chromosome;
import com.sensor.entity.Gene;
import com.sun.deploy.panel.ITreeNode;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DanLongChen on 2018/11/27
 * 变异之后再整理一次neibor和trust
 * 这里调整信赖域的K值暂且定为染色体中的总位数的0.2
 **/
public class GAMutation {
    /***
     *
     * @param list（染色体数组）
     * @param Md（整体变异概率）
     * @param generation（当前代数）
     */
    public static void doMutation(List<Chromosome> list, double Md,int generation){
        int K=(int) (GAGetChromosomeLength.getLength(list.get(0))*0.1);
        for(Chromosome chromosome:list){//对染色体中的每一条染色体执行变异，然后调整其变异率
            double ratio=Math.random();
            if(ratio<=chromosome.getRatio()){//与染色体各自的变异率进行比较
                int geneListPoint=(int)Math.floor(chromosome.getList().size()*Math.random());//寻找变异的基因位置
                Gene gene=chromosome.getList().get(geneListPoint);
                int genePoint=(int)Math.floor(gene.getList().size()*Math.random());//寻找基因中需要变异的基因位
                boolean temp=gene.getList().get(genePoint);
                if(temp){
                    gene.getList().set(genePoint,false);
                }else{
                    gene.getList().set(genePoint,true);
                }
            }
            chromosome.setRatio(Md+0.02*(1-chromosome.getScore()/GADecode.getMaxScore(list))+0.01*1/generation);//按照公式变更变异率
        }
        /***
         * 调整染色体数组的邻居列表和信赖值（需要在全部变异完之后进行）
         */
        for (Chromosome chromosome:list){
            int ownScore=GADecode.getScore(chromosome);//获取自身的score
            for(int i=0;i<chromosome.getNlist().size();i++){
                int neiborScore=GADecode.getScore(list.get(GAFindChromosomeById.findById(list,chromosome.getNlist().get(i))));
                if(Math.abs(ownScore-neiborScore)<=K){//汉明距离在范围之内
                    //########################################可以做下实验，验证下 chromosome.getTrust().set(i,chromosome.getTrust().get(i))这样能否成功

                    double temp=chromosome.getTrust().get(i);
                    if(temp>=1.0){
                        //因为信赖域是-1~1，所以大于1的时候，不做任何事情
                    }else{
                        chromosome.getTrust().set(i,temp+0.1);
                    }
                }else{
                    double temp=chromosome.getTrust().get(i);
                    chromosome.getTrust().set(i,temp-0.2);
                }
                /**
                 * 在设置完信赖域之后，如果满足剔除条件的则剔除，然后随机选择新的染色体加入
                 */
                if (chromosome.getTrust().get(i)<-1){
                    int position=(int) Math.floor(Math.random()*list.size());
                    int ID=list.get(position).getId();
                    chromosome.getNlist().set(i,ID);//随机挑选染色体加入邻居队列
                    chromosome.getTrust().set(i,1.0);//重新设置信赖域值为1.0
                }
            }

        }
    }
    @Test
    public  void test(){
        List<Chromosome> list = new ArrayList<Chromosome>();
        GATestTools.produceData(list,0.2);
        System.out.println(list);
        GAMutation.doMutation(list,1,50);
        System.out.println(list);
    }

}
