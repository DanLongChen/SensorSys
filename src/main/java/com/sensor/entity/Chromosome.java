package com.sensor.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * @author:DanLongChen
 * @versioin:2018年11月14日下午2:51:31
 **/
public class Chromosome {// 表示一条染色体
	public int id = 0;// 染色体ID
	public List<Gene> list = new ArrayList<Gene>();// 染色体中的基因组
	public double ratio;// 染色体变异率
	public int score = 0;// 适应度值
	public List<Chromosome> Nlist = new ArrayList<Chromosome>();// 染色体的邻居队列
	public List<Double> trust=new ArrayList<Double>();//邻居的信赖域列表

	public Chromosome(int id, double ratio) {
		this.id = id;
		this.ratio = ratio;
	}

	public Chromosome clone(){
		Chromosome clone=new Chromosome(this.id,this.ratio);
		clone.setList(this.getList());
		clone.setScore(this.getScore());
		clone.setNlist(this.getNlist());
		return clone;
	}

	@Override
	public String toString() {
		return list.toString();
	}

	/* ###################getset##################### */
	public List<Gene> getList() {
		return list;
	}

	public void setList(List<Gene> list) {
		this.list = list;
	}

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public List<Chromosome> getNlist() {
		return Nlist;
	}

	public void setNlist(List<Chromosome> nlist) {
		Nlist = nlist;
	}

	public List<Double> getTrust() {
		return trust;
	}

	public void setTrust(List<Double> trust) {
		this.trust = trust;
	}
}
