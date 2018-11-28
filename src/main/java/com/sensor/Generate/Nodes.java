package com.sensor.Generate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.sound.sampled.LineListener;

public class Nodes {
	
	int id; //唯一标示节点
	int data;
	Point point;  //节点的位置信息 
	NodeStat nodeStat; //节点的接受和发送包的统计信息
	List<Packets> space; // 存储空间，每个包存储的是节点的id（可为一，可为多）
	List<SensorNodes> neigh; // 邻居集合
	int state; // 状态标志位，可为1与0，分别表示正常与损坏
	
	//定义一个无参的构造方法
	public Nodes() {   
		
	}
//	带参的构造方法
	public Nodes(int id,int data,Point point,NodeStat nodeStat,List<Packets> space,List<SensorNodes> neigh,int state) {
		this.id=id;
		this.data=data;
		this.neigh = neigh;
		this.space = space;
		this.state = state; 
		this.point = point;
		this.nodeStat = nodeStat;
	}

//	开启节点
	public void On()
	{		
		this.state=1;
	}
//	关闭节点
	public void Off()
	{
		this.state=0;	
	}
//	检测状态
	public int CheckOn()
	{
		return this.state;	
	}
//	返回节点的位置
	public Point GetPosition()
	{
		return this.point;
	}
//	检测邻居节点
	public void DetectNeighbors() 
	{
		// TODO Auto-generated method stub
	}
//	刷新邻居节点
	public void RefreshNeighbors() 
	{
		
	}
//  有条件的的选择包
	public Packets RandSelectPacket(){
		return null;
	}
//	随机选择一个包，没有度限制，并且返回这个包
	public Packets RERandSelectPacket()
	{
		return null;	
	}
//	广播时对邻居进行选择
	public List<SensorNodes> RandSelectNeigh() 
	{
		return null;
	}
//	单播时随机选择一个邻居
	public SensorNodes RandSelectSensorNodes()
	{
		return null;
	}
//	单播方式
	public void Unicast()
	{
		
	}
//	广播方式，在选择的邻居列表内进行单播
	public SensorNodes Broadcast()
	{
		return null;
	}
//	发送包
	public Packets Sendpkt()
	{
		return null;
	}
//	接收包
	public Packets Recvpkt()
	{
		return null;
	}

}
