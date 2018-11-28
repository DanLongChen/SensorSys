package com.sensor.Generate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
public class SinkNodes extends Nodes{
	List<Packets> x_Packets;//已经解码的包,全是一度包
	List<Packets> y_Packets;//未解码的包
	List<Integer> z_Packets;//已经解码后的节点id
	int radius;//定义一个节点的邻居半径
	int type=1;
	public SinkNodes() {
		this.type=type;

	}
	public SinkNodes(Point point,List<SensorNodes> neigh,List<Packets> x_Packets,List<Packets> y_Packets,int state) {
		this.point=point;
		this.neigh=neigh;
		this.x_Packets=x_Packets;
		this.y_Packets=y_Packets;
		this.radius=radius;
		this.state=state;
	}		
//	sinknode检测邻居
	public void DetectNeighbors(List<SensorNodes> sensorNodes2,int radius){
		for(SensorNodes sensorNodes:sensorNodes2) {
			if (sensorNodes.state==1) {
				double _x = Math.abs(this.point.x_loc - sensorNodes.point.x_loc);
				double _y = Math.abs(this.point.y_loc - sensorNodes.point.y_loc);
				double distance=Math.sqrt(_x*_x+_y*_y);
				if (distance<=radius && sensorNodes.state==1) {
					this.neigh.add(sensorNodes);
				}
			}
		}
	}
	//更新自己的邻居节点
	public void RefreshNeighbors(){
		 Iterator<SensorNodes> iterator = this.neigh.iterator();
	     while (iterator.hasNext()) {
	    	 SensorNodes temp = iterator.next();
	         if (0==temp.state) {
	                iterator.remove();
	         }
	     }
	}
//	新的距离计算
	public int getDistanceDeg(Packets packets) {
		int num=0;
		for(Integer integer:packets.id_sets) {
			for(Packets packets2:this.x_Packets) {
				if (packets2.id_sets.contains(integer)) {
					num+=1;
				}
			}
		}
//		正好能解码
		if (num==packets.id_sets.size()-1) {
			return 1;
		}
//		正好在已经解码队列中存在了这些编码
		else if(num==packets.id_sets.size()){
			return 0;
		}
//		没法解码
		return -1;	
	}
	
//	新的距离计算
	public int getDistanceDeg(List<Packets> packets1,Packets packets) {
		int num=0;
		for(Integer integer:packets.id_sets) {
			for(Packets packets2:packets1) {
				if (packets2.id_sets.contains(integer)) {
					num+=1;
				}
			}
		}
//		正好能解码
		if (num==packets.id_sets.size()-1) {
			return 1;
		}
//		正好在已经解码队列中存在了这些编码
		else if(num==packets.id_sets.size()){
			return 0;
		}
//		没法解码
		return -1;	
	}	
	
	
//	新的判断是否有这个包了
	public boolean contain(Packets packets) {
		int temp_num=0;
		for(Integer id:packets.id_sets) {
			for(Packets packets3:this.x_Packets) {
				if (packets3.id_sets.contains(id)) {
					temp_num+=1;
				}
			}
		}
		if (temp_num==packets.id_sets.size()) {
				return true;
			}
		return false;
	}

	

//	单播时随机选择一个邻居
	public SensorNodes RandSelectSensorNodes(){
		Random random = new Random();
		int maxRound=100;
		int i=0;
		while(true) {
			if (this.neigh.size()==0) {
//				System.out.println("没有邻居了");
				return null;
			}
			else {
				int random_neigh=random.nextInt(this.neigh.size());
				SensorNodes sensorNodes=this.neigh.get(random_neigh);
				if (sensorNodes.state==1) {
					return sensorNodes;
				}
				else {
					i++;
					if (i>maxRound) {
						return null;
					}
					else {
						continue;
					}
				}
			}
		}
	}
//	随机选择一个包，有度限制，并且返回这个包（还未源节点的异或）,保证传过来都是能用的,要是符合条件就返回结合后的包，要是不符合就返回原包
	public Packets RandSelectPacket(int degree){
		Random random = new Random();
		int maxRound=10;
		int i=0;
		while (i<maxRound) {
			i++;
			int random_pac=random.nextInt(this.space.size()-1);
			random_pac=random_pac+1;
				if (degree==1)  {
					return this.space.get(random_pac);
				}
				else {
					if(this.space.get(random_pac).getDegree()<degree && this.space.get(random_pac).returnIntersection(this.space.get(0))) {
						return this.space.get(random_pac).combinPac(this.space.get(0));
					}
					else{
						return this.space.get(random_pac);
					}
				}
		}
		return null;
	}

//	
	public Map<Integer, List<Packets>> recoverNode(List<Packets> paca,List<Packets> pacblist) {
		
		Packets tempPac=new Packets();
		Map<Integer, List<Packets>> result=new HashMap<Integer, List<Packets>>();
		boolean flags=false;
		while(!flags) {
			List<Packets> newpacblist=new ArrayList<Packets>();
			flags=true;
			for(Packets pacb:pacblist) {
				if (getDistanceDeg(paca,pacb)==1) {
					int flag=0;
					for(Packets packets2:paca) {
						int id=packets2.id_sets.get(0);
						if (pacb.id_sets.contains(id)) {
							pacb.data=pacb.data^packets2.data;
							pacb.id_sets.remove(id);
							flag++;
						}
					}
					if (flag==pacb.id_sets.size()-1)
					{
						flags=false;
						paca.add(pacb);	
					}
					else {
						System.out.println("ojbk");
						newpacblist.add(pacb);
					}
					
				}
			}	
			pacblist=newpacblist;	
		}
		result.put(0, paca);
		result.put(1, pacblist);
		return result;
	}
	

	
	
	
//	接收新的编码并且进行解码
	public Map<Integer,Object> RevNodes(){
		boolean flags=false;
		Map<Integer, Object> map=new HashMap<Integer, Object>();
		
		SensorNodes sensorNodes=this.RandSelectSensorNodes();
		map.put(0, sensorNodes.id);
		if (sensorNodes!=null) {
			Packets packets=sensorNodes.RERandSelectPacket();
			if (packets.getDegree()==1) 
			{
//				返回true说明是已经包含这个包了
				if(this.contain(packets)) 
				{
					map.put(1, 0);
					return map;
//					System.out.println("已经解码的队列中已经有了这个包");
				}
				else {
					this.x_Packets.add(packets);
					flags=true;
					map.put(1, packets.data);
					return map;
				}
			}
			else {
				if (this.x_Packets==null) {
					Packets packets2=new Packets();
					packets2.id_sets=packets.id_sets;
					this.y_Packets.add(packets2);
					map.put(1, packets.data);
					return map;

				}
				else {
					if (this.getDistanceDeg(packets)==1) {
						
						int resultData = 0;
						for(Integer integer:packets.id_sets) {
							int flag=0;
							Packets temp_x=new Packets();
							for(Packets packets2:this.x_Packets) {
								if (packets2.id_sets.contains(integer)) {
									resultData=packets.data^packets2.data;
									continue;
								}
								else {
									flag++;
								}
							}
//							data还没放进去
							if (flag==this.x_Packets.size()) {
								temp_x.id_sets.add(integer);
								temp_x.data=resultData;
								this.x_Packets.add(temp_x);
								flags=true;
							}
						}
						map.put(1, packets.data);
						return map;
					}
					
					else if(this.getDistanceDeg(packets)==-1){
						this.y_Packets.add(packets);  
						map.put(1, packets.data);
						return map;
					}
					else {
						map.put(1, packets.data);
						return map;
//						System.out.println("X队列中已经有包了,抛弃");
					}
				}
			}
		}	
		if (flags) {
			Map<Integer, List<Packets>>  result=recoverNode(this.x_Packets, this.y_Packets);
			this.x_Packets=result.get(0);
			this.y_Packets=result.get(1);
		}
		map.put(1, 0);
		return map;
	}

	
}
