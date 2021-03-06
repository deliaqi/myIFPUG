package IFPUG.Component;

public abstract class Component {
	protected String Complexity;//   L/M/H
	protected int Weight;
	protected int Number=1;
	protected int DET;
	//private int FunctionPoint;
	
	public Component() {
	}
	
	public Component(String complexity, int weight){
		Complexity = complexity;
		Weight = weight;
	}

	public Component(String complexity, int weight, int number) {
		this(complexity, weight);
		Number = number;
		//FunctionPoint = weight * number;
	}
	
	public String getComplexity() {
		return Complexity;
	}
	public int getWeight() {
		return Weight;
	}
	public int getNumber() {
		return Number;
	}
	public int getDET() {
		return DET;
	}
	public void setComplexity(String complexity) {
		Complexity = complexity;
	}
	public void setWeight(int weight) {
		Weight = weight;
	}
	public void setNumber(int number) {
		Number = number;
	}
	public void setDET(int dET) {
		DET = dET;
	}
	
	public int getFunctionPoint(){
		return Weight * Number;
	}

	public void setRET(int RET) {
		// TODO Auto-generated method stub
		
	}

	public void computeComplexity(int[][] table) {
		// TODO Auto-generated method stub
		
	}
	

}
