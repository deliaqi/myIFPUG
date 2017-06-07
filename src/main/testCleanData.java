package main;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import DataProcessing.cleanData;

public class testCleanData {

	public static void main(String[] args) throws IOException {
		String prefix = "/Users/liujiaqi/OneDrive/文档/毕设/FPA/dataset/ISBSG/";
		String suffix = ".xlsx";
		String dataName = "IFPUG4";
		String sourcePath = prefix + dataName + suffix;
		String resultPath = prefix + "FeatureAnalysis/" + dataName + "_DBMS Used" + suffix;
		
		Map<String, String[]> targetData = new LinkedHashMap<String, String[]>();
		targetData.put("Function Points", new String[]{""});
		targetData.put("Development Type", new String[]{""});
		targetData.put("Development Platform", new String[]{""});
		targetData.put("Language Type", new String[]{""});
		targetData.put("DBMS Used", new String[]{"Yes"});
		targetData.put("Organisation type", new String[]{});
		targetData.put("How Methodology Acquired", new String[]{});
		targetData.put("Effort", new String[]{});
		
		cleanData cleandata = new cleanData();
		cleandata.cleanISBSG(sourcePath, targetData, resultPath);

	}

}
