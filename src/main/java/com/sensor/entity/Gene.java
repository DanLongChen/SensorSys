package com.sensor.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * @author:DanLongChen
 * @versioin:2018年11月14日下午2:59:36
 **/
public class Gene {// 表示一个基因
	public List<Boolean> list=new ArrayList<Boolean>();// 基因的bit位
	public int GeneIn = 0;// 输入链路数量
	public int GeneOut = 0;// 输出链路数量

	public Gene(int in,int out){
		this.GeneIn=in;
		this.GeneOut=out;
	}
	/* ##############getset############### */

	public List<Boolean> getList() {
		return list;
	}

	public void setList(List<Boolean> list) {
		this.list = list;
	}

	public int getGeneIn() {
		return GeneIn;
	}

	public void setGeneIn(int geneIn) {
		GeneIn = geneIn;
	}

	public int getGeneOut() {
		return GeneOut;
	}

	public void setGeneOut(int geneOut) {
		GeneOut = geneOut;
	}

	public void Init() {// 初始化基因
		int in = getGeneIn();
		int out = getGeneOut();
		for (int i = 0; i < out; i++) {
			Boolean[] g1 = new Boolean[in];
			for (int j = 0; j < in; j++) {
				g1[j] = Math.random()>=0.5;
			}
			list.addAll(Arrays.asList(g1));
		}
	}

}
