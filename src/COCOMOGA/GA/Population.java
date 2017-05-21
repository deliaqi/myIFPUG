package COCOMOGA.GA;

public class Population {

	Individual[] individuals;
	double BestFitness;
	Individual Fittest;

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
		if(Fittest == null){
			Individual fittest = individuals[0];
			double bestFitness = fittest.getFitness();
			for(int i=1; i<size(); i++){
				if(bestFitness <= getIndividual(i).getFitness()){
					fittest = getIndividual(i);
					bestFitness = getIndividual(i).getFitness();
				}
			}
			BestFitness = bestFitness;
			Fittest = fittest;
		}

		return Fittest;
	}

	public double getBestFitness(){
		if(BestFitness >= 0){
			return getFittest().getFitness();
		}else{
			return BestFitness;
		}
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
