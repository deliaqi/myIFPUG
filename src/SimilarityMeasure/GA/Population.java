package SimilarityMeasure.GA;

public class Population {

	Individual[] individuals;

	/*
	 * Constructors
	 */
	// Create a population
	public Population(int populationSize, boolean initialize) {
		individuals = new Individual[populationSize];
		// Initiallize population
		if(initialize){
			for(int i=0; i<size(); i++){
				Individual newIndividual = new Individual();
				newIndividual.generateIndividual();
				saveIndividual(i, newIndividual);
			}
		}
	}

	/* Getters */
	public Individual getIndividual(int index) {
		return individuals[index];
	}
	
	public Individual getFittest(){
		Individual fittest = individuals[0];
		for(int i=0; i<size(); i++){
			if(fittest.getFitness() <= getIndividual(i).getFitness()){
				fittest = getIndividual(i);
			}
		}
		return fittest;
	}

	/* Public methods */
	// Get population size
	public int size() {
		return individuals.length;
	}

	// Save individual
	public void saveIndividual(int index, Individual indiv) {
		individuals[index] = indiv;
	}
}
