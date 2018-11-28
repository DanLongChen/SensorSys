package com.sensor.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class primeNumber {
	
//	����С��N��һ�������б�
	public List<Integer> returnPrimeNumber(int N) {
		List<Integer> primeNumber=new ArrayList<Integer>();
		for(int i=2;i<N;i++) {
			boolean falg=true;
			for(int k=2;k<=Math.sqrt(i);k++) {
				if (i%k==0) {
					falg=false;
				}
			}
			if (falg==true) {
				primeNumber.add(i);
			}
		}
		return primeNumber;
	}
//	����С��N��һ������map
	public Map<Integer, Integer> returnResult(int N) {
		List<Integer> list=this.returnPrimeNumber(N);
		Map<Integer, Integer> result=new HashMap<Integer,Integer>();
		for(int i=0;i<list.size();i++) {
			result.put(list.get(i),1);
		}
		return result;
	}
	
	
//	����A/B���
	public double subMap(Map<Integer, Integer> mapA,Map<Integer, Integer> mapB) {
		Iterator<Map.Entry<Integer, Integer>> mapa=mapA.entrySet().iterator();
		Iterator<Map.Entry<Integer, Integer>> mapb=mapB.entrySet().iterator();
		Map<Integer, Integer> tempMapA=new HashMap<Integer,Integer>();
		Map<Integer, Integer> tempMapB=new HashMap<Integer,Integer>();
		while (mapa.hasNext()) {
			Map.Entry<Integer, Integer> entry1 = mapa.next();
			if (mapB.containsKey(entry1.getKey())) {
				if (mapB.get(entry1.getKey())>entry1.getValue()) {
					tempMapB.put(entry1.getKey(),mapB.get(entry1.getKey())-entry1.getValue());	
					tempMapA.put(entry1.getKey(),0);	
				}
				else if(mapB.get(entry1.getKey())<entry1.getValue()) {
					tempMapA.put(entry1.getKey(),entry1.getValue()-mapB.get(entry1.getKey()));	
					tempMapB.put(entry1.getKey(),0);	
				}
				else {
					tempMapB.put(entry1.getKey(),0);	
					tempMapA.put(entry1.getKey(),0);	
				}
			}
			else {
				tempMapA.put(entry1.getKey(),entry1.getValue());	
			}
		}
		
		while (mapb.hasNext()) {
			Map.Entry<Integer, Integer> entry1 = mapb.next();
			if (tempMapB.containsKey(entry1.getKey())) {
				continue;
			}
			else {
				tempMapB.put(entry1.getKey(),entry1.getValue());	
			}
		}
		Iterator<Map.Entry<Integer, Integer>> tempA=tempMapA.entrySet().iterator();
		Iterator<Map.Entry<Integer, Integer>> tempB=tempMapB.entrySet().iterator();
		double tempAnum=1;
		double tempBnum=1;
		while (tempA.hasNext()) {
			Map.Entry<Integer, Integer> entry1 = tempA.next();
			for (int i = 0; i < entry1.getValue(); i++) {
				tempAnum=tempAnum*entry1.getKey();
			}		
		}
		while (tempB.hasNext()) {
			Map.Entry<Integer, Integer> entry1 = tempB.next();
			for (int i = 0; i < entry1.getValue(); i++) {
				tempBnum=tempBnum*entry1.getKey();
			}		
		}
		return tempAnum/tempBnum;	
	}
	
	
//	����һ�μ����Ľ��
	public Map<Integer, Integer> updateResult(Map<Integer, Integer> newmap,Map<Integer, Integer> oldmap) {
		 Iterator<Map.Entry<Integer, Integer>> it = newmap.entrySet().iterator();
	        while (it.hasNext()) {
	            Map.Entry<Integer, Integer> entry1 = it.next();
	            if (oldmap.containsKey(entry1.getKey())) {
	            	oldmap.put(entry1.getKey(),entry1.getValue()+oldmap.get(entry1.getKey()));
				}
	            else {
	            	oldmap.put(entry1.getKey(),entry1.getValue());
				}
	        }
		return oldmap;
	}
//	�ֽ�һ����Ϊ����֮�������ҷ���һ��map�Թ�����ĸ�����mapʹ��
	public  Map<Integer, Integer> decompositionNumber(int N,Map<Integer, Integer> prime) {
		Map<Integer, Integer> result=new HashMap<Integer,Integer>();
		while (N!=1) {
			Iterator<Map.Entry<Integer, Integer>> it = prime.entrySet().iterator();
		        while (it.hasNext()) {
		            Map.Entry<Integer, Integer> entry1 = it.next();
		            if (N%entry1.getKey()==0) {
		            	if (result.containsKey(entry1.getKey())) {
		            		result.put(entry1.getKey(), result.get(entry1.getKey())+1);
						}
		            	else {
			            	result.put(entry1.getKey(),1);
		            	}
		            	N=N/entry1.getKey();
					}   
		        }
		}
		return result;
	}
	
	
	
	
	public static void main(String[] args) {
		primeNumber primeNumber=new primeNumber();
		Map<Integer, Integer> nullmap=new HashMap<Integer, Integer>();
//		100���ڵ�����
		Map<Integer, Integer> oldmap=primeNumber.returnResult(100);
//		�ֽ�100֮�������
		Map<Integer, Integer> newmap=primeNumber.decompositionNumber(400,oldmap);
		Map<Integer, Integer> newmap3=primeNumber.decompositionNumber(100,oldmap);
		Iterator<Map.Entry<Integer, Integer>> its = newmap.entrySet().iterator();
		while (its.hasNext()) {
			Map.Entry<Integer, Integer> entry1 = its.next();
			System.out.println(entry1.getKey()+"   "+entry1.getValue());
		}
		Iterator<Map.Entry<Integer, Integer>> itss = newmap3.entrySet().iterator();
		while (itss.hasNext()) {
			Map.Entry<Integer, Integer> entry1 = itss.next();
			System.out.println(entry1.getKey()+"   "+entry1.getValue());
		}
		System.out.println(primeNumber.subMap(newmap, newmap3));
		
//		�ֽ�51֮�������	
		Map<Integer, Integer> newmap2=primeNumber.decompositionNumber(51,oldmap);
		
		nullmap=primeNumber.updateResult(newmap2,nullmap);
		nullmap=primeNumber.updateResult(newmap,nullmap);
		nullmap=primeNumber.updateResult(newmap3,nullmap);
	
		System.out.println("second");
		Iterator<Map.Entry<Integer, Integer>> it = nullmap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, Integer> entry1 = it.next();
			System.out.println(entry1.getKey()+"   "+entry1.getValue());
		}
	}

}
