package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import DataProcessing.readData;
import IFPUG.Component.Component;

public class IFPUG_DT {
	
	public static int[][] DataComplexity = {{1,20,51},{1,2,6},{5,7,15},{7,10,10}};
	public static int DFP;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String prePath = "/Users/liujiaqi/OneDrive/文档/毕设/FPA/dataset/selfData/";
		String suffix = ".xlsx";
		List<Component> CList = new ArrayList<Component>();
		
		readData.readDataFilefromExcel(prePath+"ILF"+suffix, CList, DataComplexity);
		readData.readDataFilefromExcel(prePath+"EIF"+suffix, CList, DataComplexity);
		
		// Caculate DFP
		for(Component component : CList){
			DFP = DFP + component.getFunctionPoint();
		}
		
		System.out.println("DFP="+DFP);

	}

}
