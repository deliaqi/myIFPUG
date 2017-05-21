package AnalogyAdjustmentGA.GA;

import java.security.SecureRandom;
import java.util.Random;

public class Individual {

	static int defaultGeneLength = 64;
	//private byte[] genes = new byte[defaultGeneLength];
	private double[] genes = new double[8];

	
	// Cache
	private double fitness = 0;
	private double MMRE = 0;
	private double Pred = 0;
	
	// Create a randow individual
	public void generateIndividual(int seed){
		// Language: 3GL 1.00~1.30;4GL 0.70~1.00
		Random random = new Random(seed);
		double gene_3GL = random.nextDouble()*0.3+1;
		double gene_4GL = random.nextDouble()*0.3+0.7;
		//double gene_3GL = (double)(Math.round(Math.random() * 0.3)+1);
		// Development Platform: PC 0.60~0.90;MR 0.95~1.15;MF 1.00~1.15
		double gene_PC = random.nextDouble() * 0.3+0.6;
		double gene_MR = random.nextDouble() * 0.2+0.95;
		double gene_MF = random.nextDouble() * 0.15+1.0;
		// Development Type: Enhancement/New Development/Re-development 0.85～1.15
		double gene_En = random.nextDouble() * 0.3+0.85;
		double gene_ND = random.nextDouble() * 0.3+0.85;
		double gene_RD = random.nextDouble() * 0.3+0.85;

		genes[0] = gene_3GL;
		genes[1] = gene_4GL;
		genes[2] = gene_PC;
		genes[3] = gene_MR;
		genes[4] = gene_MF;
		genes[5] = gene_En;
		genes[6] = gene_ND;
		genes[7] = gene_RD;
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
			geneString += String.format("%.6f", getGene(i)) + ",";
		}
		return geneString;
	}
	
	
}
