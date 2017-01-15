package IFPUGComponent;

public class EQ extends Component{
	private int FTR;
	private int TransFunction;
	
	public EQ(){
		super();
	}

	public EQ(String complexity, int weight) {
		super(complexity, weight);
		TransFunction = weight;
	}
	
	
	public EQ(String complexity, int weight, int number) {
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
