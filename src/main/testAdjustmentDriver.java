package main;

import AdjustmentMechanism.AdjustmentDriver;
import DataProcessing.readData;
import EvaluationMethod.EvaluateMRE;
import EvaluationMethod.MRE;
import SimilarityMeasure.DistanceDriver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class testAdjustmentDriver {
	
	public static double BASE_PRODUCTIVITY = 12;// person-hours/FP

	public static void main(String[] args) throws IOException {
		// for Reading Data
		String prePath = "/Users/liujiaqi/OneDrive/文档/毕设/FPA/dataset/Cocomo/";
		String suffix = ".xlsx";
		
		//String[][] data = readData.readStringFromExcel(prePath+"clean_DT_LT"+suffix);
		String[][] data = readData.readStringFromExcel(prePath+"cocomo81_forGA"+suffix);
		String[] titles = data[0];
		
		// for Evaluation
		List<MRE> MREs = new ArrayList<MRE>();
		double MMRE, MdMRE;
		
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
		
		//System.out.println("Predicted,Prediction,MRE");
		for(String name : data4Similarity.keySet()){
			List<Object> Predicted = data4Similarity.get(name);
			Map<String, List<Object>> Analogues = new LinkedHashMap<String, List<Object>>(data4Similarity);
			Analogues.remove(name);
			AdjustmentDriver estimateDriver = new AdjustmentDriver(Predicted, Analogues);
			
			// using GA to train Weights of each features for Similarity Measure
			AdjustmentDriver trainDriver = new AdjustmentDriver(data4Similarity);
			trainDriver.TrainWeights();
			estimateDriver.setWeights(trainDriver.getWeights());
			
			double prediction = estimateDriver.process();
			double actualEffort = estimateDriver.getActualEffort();

			// Evaluation
			MRE curMRE = new MRE(actualEffort, prediction);
			MREs.add(curMRE);
			
			System.out.println(name+","+String.format("%.2f", prediction)+","+String.format("%.2f", curMRE.getRelativeError()));
			
		}
		
		// Evalution
		EvaluateMRE evaluation = new EvaluateMRE(MREs);
		System.out.println("MMRE="+String.format("%.2f", evaluation.getMMRE())+"MdMRE="+String.format("%.2f", evaluation.getMdMRE())+"Pred(0.25)="+String.format("%.2f", evaluation.getPred()));

	}

}
