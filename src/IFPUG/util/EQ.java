package IFPUG.util;

public class EQ {
	
	private String Complexity;//   L/M/H
	private int Weight;
	private int Number=1;
	private int DET;
	private int FTR;
	private int TransFunction;
	
	public EQ(String complexity, int weight, int number) {
		super();
		Complexity = complexity;
		Weight = weight;
		Number = number;
		TransFunction = Weight * Number;
	}

	public EQ(String complexity, int weight) {
		super();
		Complexity = complexity;
		Weight = weight;
		Number = 1;
		TransFunction = Weight * Number;
	}

	public String getComplexity() {
		return Complexity;
	}

	public void setComplexity(String complexity) {
		Complexity = complexity;
	}

	public int getWeight() {
		return Weight;
	}

	public void setWeight(int weight) {
		Weight = weight;
	}

	public int getNumber() {
		return Number;
	}

	public void setNumber(int number) {
		this.Number = number;
	}

	public int getDET() {
		return DET;
	}

	public void setDET(int dET) {
		DET = dET;
	}

	public int getFTR() {
		return FTR;
	}

	public void setFTR(int fTR) {
		FTR = fTR;
	}

	public int getTransFunction() {
		return TransFunction;
	}

	public void setTransFunction(int transFunction) {
		TransFunction = transFunction;
	}
	
	
	
	
	
	
	
	
	

}
