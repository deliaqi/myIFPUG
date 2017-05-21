package AnalogyAdjustmentGA;

import AnalogyAdjustmentGA.GA.Algorithm;
import AnalogyAdjustmentGA.GA.FitnessCalc;
import AnalogyAdjustmentGA.GA.Individual;
import AnalogyAdjustmentGA.GA.Population;

public class GADriver {
	
	private static DistanceDriver distanceDriver;
	public static int MAX_Generation = 50;
	//public static int KeyFactorCount = 3;
//	private static double 
	
	
	public GADriver(DistanceDriver distancedriver){
		distanceDriver = distancedriver;
	}
	
	public double[] process(double[] rawWeights) {
		// Set a candidate solution
		FitnessCalc.setDistanceDriver(distanceDriver);
		//Individual.setDefaultGeneLength(KeyFactorCount);

		// Create an initial population
		Population myPop = new Population(50, true);
		
		// Evolve our population until we reach an optimum solution
		System.out.println("Generation,Fittest,BestGenes");
		int generationCount = 0;
		while (myPop.getFittest().getFitness() < FitnessCalc.getMaxFitness() || generationCount < MAX_Generation) {
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
