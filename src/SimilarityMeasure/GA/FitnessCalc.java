package SimilarityMeasure.GA;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import EvaluationMethod.EvaluateMRE;
import EvaluationMethod.MRE;
import SimilarityMeasure.DistanceDriver;

public class FitnessCalc {
	
	//static byte[] solution = new byte[64];
	//static double[] solution = new double[64];
	static double ActualEffort;
	static DistanceDriver distanceDriver;
	static double maxFitness = 1;
	
	/* Public methods */
	
	public static void setDistanceDriver(DistanceDriver distancedriver){
		distanceDriver = distancedriver;
	}
	
	// Calculate individuals fitness by comparing it to our candidate solution
	static double getFitness(Individual individual) {
		double fitness = 0;
		double[] weights = individual.getGenes();
		List<MRE> MREs = new ArrayList<MRE>();
		Map<String, List<Object>> ProjectData = distanceDriver.getDataset();
		
		// Loop
		for(String proName : ProjectData.keySet()){
			distanceDriver.setWeights(individual.getGenes());
			Map<String, Double> Distances = distanceDriver.process(proName);
			
			double CAEffort = distanceDriver.getCAEffort();
			double actualEffort = distanceDriver.getActualEffort();
			double CAFP = (double)distanceDriver.getCAdata().get(0);
			double actualFP = (double)distanceDriver.getPredicted().get(0);
			
			// Estimation
			double productivity = CAEffort / CAFP;
			double prediction = productivity * actualFP;
			MRE curMRE = new MRE(actualEffort, prediction);
			MREs.add(curMRE);
		}
		
		// Evaluation
		EvaluateMRE evaluation = new EvaluateMRE(MREs);
		//System.out.println("MMRE="+evaluation.getMMRE()+"MdMRE="+evaluation.getMdMRE()+
			//	"Pred(0.25)="+evaluation.getPred()+"Pred(0.25)-MMRE="+(evaluation.getPred()-evaluation.getMMRE()));
		
		fitness = evaluation.getPred() - evaluation.getMMRE();
		//fitness = -evaluation.getMMRE();
		
		return fitness;
	}
	
	// Get optimum fitness
	public static double getMaxFitness() {
		return maxFitness;
	}
	

}
