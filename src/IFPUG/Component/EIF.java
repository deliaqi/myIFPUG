package IFPUG.Component;

public class EIF extends Component{
	private int RET;
	private int DataFunction;
	
	public EIF(){
		super();
	}

	public EIF(String complexity, int weight) {
		super(complexity, weight);
		DataFunction = weight;
	}
	
	public EIF(String complexity, int weight, int number) {
		super(complexity, weight, number);
		DataFunction = weight * number;
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
	
	public void computeComplexity(int[][] table){
		if((DET >= table[0][2] && RET < table[1][0]) || 
		   (DET >= table[0][1] && DET < table[0][2] && RET >= table[1][1] && RET < table[1][2]) ||
		   (DET < table[0][1] && RET >= table[1][2])){
			Complexity = "M";
			Weight = table[3][1];
		}else if((DET < table[0][1] && RET < table[1][1]) || 
		   (DET >= table[0][1] && DET < table[0][2] && RET < table[1][1]) ||
		   (DET < table[0][1] && RET >= table[1][1] && RET < table[1][2])){
			Complexity = "L";
			Weight = table[3][0];
//		}else if((DET >= table[0][2] && RET < table[1][0]) || 
//				 (DET >= table[0][1] && DET < table[0][2] && RET >= table[1][1] && RET < table[1][2]) ||
//				 (DET < table[0][1] && RET >= table[1][2])){
//			
		}else{
			Complexity = "H";
			Weight = table[3][2];
		}
	}
	
	

}
