package EffortEstimation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import DataProcessing.readData;
import DataProcessing.writeData;
import EvaluationMethod.MRE;

public class COCOMO2 {
	
	public static Map<String, List<Double>> EMValueTable;
	public static double A = 2.94;// for COCOMO II.2000
	private List<MRE> MREs = new ArrayList<MRE>(); 
	private String resultpath = "";
	
	public void process(String path) throws IOException{
		String data[][] = readData.readStringFromExcel(path);
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
			MRE curMRE = null;
			List<Object> curUSE = new ArrayList<Object>();
			double estimationEffort,actualEffort;
			for(int j=0;j<curdata.length;j++){
				// add EM value
				if(j == 16){
					curUSE.add(Double.parseDouble(curdata[j]));
					estimationEffort = caculatePM((Double)curUSE.get(j-1),curUSE);
					actualEffort = (Double)curUSE.get(j);
					curMRE = new MRE(actualEffort, estimationEffort);
					curUSE.add(estimationEffort);
					curUSE.add(curMRE.getRelativeError());
					break;
				}
				
				double curemvalue;
				try{
					curemvalue = Double.parseDouble(curdata[j]);
				}catch(NumberFormatException e){
					List<Double> emvalues = EMValueTable.get(columnNames.get(j+1).toUpperCase());
					switch(curdata[j]){
					case "Very_Low": curemvalue = emvalues.get(0);break;
					case "vl": curemvalue = emvalues.get(0);break;
					case "Low": curemvalue = emvalues.get(1);break;
					case "l": curemvalue = emvalues.get(1);break;
					case "Nominal": curemvalue = emvalues.get(2);break;
					case "n": curemvalue = emvalues.get(2);break;
					case "High": curemvalue = emvalues.get(3);break;
					case "h": curemvalue = emvalues.get(3);break;
					case "Very_High": curemvalue = emvalues.get(4);break;
					case "vh": curemvalue = emvalues.get(4);break;
					case "Extra_High": curemvalue = emvalues.get(5);break;
					case "xh": curemvalue = emvalues.get(5);break;
					default: curemvalue = Double.NaN;break;
					}
				}
				
				curUSE.add(curemvalue);
			}
			MREs.add(curMRE);
			usefulData.put(i+"", curUSE);
		}
		
		if(resultpath == "" || resultpath == null){
			writeData.writeExcel(usefulData, columnNames, "C:\\Users\\dell1\\Documents\\409\\FPA\\dataset\\Cocomo\\cocomonasa_result.xlsx");
		}else{
			writeData.writeExcel(usefulData, columnNames,resultpath);
		}
		
		
	}
	
	public double caculatePM(double locsize, List<Object> emValues){
		Double pm = A * locsize;
		for(int i=0;i<emValues.size();i++){
			if(i == 15) break;
			pm = pm*(Double)emValues.get(i);
		}
		return pm;
	}
	
	public static Map<String, List<Double>> readEMValue(String path) throws IOException{
		Map<String, List<Double>> EMTable = new LinkedHashMap<String, List<Double>>();
		String data[][] = readData.readStringFromExcel(path);
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

	public List<MRE> getMREs() {
		return MREs;
	}

	public String getResultpath() {
		return resultpath;
	}

	public void setResultpath(String resultpath) {
		this.resultpath = resultpath;
	}

}
