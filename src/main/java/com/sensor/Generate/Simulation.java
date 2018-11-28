package com.sensor.Generate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sensor.Generate.*;
import com.sensor.utils.*;
public class Simulation {
	public Map<Integer, List<Object>> GCSimulationInterface(int x_loc,int y_loc,int sensorNUm,int UnicatNumber,int radius,int sinkRadius) throws InterruptedException {
		Map<Integer, List<Object>> results=new HashMap<Integer, List<Object>>();
//		初始化空间、sensorNode节点、SinkNode节点
		Space space=new Space(x_loc,y_loc);
		List<SensorNodes> allnodes=space.buildNode(sensorNUm);
		SinkNodes sinkNodes=space.buildSinkNode();
		sinkNodes.id=sensorNUm+1;
		sinkNodes.DetectNeighbors(allnodes,sinkRadius);
		List<Object> newPac=new ArrayList<Object>();
		for (SensorNodes sensorNodes:allnodes) 
		{	
			sensorNodes.DetectNeighbors(allnodes,radius);	
			dataPac dataPac=new dataPac();
			dataPac.setType(sensorNodes.type);
			dataPac.setId(sensorNodes.id);
			dataPac.setX(sensorNodes.point.x_loc);
			dataPac.setY(sensorNodes.point.y_loc);
			dataPac.setState(sensorNodes.state);
			dataPac.setRadius(radius);
			dataPac.setData(sensorNodes.space.get(0));
			newPac.add(dataPac);
		}
			dataPac dataPac=new dataPac();
			dataPac.setType(sinkNodes.type);
			dataPac.setId(sinkNodes.id);
			dataPac.setX(sinkNodes.point.x_loc);
			dataPac.setY(sinkNodes.point.y_loc);
			dataPac.setState(sinkNodes.state);
			dataPac.setRadius(sinkRadius);
			dataPac.setData(null);
	
			newPac.add(dataPac);
//			以上是需要传输的基本信息
			results.put(0, newPac);

		expectionFormual expectionFormual=new expectionFormual();
		Map<Integer, Integer> rMap=expectionFormual.returnR((int)(space.x_loc*space.y_loc));
		Map<Integer, Integer> kMap=expectionFormual.returnK((int)(space.x_loc*space.y_loc),rMap);
	
		int degree=1;
		List<Object> evenPac=new ArrayList<Object>();
		for (int unicastnum=0;unicastnum<UnicatNumber;unicastnum++) {
//			在传的过程中随机进行破坏节点，因为节点已经被破坏掉了，所以每次遍历都是遍历已经被破坏掉的那个了
			
			space.destory(allnodes);
//			破坏后所有节点刷新所有节点的邻居
			for (SensorNodes sensorNodes:allnodes) 
			{
				if(sensorNodes.state==1) {
					sensorNodes.RefreshNeighbors();
				}
			}	
//			按照GC度概率公式进行计算并进行度增长
			if (kMap.get(degree)!=null) {
				if (unicastnum>kMap.get(degree)) {
					degree+=1;
				}
			}
			else {
				degree=degree;
			}
			
			for (SensorNodes sensorNodes:allnodes) {
				eventPac eventPac=new eventPac();
				int sendId=sensorNodes.id;
				Map<Integer,Object> map=sensorNodes.Unicast(degree);
				eventPac.setRound(unicastnum+1);
				eventPac.setType(0);
				eventPac.setRevId((int)map.get(0));
				eventPac.setSendId(sendId);
				eventPac.setState(sensorNodes.state);
				eventPac.setTranPac((int)map.get(1));
				eventPac.setRevPac((int)map.get(2));
				evenPac.add(eventPac);

			}
//			每一轮传输结束之后进行sinknode的接受节点并且解码
			Map<Integer,Object> map=sinkNodes.RevNodes();
			eventPac eventPac=new eventPac();
			eventPac.setRound(unicastnum+1);
			eventPac.setType(1);
			eventPac.setRevId(sinkNodes.id);
			eventPac.setSendId((int)map.get(0));
			eventPac.setState(sinkNodes.state);
			eventPac.setTranPac((int)map.get(1));
			evenPac.add(eventPac);	
		}
		results.put(1,evenPac);
		int destoryNum=0;
		for (SensorNodes sensorNodes:allnodes) 
		{
			if(sensorNodes.state==0) {
				destoryNum++;
			}
		}	
		return results;	
	}
	
	
	
	
	
	
	
	
	
	
	public void GCSimulation(int x_loc,int y_loc,int sensorNUm,int UnicatNumber,int radius,int sinkRadius) throws InterruptedException {

//		初始化空间、sensorNode节点、SinkNode节点
		Space space=new Space(x_loc,y_loc);
		List<SensorNodes> allnodes=space.buildNode(sensorNUm);
		SinkNodes sinkNodes=space.buildSinkNode();
		sinkNodes.DetectNeighbors(allnodes,sinkRadius);
		
		System.out.println("节点初始化完成-------");	
		System.out.println("初始SensorNodes有："+allnodes.size()+"个");	
		System.out.println("初始SinkNodes位置为：X:"+sinkNodes.point.x_loc+"  Y:"+sinkNodes.point.y_loc);
		System.out.println("开始检测节点-------");
		List<dataPac> newPac=new ArrayList<dataPac>();
		for (SensorNodes sensorNodes:allnodes) 
		{	
			sensorNodes.DetectNeighbors(allnodes,radius);	
			dataPac dataPac=new dataPac();
			dataPac.setType(sensorNodes.type);
			dataPac.setId(sensorNodes.id);
			dataPac.setX(sensorNodes.point.x_loc);
			dataPac.setY(sensorNodes.point.y_loc);
			dataPac.setState(sensorNodes.state);
			dataPac.setRadius(radius);
			newPac.add(dataPac);
		}
			dataPac dataPac=new dataPac();
			dataPac.setType(sinkNodes.type);
			dataPac.setId(sinkNodes.id);
			dataPac.setX(sinkNodes.point.x_loc);
			dataPac.setY(sinkNodes.point.y_loc);
			dataPac.setState(sinkNodes.state);
			dataPac.setRadius(sinkNodes.radius);
			newPac.add(dataPac);
//			以上是需要传输的基本信息
			
			
			
			
			
		System.out.println("开始单播-------");

//通过工具类进行GC中的K数组求解，并且按照此度分布进行交换
//目前问题，可能K数组求的时候失误了，可以重点看一下，重新求一下
		expectionFormual expectionFormual=new expectionFormual();
		Map<Integer, Integer> rMap=expectionFormual.returnR((int)(space.x_loc*space.y_loc));
		Map<Integer, Integer> kMap=expectionFormual.returnK((int)(space.x_loc*space.y_loc),rMap);
	
		int degree=1;
		List<eventPac> evenPac=new ArrayList<eventPac>();
		for (int unicastnum=0;unicastnum<UnicatNumber;unicastnum++) {
//			在传的过程中随机进行破坏节点，因为节点已经被破坏掉了，所以每次遍历都是遍历已经被破坏掉的那个了
			
			space.destory(allnodes);
//			破坏后所有节点刷新所有节点的邻居
			for (SensorNodes sensorNodes:allnodes) 
			{
				if(sensorNodes.state==1) {
					sensorNodes.RefreshNeighbors();
				}
			}	
//			按照GC度概率公式进行计算并进行度增长
			if (kMap.get(degree)!=null) {
				if (unicastnum>kMap.get(degree)) {
					degree+=1;
				}
			}
			else {
				degree=degree;
			}
			
			for (SensorNodes sensorNodes:allnodes) {
				eventPac eventPac=new eventPac();
				int sendId=sensorNodes.id;
				Map<Integer,Object> map=sensorNodes.Unicast(degree);
				eventPac.setRound(unicastnum+1);
				eventPac.setType(0);
				eventPac.setRevId((int)map.get(0));
				eventPac.setSendId(sendId);
				eventPac.setState(sensorNodes.state);
				eventPac.setTranPac((int)map.get(1));
				eventPac.setRevPac((int)map.get(2));
				evenPac.add(eventPac);

			}
//			每一轮传输结束之后进行sinknode的接受节点并且解码
			sinkNodes.RevNodes();
		}
		int destoryNum=0;
		for (SensorNodes sensorNodes:allnodes) 
		{
			if(sensorNodes.state==0) {
				destoryNum++;
			}
		}	

		System.out.println("被破坏的节点有"+destoryNum+"个");
		System.out.println("已经解出的包有"+sinkNodes.x_Packets.size()+"个");
		System.out.println("未解出的包有"+sinkNodes.y_Packets.size()+"个");
		System.out.println("丢失的包有"+(allnodes.size()-sinkNodes.x_Packets.size()-sinkNodes.y_Packets.size())+"个");	
	}

	public static void main(String[] args) throws InterruptedException {
		Simulation simulation=new Simulation();
		simulation.GCSimulation(100,100,500,2000,10,10);
	}	
}
