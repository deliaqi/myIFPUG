package AdjustmentMechanism.GA;

import AdjustmentMechanism.AdjustmentDriver;
import EvaluationMethod.EvaluateMRE;
import EvaluationMethod.MRE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FitnessCalc {
	
	//static byte[] solution = new byte[64];
	//static double[] solution = new double[64];
	static double ActualEffort;
	static AdjustmentDriver adjustmentDriver;
	static double maxFitness = 1;
	
	/* Public methods */
	
	public static void setAdjustmentDriver(AdjustmentDriver adjustmentdriver){
		adjustmentDriver = adjustmentdriver;
	}
	
	// Calculate individuals fitness by comparing it to our candidate solution
	static double getFitness(Individual individual) {
		double fitness = 0;
		double[] weights = individual.getGenes();
		List<MRE> MREs = new ArrayList<MRE>();
		Map<String, List<Object>> ProjectData = adjustmentDriver.getDataset();
		
		// Loop
		for(String proName : ProjectData.keySet()){
			adjustmentDriver.setWeights(individual.getGenes());
			double predictedEffort = adjustmentDriver.process(proName);

			double actualEffort = adjustmentDriver.getActualEffort();
			
			// Evaluation
			MRE curMRE = new MRE(actualEffort, predictedEffort);
			MREs.add(curMRE);
		}
		
		// Evaluation
		EvaluateMRE evaluation = new EvaluateMRE(MREs);
		//System.out.println("MMRE="+evaluation.getMMRE()+"MdMRE="+evaluation.getMdMRE()+
				//"Pred(0.25)="+evaluation.getPred()+"Pred(0.25)-MMRE="+(evaluation.getPred()-evaluation.getMMRE()));
		
		fitness = evaluation.getPred() - evaluation.getMMRE();
		//fitness = -evaluation.getMMRE();
		
		return fitness;
	}
	
	// Get optimum fitness
	public static double getMaxFitness() {
		return maxFitness;
	}
	

}
