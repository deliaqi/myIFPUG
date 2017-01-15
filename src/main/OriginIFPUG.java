package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import DataProcessing.readExcel;
import IFPUGComponent.Component;

public class OriginIFPUG {
	public static List<Component> CList = new ArrayList<Component>();
	
	public static void main(String[] args){
		try {
			int FP = 0;
			readExcel.read("C:\\Users\\dell1\\Documents\\409\\FPA\\dataset\\P3.xlsx",CList);
			for(int i=0;i<CList.size();i++){
				if(CList.get(i) != null){
					FP += CList.get(i).getFunctionPoint();
				}
			}
			
			System.out.println(FP);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
