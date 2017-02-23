package EffortEstimation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import DataProcessing.readData;
import DataProcessing.writeData;

public class COCOMO2 {
	
	public static Map<String, List<Double>> EMValueTable;
	public static double A = 2.94;// for COCOMO II.2000
	
	public static void process(String path) throws IOException{
		String data[][] = readData.readDataFromExcel(path);
		if(data == null) return;
		
		Map<String, List<Object>> usefulData = new LinkedHashMap<String, List<Object>>();
		
		// set column names 
		List<String> columnNames = new ArrayList<String>();
		columnNames.add("OriginNum");
		String[] titles = data[0];
		for(int i=0;i<titles.length;i++){
			if(i == 17) break;
			columnNames.add(titles[i]);
		}
		columnNames.add("PREDICT_EFFORT");
		columnNames.add("RELATIVE_ERROR");
		
		for(int i=1;i<data.length;i++){
			String[] curdata = data[i];
			List<Object> curUSE = new ArrayList<Object>();
			for(int j=0;j<curdata.length;j++){
				// add EM value
				if(j == 15){
					curUSE.add(Double.parseDouble(curdata[j]));continue;
				}else if(j == 16){
					curUSE.add(Double.parseDouble(curdata[j]));
					curUSE.add(caculatePM((Double)curUSE.get(j-1),curUSE));
					curUSE.add(Math.abs((Double)curUSE.get(j)-(Double)curUSE.get(j+1))/(Double)curUSE.get(j));
					break;
				}
				List<Double> emvalues = EMValueTable.get(columnNames.get(j+1));
				Double curemvalue;
				switch(curdata[j]){
				case "Very_Low": curemvalue = emvalues.get(0);break;
				case "Low": curemvalue = emvalues.get(1);break;
				case "Nominal": curemvalue = emvalues.get(2);break;
				case "High": curemvalue = emvalues.get(3);break;
				case "Very_High": curemvalue = emvalues.get(4);break;
				case "Extra_High": curemvalue = emvalues.get(5);break;
				default: curemvalue = Double.NaN;break;
				}
				curUSE.add(curemvalue);
			}
			usefulData.put(i+"", curUSE);
		}
		
		writeData.writeExcel(usefulData, columnNames, "C:\\Users\\dell1\\Documents\\409\\FPA\\dataset\\COCOMOII\\cocomonasa.xlsx");
		
	}
	
	public static double caculatePM(double locsize, List<Object> emValues){
		Double pm = A * locsize;
		for(int i=0;i<emValues.size();i++){
			if(i == 15) break;
			pm = pm*(Double)emValues.get(i);
		}
		return pm;
	}
	
	public static Map<String, List<Double>> readEMValue(String path) throws IOException{
		Map<String, List<Double>> EMTable = new LinkedHashMap<String, List<Double>>();
		String data[][] = readData.readDataFromExcel(path);
		for(int i=1;i<data.length;i++){
			String[] curdata = data[i];
			String curEM = curdata[0];
			List<Double> curvalue = new ArrayList<Double>();
			for(int j=1;j<curdata.length;j++){
				if(curdata[j].equalsIgnoreCase("NA")){
					curvalue.add(Double.NaN);
				}else{
					curvalue.add(Double.parseDouble(curdata[j]));
				}
			}
			EMTable.put(curEM, curvalue);
		}
		return EMTable;
	}
	
	public static void main(String[] args) throws IOException{
		String path = "C:\\Users\\dell1\\Documents\\409\\FPA\\dataset\\cocomonasa.xlsx";
		EMValueTable = readEMValue("C:\\Users\\dell1\\Documents\\409\\FPA\\dataset\\EMValue.xlsx");
		process(path);
	}

}
