package main;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import DataProcessing.cleanData;

public class testCleanData {

	public static void main(String[] args) throws IOException {
		String prefix = "C:\\Users\\dell1\\Documents\\409\\FPA\\dataset\\ISBSG\\";
		String suffix = ".xlsx";
		String dataName = "IFPUG4";
		String sourcePath = prefix + dataName + suffix;
		String resultPath = prefix + dataName + "_large" + suffix;
		
		Map<String, String[]> targetData = new LinkedHashMap<String, String[]>();
		targetData.put("Function Points", new String[]{"2000-10000"});
		targetData.put("Development Type", new String[]{""});
		targetData.put("Development Platform", new String[]{""});
		targetData.put("Language Type", new String[]{""});
		targetData.put("DBMS Used", new String[]{});
		targetData.put("Organisation type", new String[]{});
		targetData.put("How Methodology Acquired", new String[]{});
		targetData.put("Effort", new String[]{});
		
		cleanData cleandata = new cleanData();
		cleandata.cleanISBSG(sourcePath, targetData, resultPath);

	}

}
