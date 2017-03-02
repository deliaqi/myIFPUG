package DataProcessing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class cleanData {
	
	public static void cleanISBSG(String path,Map<String, String[]> targetdata) throws IOException{
		String[][] data = readData.readDataFromExcel(path);
		if(data == null) return;
		
		// Find target columns and rows
		String[] titles = data[0];
		Map<Integer, String> targetCol = new LinkedHashMap<Integer, String>();
		for(int i=0;i<titles.length;i++){
			if(targetdata.containsKey(titles[i])){
				targetCol.put(i, titles[i]);
			}
		}
		
		Map<String, List<Object>> usefulData = new LinkedHashMap<String, List<Object>>();
		boolean isUseful = false;
		for(int i=1;i<data.length;i++){
			List<Object> curdata = new ArrayList<Object>();
			for(int key : targetCol.keySet()){
				String colname = targetCol.get(key);
				String[] valivalue = targetdata.get(colname);
				if(valivalue == null || valivalue.length == 0){
					isUseful = true;
				}else{
					for(String value : targetdata.get(targetCol.get(key))){
						if(value.equals(data[i][key])){
							isUseful = true;
							break;
						}
						isUseful = false;
					}
				}
				if(!isUseful) break;
				curdata.add(data[i][key]);
			}
			if(isUseful){
				usefulData.put(i+"", curdata);
			}
		}
		
		// Write data
		List<String> columnNames = new ArrayList<String>();
		columnNames.add("OriginNum");
		for(String col : targetdata.keySet()){
			columnNames.add(col);
		}
		writeData.writeExcel(usefulData, columnNames, "C:\\Users\\dell1\\Documents\\409\\FPA\\dataset\\ISBSG\\clean_DT_LT.xlsx");
		
		
		
	}
	
	public static void cleanPromise(String path,Map<String, String[]> targetdata) throws IOException{
		String[][] data = readData.readDataFromExcel(path);
		if(data == null) return;
		
		// Find target columns and rows
		String[] titles = data[0];
		Map<Integer, String> targetCol = new LinkedHashMap<Integer, String>();
		for(int i=0;i<titles.length;i++){
			if(targetdata.containsKey(titles[i])){
				targetCol.put(i, titles[i]);
			}
		}
		
		Map<String, List<Object>> usefulData = new LinkedHashMap<String, List<Object>>();
		boolean isUseful = false;
		for(int i=1;i<data.length;i++){
			List<Object> curdata = new ArrayList<Object>();
			for(int key : targetCol.keySet()){
				String colname = targetCol.get(key);
				String[] valivalue = targetdata.get(colname);
				if(valivalue == null || valivalue.length == 0){
					isUseful = true;
				}else{
					for(String value : targetdata.get(targetCol.get(key))){
						if(value.equals(data[i][key])){
							isUseful = true;
							break;
						}
						isUseful = false;
					}
				}
				if(!isUseful) break;
				curdata.add(data[i][key]);
			}
			if(isUseful){
				usefulData.put(i+"", curdata);
			}
		}
		
		// Write data
		List<String> columnNames = new ArrayList<String>();
		columnNames.add("OriginNum");
		for(String col : targetdata.keySet()){
			columnNames.add(col);
		}
		writeData.writeExcel(usefulData, columnNames, "C:\\Users\\dell1\\Documents\\409\\FPA\\dataset\\Promise\\clean.xlsx");
		
		
		
	}
	
	public static void main(String[] args) throws IOException{
		Map<String, String[]> targetData = new LinkedHashMap<String, String[]>();
		targetData.put("Function Points", new String[]{});
		targetData.put("Development Type", new String[]{"New Development"});
		//targetData.put("Development Platform", new String[]{"PC"});
		targetData.put("Language Type", new String[]{"3GL"});
		//targetData.put("DBMS Used", new String[]{"Yes"});
		targetData.put("Effort", new String[]{});
		
		cleanISBSG("C:\\Users\\dell1\\Documents\\409\\FPA\\dataset\\ISBSG\\IFPUG4.xlsx", targetData);
		
//		Map<String, String[]> targetData = new LinkedHashMap<String, String[]>();
//		targetData.put("PointsAdjust", new String[]{});
//		targetData.put("Effort", new String[]{});
//		
//		cleanPromise("C:\\Users\\dell1\\Documents\\409\\FPA\\dataset\\Promise\\desharnais.xlsx", targetData);
	}

}
