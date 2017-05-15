package AdjustmentMechanism;

import AdjustmentMechanism.GA.Algorithm;
import AdjustmentMechanism.GA.FitnessCalc;
import AdjustmentMechanism.GA.Individual;
import AdjustmentMechanism.GA.Population;

public class GADriver {
	
	private static AdjustmentDriver adjustmentDriver;
	public static  int MAX_Generation = 200;
	
	public GADriver(AdjustmentDriver adjustmentdriver){
		adjustmentDriver = adjustmentdriver;
	}
	
	public double[] process(double[] rawWeights) {
		// Set a candidate solution
		FitnessCalc.setAdjustmentDriver(adjustmentDriver);
		Individual.setDefaultGeneLength(adjustmentDriver.getFeatureLength());

		// Create an initial population
		Population myPop = new Population(50, true);
		
		// Evolve our population until we reach an optimum solution
		System.out.println("Generation,Fittest,RELY,DATA,CPLX,TIME,STOR,VIRT,TURN,ACAP,AEXP,PCAP,VEXP,LEXP,MODP,TOOL,SCED");
		int generationCount = 0;
		while (myPop.getFittest().getFitness() < FitnessCalc.getMaxFitness() && generationCount < MAX_Generation) {
			generationCount++;
			System.out.println(generationCount + "," + myPop.getBestFitness() + "," + myPop.getFittest().toString());
			myPop = Algorithm.evolvePopulation(myPop);
		}
		System.out.println("Solution found!");
		System.out.println("Generation: " + generationCount);
		System.out.println("Genes:");
		System.out.println(myPop.getFittest());
		return myPop.getFittest().getGenes();

	}
	
	

}
