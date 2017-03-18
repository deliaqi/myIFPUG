package SimilarityMeasure;

import java.util.List;
import java.util.Map;

import SimilarityMeasure.GA.Algorithm;
import SimilarityMeasure.GA.FitnessCalc;
import SimilarityMeasure.GA.Individual;
import SimilarityMeasure.GA.Population;

public class GADriver {
	
	private static DistanceDriver distanceDriver;
//	private static double 
	
	
	public GADriver(DistanceDriver distancedriver){
		distanceDriver = distancedriver;
	}
	
	public void process(double[] rawWeights) {
		// Set a candidate solution
		FitnessCalc.setDistanceDriver(distanceDriver);
		Individual.setDefaultGeneLength(distanceDriver.getFeatureLength());

		// Create an initial population
		Population myPop = new Population(50, true);
		
		// Evolve our population until we reach an optimum solution
		int generationCount = 0;
		while (myPop.getFittest().getFitness() < FitnessCalc.getMaxFitness()) {
			generationCount++;
			System.out.println("Generation: " + generationCount + " Fittest: " + myPop.getBestFitness());
			myPop = Algorithm.evolvePopulation(myPop);
		}
		System.out.println("Solution found!");
		System.out.println("Generation: " + generationCount);
		System.out.println("Genes:");
		System.out.println(myPop.getFittest());

	}
	
	

}
