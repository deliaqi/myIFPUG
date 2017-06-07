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

public class FeatureAnalysis {
	
	public static double BASE_PRODUCTIVITY = 12;// person-hours/FP

	public static void main(String[] args) throws IOException {
		// for Reading Data
		String prePath = "/Users/liujiaqi/OneDrive/文档/毕设/FPA/dataset/ISBSG/FeatureAnalysis/";
		String suffix = ".xlsx";
		
		//String[][] data = readData.readStringFromExcel(prePath+"clean_DT_LT"+suffix);
		String[][] data = readData.readStringFromExcel(prePath+"IFPUG4_4GL_New Development_MR"+suffix);
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

			Productivity.add(productivity);
			
			System.out.println(name+":"+String.format("%.2f", productivity));
			
		}
		
		// Evaluate productivity
		EvaluateMRE evaluation = new EvaluateMRE();

		
		int psize = Productivity.size();
		double[] pros = new double[psize];
		for(int i=0;i<Productivity.size();i++){
			pros[i] = Productivity.get(i);
		}

		System.out.println("Median Productivity="+String.format("%.2f", evaluation.caculateMdMRE(pros))
				+",Average Productivity="+String.format("%.2f", evaluation.caculateMMRE(pros))
				+",Multiple="+String.format("%.2f", evaluation.getMMRE()/12));

	}

}
