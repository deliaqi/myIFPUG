package main;

import DataProcessing.readData;
import EvaluationMethod.EvaluateMRE;
import EvaluationMethod.MRE;
import SimilarityMeasure.DistanceDriver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class testIFPUGGA {
	
	public static double BASE_PRODUCTIVITY = 12;// person-hours/FP

	public static void main(String[] args) throws IOException {
		// for Reading Data
		String prePath = "/Users/liujiaqi/OneDrive/文档/毕设/FPA/dataset/ISBSG/";
		String suffix = ".xlsx";
		
		//String[][] data = readData.readStringFromExcel(prePath+"clean_DT_LT"+suffix);
		String[][] data = readData.readStringFromExcel(prePath+"isbsg10_use18"+suffix);
		String[] titles = data[0];
		
		// for Evaluation
		List<MRE> MREs = new ArrayList<MRE>();
		double MMRE, MdMRE;
		
		// Store Productivity (Effort / FunctionPoints)
		List<Double> Productivity = new ArrayList<Double>();
		
		// Prepare Data Map for Similarity Measure
		Map<String, List<Object>> data4Similarity = new LinkedHashMap<String, List<Object>>();
		Map<String, Double> data4Estimation = new LinkedHashMap<String, Double>();
		int rowNum = data.length;
		int colNum = data[0].length;
		for(int i=1;i<rowNum;i++){
			String curName = data[i][0];
			List<Object> curData = new ArrayList<Object>();
			for(int j=1;j<colNum;j++){
				try{
					curData.add(Double.parseDouble(data[i][j]));
				}catch(NumberFormatException e){
					curData.add(data[i][j]);
				}
			}
			data4Similarity.put(curName, curData);
			data4Estimation.put(curName, Double.parseDouble(data[i][colNum-1]));
		}

		// using GA to train Weights of each features for Similarity Measure
		//DistanceDriver trainDriver = new DistanceDriver(data4Similarity);
		//trainDriver.TrainWeights();
		
		System.out.println("Predicted,ActualProductivity,FP,ActualEffort,Prediction,MRE");
		for(String name : data4Similarity.keySet()){
			List<Object> Predicted = data4Similarity.get(name);
			Map<String, List<Object>> Analogues = new LinkedHashMap<String, List<Object>>(data4Similarity);
			Analogues.remove(name);
			DistanceDriver estimateDriver = new DistanceDriver(Predicted, Analogues);
			
			//Map<String, Double> Distances = estimateDriver.process();
			//String CAname = estimateDriver.getMinDistance();
			//double CAdistance = Distances.get(CAname);
			
			// Estimation
			//double CAEffort = estimateDriver.getCAEffort();
			double actualEffort = estimateDriver.getActualEffort();
			//double CAFP = (double)estimateDriver.getCAdata().get(0);
			double actualFP = (double)estimateDriver.getPredicted().get(0);
			
			double productivity = actualEffort / actualFP;
			double AFP = actualFP;

			// Adjust FP
			// by Development Type
			String DT = (String) Predicted.get(4);
			if(DT.equalsIgnoreCase("'New Development'")) AFP = AFP * 0.857;
			else if(DT.equalsIgnoreCase("Re-development")) AFP = AFP * 0.863;
			else if(DT.equalsIgnoreCase("Enhancement")) AFP = AFP * 0.857;
			// by Language Type
			String language = (String) Predicted.get(6);
			if(language.equalsIgnoreCase("3GL")) AFP = AFP * 1.06;
			else if(language.equalsIgnoreCase("4GL")) AFP = AFP * 0.87;
			// by Development Platform
			String DP = (String) Predicted.get(5);
			if(DP.equalsIgnoreCase("PC")) AFP = AFP * 0.61;
			else if(DP.equalsIgnoreCase("MR")) AFP = AFP * 1.01;
			else if(DP.equalsIgnoreCase("MF")) AFP = AFP * 1.06;

			//double prediction = BASE_PRODUCTIVITY * AFP;

			double prediction = BASE_PRODUCTIVITY * actualFP;
			MRE curMRE = new MRE(actualEffort, prediction);
			MREs.add(curMRE);
			Productivity.add(productivity);
			
			System.out.println(name+","+String.format("%.2f", productivity)+","+String.format("%.2f", actualFP)
					+","+String.format("%.2f", actualEffort)+","+String.format("%.2f", prediction)+","
					+String.format("%.2f", curMRE.getRelativeError()));
			
		}
		
		// Evalution
		EvaluateMRE evaluation = new EvaluateMRE(MREs);
		System.out.println("MMRE="+String.format("%.2f", evaluation.getMMRE())+"MdMRE="+String.format("%.2f", evaluation.getMdMRE())+"Pred(0.25)="+String.format("%.2f", evaluation.getPred()));
		
		double sum = 0;
		for(int i=0;i<Productivity.size();i++){
			sum = sum + Productivity.get(i);
		}
		System.out.println("Average Productivity:"+String.format("%.2f", sum/Productivity.size()));

	}

}
