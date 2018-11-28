package com.sensor.Generate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SensorNodes extends Nodes{
	
	int type=0;
	public SensorNodes() {
		// TODO Auto-generated constructor stub
		this.type=type;
	}
	public SensorNodes(int id,int data,Point point,NodeStat nodeStat,List<Packets> space,List<SensorNodes> neigh,int state) {
		// TODO Auto-generated constructor stub
		super(id, data,point, nodeStat, space, neigh, state);
		this.type=type;
	}

//	定义一个判断顺序找还是纯随机找的方法
	public Packets RERandSelectPacket(){
		//0为顺序找，1为随机找
		Random random = new Random();
		int random_pac=random.nextInt(this.space.size());
		return this.space.get(random_pac);	
	}	
	
	
//	GC  随机选择一个包，有度限制，并且返回这个包（还未与源节点进行异或）,保证传过来都是能用的,要是符合条件就返回结合后的包，要是不符合就返回原包
	public Packets GCRandSelectPacket(int degree){
//		degree为0则为随机找，不为0则为有条件找
		Random random = new Random();
		int maxRound=this.space.size();
		int i=0;
		while (i<maxRound) {
			i++;
			if (maxRound==1) {
				return this.space.get(0);
			}
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
	
	

	
//	发送包
	public Packets Sendpkt(int degree){
		return GCRandSelectPacket(degree);
	}
//	接收包
	public void Recvpkt(Packets packets){

	}
//	搜索所有节点并且将活的邻居节点加入到自己的邻居内
	public void DetectNeighbors(List<SensorNodes> sensorNodes2,int radius){
		for(SensorNodes sensorNodes:sensorNodes2) {
			if (sensorNodes.state==1) {
				double _x = Math.abs(this.point.x_loc - sensorNodes.point.x_loc);
				double _y = Math.abs(this.point.y_loc - sensorNodes.point.y_loc);
				double distance=Math.sqrt(_x*_x+_y*_y);
				if (distance<=radius && sensorNodes!=this) {
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


	
//	广播时对一批邻居进行选择
	public List<SensorNodes> RandSelectNeigh(List<SensorNodes> allNodes) {
		List<SensorNodes> neigh=new ArrayList<SensorNodes>();
		Random random=new Random();
		int neighNum=random.nextInt(15);
		for(int i=0;i<neighNum;i++) {
			int neighid=random.nextInt(allNodes.size());
			if (allNodes.get(neighid).state==1&&allNodes.get(neighid).id!=this.id) {
				neigh.add(allNodes.get(neighid));
			}
		}
		return neigh;
	}
	
//	单播时随机选择一个邻居
	public SensorNodes RandSelectSensorNodes(){
		Random random = new Random();
		int maxRound=100;
		int i=0;
		while(true) {
			if (this.neigh.size()==0) {
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
	
//	单播方式，暂时考虑的是随机选出一个邻居节点，然后把自己的一个包与邻居的任一一个包进行结合，并且最后返回是这个邻居节点
	public Map<Integer,Object> Unicast(int degree){
		Map<Integer,Object> map=new HashMap<Integer,Object>();
	
		if(this.state==1) {
			Packets packetsSend=this.GCRandSelectPacket(degree);
			SensorNodes sensorRev=this.RandSelectSensorNodes();

			if (sensorRev==null) {
				map.put(0, -1);
				map.put(1, 0);
				map.put(2, 0);
				return map;
			}
			else {
				map.put(0, sensorRev.id);
				Packets packetsRev=sensorRev.GCRandSelectPacket(degree);
				int indexSend=this.space.indexOf(packetsSend);
				int indexRev=sensorRev.space.indexOf(packetsRev);
				if (packetsSend==null) {
					sensorRev.nodeStat.nsend_++;
					this.space.set(indexSend,packetsRev);
					map.put(1, 0);
					map.put(2, packetsRev.data);
					return map;
				}
				else {
					if (degree==1) {
						sensorRev.space.set(indexRev, packetsSend);
						this.space.set(indexSend,packetsRev);
						sensorRev.nodeStat.nrecv_++;
						this.nodeStat.nsend_++;
						this.nodeStat.nrecv_++;
						sensorRev.nodeStat.nsend_++;
						map.put(1, packetsSend.data);
						map.put(2, packetsRev.data);
						return map;
						}
					else {
						sensorRev.space.set(indexRev, packetsSend);
						this.space.set(indexSend,packetsRev);
						sensorRev.nodeStat.nrecv_++;
						this.nodeStat.nsend_++;
						map.put(1, packetsSend.data);
						map.put(2, packetsRev.data);
						return map;

						}
					}
			}
		}
		map.put(0, -1);
		map.put(1, 0);
		map.put(2, 0);
		return map;
	}
	
//	广播方式，在选择的邻居列表内进行单播
	public SensorNodes Broadcast(int degree,List<SensorNodes> allNodes){
		Packets packetsSend=this.GCRandSelectPacket(degree);
		List<SensorNodes> neigh=this.RandSelectNeigh(allNodes);
		for(SensorNodes sensorNodes:neigh) {
			Packets packetsRev=sensorNodes.RERandSelectPacket();
			Packets newpacketRev=packetsRev.combinPac(packetsSend);
			sensorNodes.space.remove(packetsRev);
			sensorNodes.space.add(newpacketRev);
			this.nodeStat.nsend_+=1;
			sensorNodes.nodeStat.nrecv_+=1;
			return sensorNodes;
		}
		return null;
	}

}
