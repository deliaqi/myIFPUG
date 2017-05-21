package COCOMOGA;

import java.util.List;
import java.util.Map;

public class AdjustmentDriver {
	private List<Object> Predicted;
	private Map<String, List<Object>> Analogues;
	private Map<String, Double> Distances;

	private static Map<String, List<Object>> ProjectData;
	private static int DataSize;
	private static int FeatureLength;

	private String PredictedName;
	private String CAName;

	public static double A = 2.94;// for COCOMO II.2000



	// Using GA to adjust weight
	private double[] Weights;

	public AdjustmentDriver(Map<String, List<Object>> projectdata){
		ProjectData = projectdata;
		DataSize = ProjectData.size();
		InitializeWeights();
		FeatureLength = Weights.length;
	}

	public AdjustmentDriver(List<Object> predicted,
                            Map<String, List<Object>> dataset) {
		super();
		this.Predicted = predicted;
		this.Analogues = dataset;
		InitializeWeights();
		FeatureLength = Weights.length;
	}

	public void InitializeWeights(int size){
		Weights = new double[size];
		for(int i=0; i<size; i++){
			Weights[i] = 1;
		}
	}

	public void InitializeWeights(){
		if(ProjectData != null){
			Map.Entry<String, List<Object>> entry = ProjectData.entrySet().iterator().next();
			List<Object> tmp = entry.getValue();
			InitializeWeights(tmp.size()-2);
		}else if(Predicted != null && Predicted.size() > 1){
			InitializeWeights(Predicted.size()-2);
		}else{
			System.out.println("Initializing Weights failed!");
		}
	}

	public void TrainWeights(){
		GADriver GAdriver = new GADriver(this);
		Weights = GAdriver.process(Weights);
	}
	
	public double getActualEffort(){
		if(Predicted.get(FeatureLength) instanceof Double){
			return (double)Predicted.get(FeatureLength+1);
		}
		return 0;
	}

	public double adjustmFP(double FPsize){

		return 0;
	}

	public double adjustCocomo(double locsize){
		if(Predicted.size() == 0) return -1;
		double predictedeffort = A * locsize;
		// the first column is FP size, the last column is Effort
		for(int i=1;i<Predicted.size()-1;i++){
			if(Weights[i-1] == 0) continue;
			if(Predicted.get(i) instanceof Double){
				predictedeffort = predictedeffort * (Double)Predicted.get(i);
			}
		}
		return predictedeffort;
	}


	public double process(){
		if(Predicted == null || Predicted.size() == 0) return 0;

		double size = 0,predictedeffort = 0;
		try{
			size = (Double) Predicted.get(0);
		}catch (NumberFormatException e){
			System.out.println("Something wrong with original data!");
		}

		predictedeffort = adjustCocomo(size);
		return predictedeffort;
	}
	
	public double process(String predictedName){
		Predicted = ProjectData.get(predictedName);
		double size = 0,predictedeffort = 0;
		try{
			size = (Double) Predicted.get(0);
		}catch (NumberFormatException e){
			System.out.println("Something wrong with original data!");
		}

		predictedeffort = adjustCocomo(size);
		return predictedeffort;
		
	}


	public double getCAEffort(){
		if(CAName == null || CAName == "") return 0;
		List<Object> data = Analogues.get(CAName);
		if(data.size() > 0){
			Object tmp = Analogues.get(CAName).get(FeatureLength);
			if(tmp instanceof Double){
				return (double)tmp;
			}
		}
		return 0;
	}
	
	public List<Object> getCAdata(){
		if(CAName == null || CAName == "") return null;
		return Analogues.get(CAName);
	}
	
	public double[] getWeights(){
		return Weights;
	}
	
	public List<Object> getPredicted() {
		return Predicted;
	}

	public int getFeatureLength(){
		return FeatureLength;
	}

	public Map<String, List<Object>> getDataset() {
		return ProjectData;
	}

	public void setWeights(double[] weights){
		Weights = weights;
	}

	public void setPredicted(List<Object> predicted) {
		this.Predicted = predicted;
	}

	public void setDataset(Map<String, List<Object>> dataset) {
		this.Analogues = dataset;
	}

}
