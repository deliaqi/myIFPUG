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
	
	

}
