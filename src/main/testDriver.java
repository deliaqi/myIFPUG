package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import DataProcessing.readData;
import EvaluationMethod.EvaluateMRE;
import EvaluationMethod.MRE;
import SimilarityMeasure.DistanceDriver;

public class testDriver {

	public static void main(String[] args) throws IOException {
		// for Reading Data
		String prePath = "C:\\Users\\dell1\\Documents\\409\\FPA\\dataset\\FP\\";
		String suffix = ".xlsx";
		//String[][] data = readData.readStringFromExcel(prePath+"clean_DT_LT"+suffix);
		String[][] data = readData.readStringFromExcel(prePath+"maxwell"+suffix);
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
		
		System.out.println("Predicted,CA,Distance,Productivity,Prediction,MRE");
		for(String name : data4Similarity.keySet()){
			List<Object> Predicted = data4Similarity.get(name);
			Map<String, List<Object>> Analogues = new LinkedHashMap<String, List<Object>>(data4Similarity);
			Analogues.remove(name);
			DistanceDriver estimateDriver = new DistanceDriver(Predicted, Analogues);
			
			// using GA to train Weights of each features for Similarity Measure
			//DistanceDriver trainDriver = new DistanceDriver(data4Similarity);
			//trainDriver.TrainWeights();
			//estimateDriver.setWeights(trainDriver.getWeights());
			
			Map<String, Double> Distances = estimateDriver.process();
			String CAname = estimateDriver.getMinDistance();
			double CAdistance = Distances.get(CAname);
			
			// Estimation
			double CAEffort = estimateDriver.getCAEffort();
			double actualEffort = estimateDriver.getActualEffort();
			double CAFP = (double)estimateDriver.getCAdata().get(0);
			double actualFP = (double)estimateDriver.getPredicted().get(0);
			
//			double CAEffort = data4Estimation.get(CAname);
//			double actualEffort = data4Estimation.get(name);
//			double CAFP = (Double)data4Similarity.get(CAname).get(0);
//			double actualFP = (Double)data4Similarity.get(name).get(0);
			double productivity = CAEffort / CAFP;
//			if(CAdistance>1){
//				productivity = 10;
//			}
//			if(productivity < 5 || productivity > 20){
//				productivity = 10;
//			}
			double prediction = productivity * actualFP;
			MRE curMRE = new MRE(actualEffort, prediction);
			MREs.add(curMRE);
			Productivity.add(productivity);
			
			System.out.println(name+","+CAname+","+String.format("%.2f", CAdistance)+","+String.format("%.2f", productivity)
					+","+String.format("%.2f", prediction)+","+String.format("%.2f", curMRE.getRelativeError()));
			
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
