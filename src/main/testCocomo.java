package main;

import java.io.IOException;

import EffortEstimation.COCOMO2;
import EvaluationMethod.EvaluateMRE;

public class testCocomo {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String prefix = "/Users/liujiaqi/OneDrive/文档/毕设/FPA/dataset/Cocomo/";
		String suffix = ".xlsx";
		String dataName = "cocomonasa";
		String datapath = prefix + dataName + suffix;
		String EMpath = prefix + "EMValue" + suffix;
		String resultpath = prefix + dataName +"_result_withEM" + suffix;
		
		COCOMO2.EMValueTable = COCOMO2.readEMValue(EMpath);
		COCOMO2 cocomo2 = new COCOMO2();
		cocomo2.setResultpath(resultpath);
		cocomo2.process(datapath);
		
		EvaluateMRE evaluation = new EvaluateMRE(cocomo2.getMREs());
		System.out.println("MMRE="+String.format("%.2f", evaluation.getMMRE())+"MdMRE="+String.format("%.2f", evaluation.getMdMRE())+"Pred(0.25)="+String.format("%.2f", evaluation.getPred()));

	}

}
