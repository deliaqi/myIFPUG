package main;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import DataProcessing.readData;
import EffortEstimation.LinearRegression;

public class LRestimation {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		int FP = 0;
		String prePath = "C:\\Users\\LIUJF\\Documents\\SCHOOL\\FPA\\dataset\\ISBSG\\";
		String suffix = ".xlsx";
		
		
		Map<String, double[]> dataMap = new LinkedHashMap<String, double[]>();
		readData.readDoubleFromExcel(prePath+"clean"+suffix, dataMap);
		//System.out.println(dataMap.size());
		
		// Prepare Array contains FP and Effort data
//		double[][] FP_Effort = new double[2][dataMap.size()-1];
//		int index = 0;
//		for(String projectnum : dataMap.keySet()){
//			double[] curdata = dataMap.get(projectnum);
//			if(curdata.length == 2 && index < dataMap.size()-1){
//				FP_Effort[0][index] = curdata[0];
//				FP_Effort[1][index] = curdata[1];
//				index++;
//			}
//		}
		
		// LR Estimation
		//LinearRegression.process(FP_Effort[0], FP_Effort[1]);
		//System.out.println("Predict:"+LinearRegression.predict(856));
		
		
		double[][] FP_Effort = new double[dataMap.size()][2];
		int index = 0;
		for(String projectnum : dataMap.keySet()){
			FP_Effort[index] = dataMap.get(projectnum);
			index++;
		}
		SimpleRegression simpleRegression = new SimpleRegression(true);
		simpleRegression.addData(FP_Effort);
		
		double sample = 856;
		double realEffort = 2551;
		double prediction = simpleRegression.predict(sample);
		
		System.out.println("slope="+simpleRegression.getSlope());
		System.out.println("intercept="+simpleRegression.getIntercept());
		System.out.println("predicted result="+prediction);
		System.out.println("AE="+Math.abs(prediction-realEffort)+",RE="+Math.abs(prediction-realEffort)/realEffort);

	}

}
