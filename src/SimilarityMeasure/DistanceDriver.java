package SimilarityMeasure;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DistanceDriver {
	private List<Object> Predicted;
	private Map<String, List<Object>> Analogues;
	private Map<String, Double> Distances;
	
	public DistanceDriver(){
		
	}
	
	public DistanceDriver(List<Object> predicted,
			Map<String, List<Object>> dataset) {
		super();
		this.Predicted = predicted;
		this.Analogues = dataset;
	}

	public Map<String, Double> process(){
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
				sum = sum + distances[index][i];
			}
			Distances.put(analogue, Math.sqrt(sum));
			index++;
		}
		
		return Distances;
		
	}
	
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
				if(distances[i][j] > 1){
					curNorm[j] = 1;
					continue;
				}
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
	
	public List<Object> getPredicted() {
		return Predicted;
	}



	public Map<String, List<Object>> getDataset() {
		return Analogues;
	}



	public void setPredicted(List<Object> predicted) {
		this.Predicted = predicted;
	}



	public void setDataset(Map<String, List<Object>> dataset) {
		this.Analogues = dataset;
	}

}
