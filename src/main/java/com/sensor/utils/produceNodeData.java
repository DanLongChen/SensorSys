package com.sensor.utils;

import java.awt.RadialGradientPaint;
import java.util.Random;

public class produceNodeData {

	public int returnNodeData() {
		
		Random random=new Random();
		
		int a=random.nextInt(2);
		
		return a;
		
	}
	public int returnNodeData(int dataLength) {
		
		int result = 0;
		
		for (int i = 0; i < dataLength; i++) {
			
			int l=returnNodeData();
			
			result+=l*(Math.pow(10, i));	
		}
		
		return result;	
	}
}
