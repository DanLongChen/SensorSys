package com.sensor.Generate;

import java.util.ArrayList;
import java.util.List;

import com.sensor.Generate.Packets;
public class Packets {

	List<Integer> id_sets; // 包含哪些节点
	int data; // 数据
	
	public Packets() {
		// TODO Auto-generated constructor stub
	}
	
	public Packets(List<Integer> id_sets,int data) {
		// TODO Auto-generated constructor stub
		this.id_sets=id_sets;
		this.data=data;
	}
	
//	合并包
	public Packets combinPac(Packets packets) {
		packets.id_sets.removeAll(this.id_sets);
		this.id_sets.addAll(packets.id_sets);
		this.data=this.data ^ packets.data;
		return this;
	}
	
	//就是用this去解码一个新到的packet
		public Packets decodePac(Packets packets) {
			Packets temp_pac=this;
			int pacaSize=this.id_sets.size();
			int pacbSize=packets.id_sets.size();
			if(1==Math.abs(pacaSize-pacbSize)) {
				int flag=0;
				if(pacaSize>pacbSize) {
					for(Integer sensorNodes:packets.id_sets) {
						if(this.id_sets.contains(sensorNodes)) {
							flag+=1;
						}
					}
					if (flag==pacbSize) 
					{	this.id_sets.removeAll(packets.id_sets);
						this.data=this.data^packets.data;
						return this;
					}
					else 
					{
						return this;
					}
				}	
			}
			return this;	
	}
	
	
	
//返回本包的度
	public int getDegree() {
		return this.id_sets.size();	
	}
//判断要合并的包中是否有重合的节点
	public Boolean returnIntersection(Packets packets) {
		for(Integer sensorNodes2:this.id_sets) {
			if (packets.id_sets.contains(sensorNodes2)) {
					return false;
			}
		}
		return true;
	}
	
}
