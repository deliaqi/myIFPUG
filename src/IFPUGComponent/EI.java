package IFPUGComponent;

public class EI extends Component{
	private int FTR;
	private int TransFunction;
	
	public EI(){
		super();
	}
	
	public EI(String complexity, int weight) {
		super(complexity, weight);
		TransFunction = weight;
	}
	
	
	public EI(String complexity, int weight, int number) {
		super(complexity, weight, number);
		TransFunction = weight * number;
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
