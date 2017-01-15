package IFPUG.util;

public class ILF {
	private String Complexity;//   L/M/H
	private int Weight;
	private int Number=1;
	private int DET;
	private int RET;
	private int DataFunction;
	
	public ILF(String complexity, int weight, int number) {
		super();
		Complexity = complexity;
		Weight = weight;
		Number = number;
		DataFunction = Weight * Number;
	}
	
	public ILF(String complexity, int weight) {
		super();
		Complexity = complexity;
		Weight = weight;
		Number = 1;
		DataFunction = Weight * Number;
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
	public int getRET() {
		return RET;
	}
	public void setRET(int rET) {
		RET = rET;
	}

	public int getDataFunction() {
		return DataFunction;
	}

	public void setDataFunction(int dataFunction) {
		DataFunction = dataFunction;
	}
	
	

}
