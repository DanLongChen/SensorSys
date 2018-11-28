package com.sensor.utils;

import java.util.HashMap;
import java.util.Map;

public class expectionFormual {

	public expectionFormual() {
		// TODO �Զ����ɵĹ��캯�����
	}
//	����R����
	public Map<Integer, Integer> returnR(int N) {
		Map<Integer, Integer> rMap=new HashMap<Integer, Integer>();
		for (int i=1;i<N;i++) {
			rMap.put(i, (i*N-1)/(i+1));
		}
		return rMap;	
	}
	
	
	public double factorial(int N) {
		double num=1;
		for(int i=1;i<N+1;i++) {
			num=num*i;			
		}
		return num;
	}
//	��j��N�Ľ׳�
	public double factorial(double N,double j) {
		double num=1;
		for(double i=j;i<N+1;i++) {
			num=num*i;			
		}
		return num;
	}
	public double returnC(int N,int j) {
		double num=factorial(N)/(factorial(N-j)*factorial(j));
		return num;
	}
	
//	�µķ�������ֳ������ĳ˻�������ֹ���
	public double tempK(double rj,double ri,int j,int N) {
		Map<Integer, Integer> topMap=new HashMap<Integer, Integer>();
		Map<Integer, Integer> bottomMap=new HashMap<Integer, Integer>();
		primeNumber primeNumber=new primeNumber();
//		����N���ڴ�����
		Map<Integer, Integer> fixedMap=primeNumber.returnResult(N);
//		�ֽ�����Ľ׳˷ֽ�ʽ
		Map<Integer, Integer> jMap=primeNumber.decompositionNumber(j,fixedMap);
		for(double i=ri;i<rj;i++) {
//			��������Ľ׳˷ֽ�ʽ
			for(double k=N-j+1;k<N+1;k++) {
				Map<Integer, Integer> newmap=primeNumber.decompositionNumber((int) k,fixedMap);
				topMap=primeNumber.updateResult(newmap,topMap);
			}	
			bottomMap=primeNumber.updateResult(jMap,bottomMap);
			Map<Integer, Integer> NiMap=primeNumber.decompositionNumber((int) (N-i),fixedMap);
			bottomMap=primeNumber.updateResult(NiMap,bottomMap);
			for(double temp_i=i-j+2;temp_i<i+1;temp_i++) {
				Map<Integer, Integer> ij2map=primeNumber.decompositionNumber((int) temp_i,fixedMap);
				bottomMap=primeNumber.updateResult(ij2map,bottomMap);
			}	
		}
		double num=primeNumber.subMap(topMap, bottomMap);
		return num;
	}
	
	public int tempK1(int rj,int N) {
		int num=0;
		for(int i=0;i<rj;i++) {
			num+=N/(N-i);			
		}
		return num;
	}
//	����K����
	public Map<Integer, Integer> returnK(int N,Map<Integer, Integer> rMap) {
		Map<Integer, Integer> kMap=new HashMap<Integer, Integer>();
		int temp_sum=0;
//		i����j,����һ����
		for (int i=1;i<rMap.size()+1;i++) {
			if(i==1) {
				temp_sum+=tempK1(rMap.get(i),N);
				kMap.put(i,temp_sum);
			}
			else {
				temp_sum+=tempK(rMap.get(i),rMap.get(i-1),i,N);
				kMap.put(i,temp_sum);
			}
//			System.out.println(kMap.get(i));
		}
		return kMap;	
	}
	
	
	
}
