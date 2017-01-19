package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import DataProcessing.readData;
import DataProcessing.writeData;
import IFPUG.Component.Component;
import EffortEstimation.LinearRegression;

public class OriginIFPUG {
	//public static Map<String, List<Component>> dataMap = new HashMap<String, List<Component>>();
	public static Map<String, List<Object>> result = new LinkedHashMap<String, List<Object>>();
	
	public static void main(String[] args){
		try {
			int FP = 0;
			String prePath = "C:\\Users\\LIUJF\\Documents\\SCHOOL\\FPA\\dataset\\";
			String[] Projectname = {"P2","P3"};
			String suffix = ".xlsx";
			int ProjectNum = Projectname.length;
			
			double[][] FP_Effort = new double[2][ProjectNum];
			
			Map<String, double[]> dataMap = new LinkedHashMap<String, double[]>();
			readData.readDoubleFromExcel(prePath+"dataset_use"+suffix, dataMap);
			
			// Caculate Function Point before adjustment
			int count = 0;
			for(String pname : Projectname){
				FP = 0;
				List<Component> CList = new ArrayList<Component>();
				readData.readComponentfromExcel(prePath+pname+suffix,CList);
				
				// Get useful dataset
				double[] data = dataMap.get(pname);
				if(data.length >= 2 && count < ProjectNum){
					FP_Effort[0][count] = data[0];//FP
					FP_Effort[1][count] = data[1];//Effort
					count++;
				}
				
				// Get Function Point
				for(int i=0;i<CList.size();i++){
					if(CList.get(i) != null){
						FP += CList.get(i).getFunctionPoint();
					}
				}
				
				List<Object> objs = new ArrayList<Object>();
				objs.add(FP);
				result.put(pname, objs);
				
			}
			
			// Estimate Effort by Linear Regression
			LinearRegression.process(FP_Effort[0], FP_Effort[1]);
			System.out.println("Predict:"+LinearRegression.predict(410));
//			LinearRegression.process(new double[]{180.0,205.0}, new double[]{1184,1200});
//			System.out.println("Predict:"+LinearRegression.predict(410));

			
			// Write result
			List<String> columnNames = new ArrayList<String>();
			columnNames.add("ProjectName");
			columnNames.add("PointsNonAdujust");
			columnNames.add("Adjustment");
			columnNames.add("PointsAdjust");
			writeData.writeExcel(result, columnNames, prePath + "result.xlsx");
			
			System.out.println(FP);
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
