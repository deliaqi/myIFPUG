package main;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import DataProcessing.readData;

public class LRestimationVAF {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		int FP = 0;
		String prePath = "C:\\Users\\dell1\\Documents\\409\\FPA\\dataset\\Promise\\";
		String suffix = ".xlsx";
		
		
		Map<String, double[]> dataMap = new LinkedHashMap<String, double[]>();
		readData.readDoubleFromExcel(prePath+"clean"+suffix, dataMap);
		
		
		double[][] FP_Effort = new double[dataMap.size()][2];
		int index = 0;
		for(String projectnum : dataMap.keySet()){
			FP_Effort[index] = dataMap.get(projectnum);
			index++;
		}
		SimpleRegression simpleRegression = new SimpleRegression(true);
		simpleRegression.addData(FP_Effort);
		
		double sample = 452;
		double realEffort = 3948;
		double prediction = simpleRegression.predict(sample);
		
		System.out.println("slope="+simpleRegression.getSlope());
		System.out.println("intercept="+simpleRegression.getIntercept());
		System.out.println("predicted result="+prediction);
		System.out.println("AE="+Math.abs(prediction-realEffort)+",RE="+Math.abs(prediction-realEffort)/realEffort);
		
		

	}

}
