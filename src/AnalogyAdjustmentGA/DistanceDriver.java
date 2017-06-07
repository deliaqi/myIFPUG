package AnalogyAdjustmentGA;

import java.util.*;

public class DistanceDriver {
	private List<Object> Predicted;
	private Map<String, List<Object>> Analogues;
	private Map<String, Double> Distances;
	
	private static Map<String, List<Object>> ProjectData;
	private static int DataSize;
	private static int FeatureLength;
	
	private String PredictedName;
	private String CAName;



	private static Map<String, Integer> KeyFactorList = new HashMap<String, Integer>();
//	genes[0] = gene_3GL;
//	genes[1] = gene_4GL;
//	genes[2] = gene_PC;
//	genes[3] = gene_MR;
//	genes[4] = gene_MF;
//	genes[5] = gene_En;
//	genes[6] = gene_ND;
//	genes[7] = gene_RD;

	
	// Using GA to adjust weight
	private double[] SimilarityWeights;
	private double[] AdjustmentWeights;
	
	public DistanceDriver(Map<String, List<Object>> projectdata){
		ProjectData = projectdata;
		DataSize = ProjectData.size();
		InitializeSimilarityWeights();
		InitializeAdjustmentWeights(8);
		FeatureLength = SimilarityWeights.length;
	}
	
	public DistanceDriver(List<Object> predicted,
			Map<String, List<Object>> dataset) {
		super();
		this.Predicted = predicted;
		this.Analogues = dataset;
		InitializeSimilarityWeights();
		InitializeAdjustmentWeights(8);
		FeatureLength = SimilarityWeights.length;
	}
	
	public void InitializeAdjustmentWeights(int size){
		AdjustmentWeights = new double[size];
		for(int i=0; i<size; i++){
			AdjustmentWeights[i] = 1;
		}
	}

	public void InitializeSimilarityWeights(int size){
		SimilarityWeights = new double[size];
		for(int i=0; i<size; i++){
			SimilarityWeights[i] = 1;
		}
	}

	public void InitializeSimilarityWeights(){
		if(ProjectData != null){
			Map.Entry<String, List<Object>> entry = ProjectData.entrySet().iterator().next();
			List<Object> tmp = entry.getValue();
			InitializeSimilarityWeights(tmp.size()-1);
		}else if(Predicted != null && Predicted.size() > 1){
			InitializeSimilarityWeights(Predicted.size()-1);
		}else{
			System.out.println("Initializing Weights failed!");
		}
	}
	
	public void TrainWeights(){
		GADriver GAdriver = new GADriver(this);
		SimilarityWeights = GAdriver.process(SimilarityWeights);
	}

	public double getActualEffort(){
		if(Predicted.get(FeatureLength) instanceof Double){
			return (double)Predicted.get(FeatureLength);
		}
		return 0;
	}

	/*public Map<String, Double> process(){
		double[][] distances = new double[Analogues.size()][];
		int index = 0;
		for(String analogue : Analogues.keySet()){
			EuclidianDistance ED = new EuclidianDistance(Predicted, Analogues.get(analogue));
			distances[index++] = ED.getDistance();
		}
		distances = normalizeDistance(distances);
		Distances = new LinkedHashMap<String, Double>();
		index = 0;
		for(String analogue : Analogues.keySet()){
			double sum = 0;
			for(int i=0;i<distances[index].length;i++){
				sum = sum + SimilarityWeights[i] * distances[index][i];
			}
			Distances.put(analogue, Math.sqrt(sum));
			index++;
		}

		CAName = getMinDistance();

		return Distances;

	}*/

	public Map<String, Double> process(String predictedName){
		double[][] distances = new double[DataSize-1][];
		Map<String,double[]> distancesRecord = new LinkedHashMap<String,double[]>();

		Predicted = ProjectData.get(predictedName);
		Analogues = new LinkedHashMap<String, List<Object>>(ProjectData);
		Analogues.remove(predictedName);

		int index = 0;
		for(String analogue : Analogues.keySet()){
			EuclidianDistance ED = new EuclidianDistance(Predicted, Analogues.get(analogue));
			distances[index++] = ED.getDistance();
			distancesRecord.put(analogue,ED.getDistance());
		}


		distances = normalizeDistance(distances);
		Distances = new LinkedHashMap<String, Double>();
		index = 0;
		for(String analogue : Analogues.keySet()){
			double sum = 0;
			for(int i=0;i<distances[index].length;i++){
				sum = sum + SimilarityWeights[i] * distances[index][i];
			}
			Distances.put(analogue, Math.sqrt(sum));
			index++;
		}

		CAName = getMinDistance();
		double[] CAdistance = distancesRecord.get(CAName);

		setKeyFactorList(CAdistance[3],CAdistance[4],CAdistance[5]);

		return Distances;

	}

	public void setKeyFactorList(double DTdistance,double DPdistance,double LTdistance){
		//	genes[0] = gene_3GL;
		//	genes[1] = gene_4GL;
		//	genes[2] = gene_PC;
		//	genes[3] = gene_MR;
		//	genes[4] = gene_MF;
		//	genes[5] = gene_En;
		//	genes[6] = gene_ND;
		//	genes[7] = gene_RD;
		if(Predicted == null || Predicted.size() < 11) return;

		if(DTdistance != 0){
			// get and save Development Type
			String DT = (String) Predicted.get(3);
			if(DT.equalsIgnoreCase("Enhancement")) KeyFactorList.put("Development Type",5);
			else if(DT.equalsIgnoreCase("New Development")) KeyFactorList.put("Development Type",6);
			else if(DT.equalsIgnoreCase("Re-development")) KeyFactorList.put("Development Type",7);
			else KeyFactorList.put("Development Type",-1);
		}else{
			KeyFactorList.put("Development Type",-1);
		}

		if(DPdistance != 0){
			// get and save Development Platform
			String DP = (String) Predicted.get(4);
			if(DP.equalsIgnoreCase("PC")) KeyFactorList.put("Development Platform",2);
			else if(DP.equalsIgnoreCase("MR")) KeyFactorList.put("Development Platform",3);
			else if(DP.equalsIgnoreCase("MF")) KeyFactorList.put("Development Platform",4);
			else KeyFactorList.put("Development Platform",-1);
		}else{
			KeyFactorList.put("Development Platform",-1);
		}

		if(LTdistance != 0){
			// get and save Language
			String language = (String) Predicted.get(5);
			if(language.equalsIgnoreCase("3GL")) KeyFactorList.put("Language", 0);

			else if(language.equalsIgnoreCase("4GL")) KeyFactorList.put("Language",1);
			else KeyFactorList.put("Language",-1);
		}else{
			KeyFactorList.put("Language",-1);
		}


	}

	/*public Map<String, Double> process(double[] Weights){
		double[][] distances = new double[Analogues.size()][];
		int index = 0;
		for(String analogue : Analogues.keySet()){
			EuclidianDistance ED = new EuclidianDistance(Predicted, Analogues.get(analogue));
			distances[index++] = ED.getDistance();
		}
		distances = normalizeDistance(distances);
		Distances = new LinkedHashMap<String, Double>();
		index = 0;
		for(String analogue : Analogues.keySet()){
			double sum = 0;
			for(int i=0;i<distances[index].length;i++){
				sum = sum + Weights[i] * distances[index][i];
			}
			Distances.put(analogue, Math.sqrt(sum));
			index++;
		}
		
		CAName = getMinDistance();
		
		return Distances;
		
	}*/
	
	public double[][] normalizeDistance(double[][] distances){
		Map<Integer, double[]> MinMax = findMinMax(distances);
		double[][] normDistance = new double[distances.length][];
		for(int i=0;i<distances.length;i++){
			double[] curNorm = new double[MinMax.size()];
			for(int j=0;j<MinMax.size();j++){
				if(MinMax.get(j)[1] == MinMax.get(j)[0]){
					curNorm[j] = 0;
					if(MinMax.get(j)[1] == 1){
						curNorm[j] = 1;
					}
					continue;
				}
//				if(distances[i][j] > 1){
//					curNorm[j] = 1;
//					continue;
//				}
				curNorm[j] = (distances[i][j]-MinMax.get(j)[0]) / Math.abs((MinMax.get(j)[1]-MinMax.get(j)[0]));
			}
			normDistance[i] = curNorm;
		}
		return normDistance;
		
	}
	
	public Map<Integer, double[]> findMinMax(double[][] distances){
		Map<Integer, double[]> result = new LinkedHashMap<Integer, double[]>();
		for(int i=0;i<distances[0].length;i++){
			double min = distances[0][i],max = distances[0][i];
			for(int j=1;j<distances.length;j++){
				if(distances[j][i]<min) min = distances[j][i];
				if(distances[j][i]>max) max = distances[j][i];
			}
			double[] minmax = new double[2];
			minmax[0] = min;
			minmax[1] = max;
			result.put(i, minmax);
		}
		return result;
	}
	
	public String getMinDistance(){
		if(Distances == null) return "";
		Set<String> keyset = Distances.keySet();
		double MinDistance = Double.MAX_VALUE;
		String Name = null;
		for(String name : Distances.keySet()){
			if(Distances.get(name) < MinDistance){
				MinDistance = Distances.get(name);
				Name = name;
			}
		}
		return Name;
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
		return SimilarityWeights;
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

	public Map<String, Integer> getKeyFactorList() {
		return KeyFactorList;
	}

	public double[] getAdjustmentWeights() {
		return AdjustmentWeights;
	}

	public void setAdjustmentWeights(double[] adjustmentWeights) {
		AdjustmentWeights = adjustmentWeights;
	}

	public void setWeights(double[] weights){
		SimilarityWeights = weights;
	}

	public void setPredicted(List<Object> predicted) {
		this.Predicted = predicted;
	}



	public void setDataset(Map<String, List<Object>> dataset) {
		this.Analogues = dataset;
	}

}
