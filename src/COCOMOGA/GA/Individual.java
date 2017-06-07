package COCOMOGA.GA;

public class Individual {

	static int defaultGeneLength = 64;
	//private byte[] genes = new byte[defaultGeneLength];
	private double[] genes = new double[defaultGeneLength];
	
	// Cache
	private double fitness = 0;
	private double MMRE = 0;
	private double Pred = 0;
	
	// Create a randow individual
	public void generateIndividual(){
		for(int i = 0; i < size(); i++){
			// Weight: {0，1}
			double gene = (double)Math.round(Math.random());
			genes[i] = gene;
		}
	}
	
	/* Getters and setters */
	// Use this if you want to create individuals with different gene lengths
	public static void setDefaultGeneLength(int length){
		defaultGeneLength = length;
	}
	
	public double getGene(int index){
		return genes[index];
	}
	
	public void setGene(int index, double value){
		genes[index] = value;
		fitness = 0;
		MMRE = 0;
		Pred = 0;
	}
	
	public double[] getGenes(){
		return genes;
	}
	
	/* Public methods */
	public int size(){
		return genes.length;
	}
	
	public double getFitness(){
		if(fitness == 0){
			fitness = FitnessCalc.getFitness(this);
		}
		return fitness;
	}

	@Override
	public String toString() {
		String geneString = "";
		for(int i=0; i<size(); i++){
			geneString += (int)getGene(i) + ",";
		}
		return geneString;
	}
	
	
}
