package AnalogyAdjustmentGA.GA;

import AnalogyAdjustmentGA.DistanceDriver;
import EvaluationMethod.EvaluateMRE;
import EvaluationMethod.MRE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
		Map<String,Integer> keyfactorList;

		double[] adjustmentMultiplier = new double[8];
		
		// Loop
		for(String proName : ProjectData.keySet()){
			//distanceDriver.setWeights(individual.getGenes());
			for(int i=0;i<adjustmentMultiplier.length;i++){
				adjustmentMultiplier[i] = 1.0;
			}

			Map<String, Double> Distances = distanceDriver.process(proName);

			double CAEffort = distanceDriver.getCAEffort();
			double actualEffort = distanceDriver.getActualEffort();
			double CAFP = (double)distanceDriver.getCAdata().get(0);
			double actualFP = (double)distanceDriver.getPredicted().get(0);

			// get Key Factors
			keyfactorList = distanceDriver.getKeyFactorList();
			// All KF
			/*for(String keyfactor : keyfactorList.keySet()){
				int tmp = keyfactorList.get(keyfactor);
				if(tmp >= 0 && tmp < 8){
					adjustmentMultiplier[tmp] = individual.getGene(tmp);
				}
			}*/

			// KF1-Language
			int kf1 = keyfactorList.get("Language");
			if(kf1 >= 0 && kf1 < 8){
				adjustmentMultiplier[kf1] = individual.getGene(kf1);
			}

			// KF2-Development Type
//			int kf2 = keyfactorList.get("Development Type");
//			if(kf2 >= 0 && kf2 < 8){
//				adjustmentMultiplier[kf2] = individual.getGene(kf2);
//			}

			// KF2-Development Platform
//			int kf3 = keyfactorList.get("Development Platform");
//			if(kf3 >= 0 && kf3 < 8){
//				adjustmentMultiplier[kf3] = individual.getGene(kf3);
//			}


//			genes[0] = gene_3GL;
//			genes[1] = gene_4GL;
//			genes[2] = gene_PC;
//			genes[3] = gene_MR;
//			genes[4] = gene_MF;
//			genes[5] = gene_En;
//			genes[6] = gene_ND;
//			genes[7] = gene_RD;

			// Estimation
			double productivity = CAEffort / CAFP;

			double adjustFP = actualFP;
			for(int i=0;i<adjustmentMultiplier.length;i++){
				adjustFP = adjustFP/adjustmentMultiplier[i];
			}

			double prediction = productivity * adjustFP;

			MRE curMRE = new MRE(actualEffort, prediction);
			MREs.add(curMRE);
		}
		
		// Evaluation
		EvaluateMRE evaluation = new EvaluateMRE(MREs);
		//System.out.println("MMRE="+evaluation.getMMRE()+"MdMRE="+evaluation.getMdMRE()+
			//	"Pred(0.25)="+evaluation.getPred()+"Pred(0.25)-MMRE="+(evaluation.getPred()-evaluation.getMMRE()));
		
		//fitness = evaluation.getPred() - evaluation.getMMRE();
		fitness = -evaluation.getMMRE();
		
		return fitness;
	}
	
	// Get optimum fitness
	public static double getMaxFitness() {
		return maxFitness;
	}
	

}
