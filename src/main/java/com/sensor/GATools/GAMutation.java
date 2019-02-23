package com.sensor.GATools;

import com.sensor.entity.Chromosome;
import com.sensor.entity.Gene;
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
    public static void doMyGAMutation(List<Chromosome> list, double Md,int generation,int dGenerationMaxScore,double neiborRatio){
        /***
         * 这个汉明距离有待商榷
         */
        int K=(int) (GAGetChromosomeLength.getLength(list.get(0))*0.1);//韩明重量的范围值
        for(Chromosome chromosome:list){//对染色体中的每一条染色体执行变异，然后调整其变异率
            double ratio=Math.random();
            if(ratio<=chromosome.getRatio()){//与染色体各自的变异率进行比较
                int geneListPoint=(int)Math.floor(chromosome.getList().size()*Math.random());//寻找变异的基因位置
                Gene gene=chromosome.getList().get(geneListPoint);
                int genePoint=(int)Math.floor(gene.getList().size()*Math.random());//寻找基因中需要变异的基因位
//                System.out.println("变异的位置："+geneListPoint+" "+genePoint);
                boolean temp=gene.getList().get(genePoint);
                if(temp){
                    gene.getList().set(genePoint,false);
                }else{
                    gene.getList().set(genePoint,true);
                }
            }
//            System.out.println(Md+0.03*(Math.abs(1-chromosome.getScore()/GADecode.getMaxScore(list)))+0.02*1/generation);
//            chromosome.setRatio(Md+0.03*(Math.abs(1-GADecode.getMaxScore(list)/chromosome.getScore()))+0.02*1/generation);//按照公式变更变异率
            chromosome.setRatio(Md+0.01*(Math.abs(1-dGenerationMaxScore/(chromosome.getScore()+1)))+0.01*1/generation);//按照公式变更变异率
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
                 * 在设置完信赖域之后，如果满足剔除条件的则剔除（同样双方都要剔除），然后随机选择新的染色体加入
                 */
                if (chromosome.getTrust().get(i)<-1){
                    int neiborNumber=chromosome.getNlist().get(i);
                    chromosome.getNlist().remove(i);
                    chromosome.getTrust().remove(i);
                    list.get(neiborNumber).getTrust().remove(list.get(neiborNumber).getNlist().indexOf(chromosome.getId()));//得到i在Nlist中的下标，然后从邻居的Trust中移除
                    list.get(neiborNumber).getNlist().remove(chromosome.getId());
                }
            }
            if (chromosome.getNlist().size()<list.size()*neiborRatio){
                /**
                 * 同时被加进去的邻居也要加入当前染色体作为邻居
                 */
                while (chromosome.getNlist().size()<list.size()*neiborRatio){
                    int neiborAddPoint=(int) Math.floor(Math.random()*list.size());
                    if(chromosome.getNlist().contains(neiborAddPoint) || neiborAddPoint==chromosome.getId()){//若有重复则重新插入
                        continue;
                    }
                    /***
                     * 双方都要插入（a是b的邻居，那么b也是a的邻居）
                     */
                    chromosome.getNlist().add(neiborAddPoint);//当前的染色体加入其他染色体作为邻居
                    chromosome.getTrust().add(0.0);

                    list.get(neiborAddPoint).getNlist().add(list.indexOf(chromosome));//邻居把当前染色体加入
                    list.get(neiborAddPoint).getTrust().add(0.0);

                }
            }
        }
        GADecode.setAllScore(list);//重新计算score
    }

    /***
     *
     * @param list（染色体数组）
     * @param Md（整体变异概率）
     */
    public static void doSGAMutation(List<Chromosome> list,double Md){
        for(Chromosome chromosome:list){//对染色体中的每一条染色体执行变异，然后调整其变异率
            double ratio=Math.random();
            if(ratio<=Md){//与染色体各自的变异率进行比较
                int geneListPoint=(int)Math.floor(chromosome.getList().size()*Math.random());//寻找变异的基因位置
                Gene gene=chromosome.getList().get(geneListPoint);
                int genePoint=(int)Math.floor(gene.getList().size()*Math.random());//寻找基因中需要变异的基因位
//                System.out.println("变异的位置："+geneListPoint+" "+genePoint);
                boolean temp=gene.getList().get(genePoint);
                if(temp){
                    gene.getList().set(genePoint,false);
                }else{
                    gene.getList().set(genePoint,true);
                }
            }else{
                continue;
            }
        }
        GADecode.setAllScore(list);//重新计算得分
    }

    /***
     *
     * @param list（染色体数组）
     * @param Md（整体变异概率）
     * @param neiborRatio （邻居比例）
     */
    public static void doMGAMutation(List<Chromosome> list,double Md,double neiborRatio){
        int K=(int) (GAGetChromosomeLength.getLength(list.get(0))*0.1);//韩明重量的范围值
        for(Chromosome chromosome:list){//对染色体中的每一条染色体执行变异
            double ratio=Math.random();
            if(ratio<=Md){//与总体染色体变异概率相比较
                int geneListPoint=(int)Math.floor(chromosome.getList().size()*Math.random());//寻找变异的基因位置
                Gene gene=chromosome.getList().get(geneListPoint);
                int genePoint=(int)Math.floor(gene.getList().size()*Math.random());//寻找基因中需要变异的基因位
//                System.out.println("变异的位置："+geneListPoint+" "+genePoint);
                boolean temp=gene.getList().get(genePoint);
                if(temp){
                    gene.getList().set(genePoint,false);
                }else{
                    gene.getList().set(genePoint,true);
                }
            }
        }
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
                 * 在设置完信赖域之后，如果满足剔除条件的则剔除（同样双方都要剔除），然后随机选择新的染色体加入
                 */
                if (chromosome.getTrust().get(i)<-1){
                    int neiborNumber=chromosome.getNlist().get(i);
                    chromosome.getNlist().remove(i);
                    chromosome.getTrust().remove(i);
                    list.get(neiborNumber).getTrust().remove(list.get(neiborNumber).getNlist().indexOf(chromosome.getId()));//得到i在Nlist中的下标，然后从邻居的Trust中移除
                    list.get(neiborNumber).getNlist().remove(chromosome.getId());
                }
            }
            if (chromosome.getNlist().size()<list.size()*neiborRatio){
                /**
                 * 同时被加进去的邻居也要加入当前染色体作为邻居
                 */
                while (chromosome.getNlist().size()<list.size()*neiborRatio){
                    int neiborAddPoint=(int) Math.floor(Math.random()*list.size());
                    if(chromosome.getNlist().contains(neiborAddPoint) || neiborAddPoint==chromosome.getId()){//若有重复则重新插入
                        continue;
                    }
                    /***
                     * 双方都要插入（a是b的邻居，那么b也是a的邻居）
                     */
                    chromosome.getNlist().add(neiborAddPoint);//当前的染色体加入其他染色体作为邻居
                    chromosome.getTrust().add(0.0);

                    list.get(neiborAddPoint).getNlist().add(list.indexOf(chromosome));//邻居把当前染色体加入
                    list.get(neiborAddPoint).getTrust().add(0.0);

                }
            }
        }
        GADecode.setAllScore(list);//重新计算score
    }
    @Test
    public  void test(){
        List<Chromosome> list = new ArrayList<Chromosome>();
        GATestTools.produceData(list,0.2);
        System.out.println(list);
//        GAMutation.doMyGAMutation(list,1,10);
        System.out.println(list);
    }

}
