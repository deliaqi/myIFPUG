package AdjustmentMechanism;

import AdjustmentMechanism.GA.Algorithm;
import AdjustmentMechanism.GA.FitnessCalc;
import AdjustmentMechanism.GA.Individual;
import AdjustmentMechanism.GA.Population;

public class GADriver {
	
	private static AdjustmentDriver adjustmentDriver;
	
	public GADriver(AdjustmentDriver adjustmentdriver){
		adjustmentDriver = adjustmentdriver;
	}
	
	public double[] process(double[] rawWeights) {
		// Set a candidate solution
		FitnessCalc.setDistanceDriver(adjustmentDriver);
		Individual.setDefaultGeneLength(adjustmentDriver.getFeatureLength());

		// Create an initial population
		Population myPop = new Population(50, true);
		
		// Evolve our population until we reach an optimum solution
		System.out.println("Generation,Fittest,BestGenes");
		int generationCount = 0;
		while (myPop.getFittest().getFitness() < FitnessCalc.getMaxFitness()) {
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
