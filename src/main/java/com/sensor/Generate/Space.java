package com.sensor.Generate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.sensor.utils.produceNodeData;
public class Space {
	
	List<SensorNodes> allNodes;
	double x_loc;
	double y_loc;
	int destoryNode;
	public Space() {

	}
	public Space(double x_loc,double y_loc) {
		// TODO 自动生成的构造函数存根
		this.x_loc=x_loc;
		this.y_loc=y_loc;
	}
//	随机单点损坏策略
	public void destory(List<SensorNodes> allNodes){
		Random random = new Random();
		int random_des=random.nextInt(allNodes.size());
		if (random_des==1) {
			int random_desid=random.nextInt(allNodes.size());
			if(allNodes.get(random_desid).state==1) {
				allNodes.get(random_desid).Off();
//				System.out.println("第"+random_desid+"号节点被无情的损坏");
			}
			else {
//				System.out.println("第"+random_desid+"号节点又双叒被无情的损坏了一次");
			}
		}
		else {
//			System.out.println("本轮次节点全部安全");
		}
	}
	
//	随机集中损坏策略
	public void destoryConcen(List<SensorNodes> allNodes){
		Random random = new Random();
		int random_des=random.nextInt(allNodes.size());
		if (random_des==1) {
			int random_desid=random.nextInt(allNodes.size());
			if(allNodes.get(random_desid).state==1) {
				allNodes.get(random_desid).Off();
				for(SensorNodes sensorNodes:allNodes.get(random_desid).neigh){
					if(sensorNodes.state==1) {
						allNodes.get(random_desid).Off();
					}
				}
//				System.out.println("第"+random_desid+"号节点被无情的损坏");
			}
			else {
//				System.out.println("第"+random_desid+"号节点又双叒被无情的损坏了一次");
			}
		}
		else {
//			System.out.println("本轮次节点全部安全");
		}
	}	

//	模拟随机一个sinkNode
	public SinkNodes buildSinkNode() {
		Random random = new Random();
		Point point=new Point();
		point.x_loc=random.nextInt((int)this.x_loc)+random.nextDouble();
		point.y_loc=random.nextInt((int)this.y_loc)+random.nextDouble();
		List<SensorNodes> neigh=new ArrayList<SensorNodes>();
		List<Packets> x_Packets=new ArrayList<Packets>();
		List<Packets> y_Packets=new ArrayList<Packets>();
		SinkNodes sinkNodes=new SinkNodes(point,neigh,x_Packets,y_Packets,1);
		return sinkNodes;
	}
	
//	模拟均匀进行撒节点并且进行初始化
	public List<SensorNodes> buildNode(int sensorNUm) {
		List<SensorNodes> allnodes=new ArrayList<SensorNodes>();
		produceNodeData produceNodeData=new produceNodeData();
		int id = 0;
		Random random=new Random();
		for(int k = 0;k < sensorNUm;k++) {
				Point point=new Point();
				point.x_loc=random.nextInt((int)this.x_loc)+random.nextDouble();
				point.y_loc=random.nextInt((int)this.y_loc)+random.nextDouble();
				int data=produceNodeData.returnNodeData(5);
				List<Integer> id_sets=new ArrayList<Integer>();
				id_sets.add(id);
				Packets packets=new Packets(id_sets,data);
				List<Packets> space=new ArrayList<Packets>();
				space.add(packets);
				NodeStat nodeStat=new NodeStat(0, 0);
				List<SensorNodes> neigh=new ArrayList<SensorNodes>();
				SensorNodes sensor_temp=new SensorNodes(id,data,point,nodeStat,space,neigh,1);
				allnodes.add(sensor_temp);
				id+=1;	
			}		
		return allnodes;
	}
}
