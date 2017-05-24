package AnalogyAdjustmentGA.GA;

import java.util.Random;

public class Algorithm {
	/* GA parameters */
	private static final double uniformRate = 0.5;// for crossover
	private static final double mutationRate = 0.3;
	private static final int tournamentSize = 5;// for tournament selection
	private static final boolean elitism = false;
	
	/* Public methods */
	
	// Evolve a population
	public static Population evolvePopulation(Population pop) {
		Population newPopulation = new Population(pop.size(), false);

		// Keep our best individual
		if (elitism) {
			newPopulation.saveIndividual(0, pop.getFittest());
		}

		// Crossover population
		int elitismOffset;
		if (elitism) {
			elitismOffset = 1;
		} else {
			elitismOffset = 0;
		}

		// Loop over the population size and create new individuals with
		// crossover
		for (int i = elitismOffset; i < pop.size(); i++) {
			Individual indiv1 = tournamentSelection(pop);
			Individual indiv2 = tournamentSelection(pop);
			Individual newIndiv = crossover(indiv1, indiv2);
			newPopulation.saveIndividual(i, newIndiv);
		}

		// Mutate population
		for (int i = elitismOffset; i < newPopulation.size(); i++) {
			mutate(newPopulation.getIndividual(i));
		}

		return newPopulation;
	}

	// Crossover individuals
	private static Individual crossover(Individual indiv1, Individual indiv2) {
		Individual newSol = new Individual();
		// Loop through genes
		for (int i = 0; i < indiv1.size(); i++) {
			// Crossover
			if (Math.random() <= uniformRate) {
				newSol.setGene(i, indiv1.getGene(i));
			} else {
				newSol.setGene(i, indiv2.getGene(i));
			}
		}
		return newSol;
	}

	// Mutate an individual
	private static void mutate(Individual indiv) {
		// Loop through genes
		for (int i = 0; i < indiv.size(); i++) {
			if (Math.random() <= mutationRate) {
				double gene = 1.0;
				// Create random gene : 1~10

				Random random = new Random(System.currentTimeMillis()+i*500);
				// Language: 3GL 1.00~1.30;4GL 0.70~1.00
				if (i == 0) gene = random.nextDouble()*0.3+1;//3GL
				else if(i == 1) gene = random.nextDouble()*0.3+0.7;//4GL
					// Development Platform: PC 0.60~0.90;MR 0.95~1.15;MF 1.00~1.15
				else if(i == 2) gene = random.nextDouble() * 0.3+0.6;//PC
				else if(i == 3) gene = random.nextDouble() * 0.2+0.95;//MR
				else if(i == 4) gene = random.nextDouble() * 0.15+1.0;//MF
					// Development Type: Enhancement/New Development/Re-development 0.85～1.15
				else gene = random.nextDouble() * 0.3+0.85;//Enhancement

				indiv.setGene(i, gene);
			}
		}
	}

	// Select individuals for crossover
	private static Individual tournamentSelection(Population pop){
		// Create a tournament population
		Population tournament = new Population(tournamentSize, false);
		// For each place in the tournament get a random individual
		for(int i=0; i<tournamentSize; i++){
			int randomId = (int) (Math.random() * pop.size());
			tournament.saveIndividual(i, pop.getIndividual(randomId));;
		}
		// Get fittest
		Individual fittest = tournament.getFittest();
		return fittest;
	}



	
}
