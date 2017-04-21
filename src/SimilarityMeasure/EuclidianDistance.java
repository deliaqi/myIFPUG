package SimilarityMeasure;

import java.util.List;

public class EuclidianDistance extends Distance{
	

	public EuclidianDistance(List<Object> predicted, List<Object> analogue) {
		super(predicted, analogue);
		this.Distance = caculateDistance(predicted, analogue);
		// TODO Auto-generated constructor stub
	}
	
	public double[] caculateDistance(List<Object> predicted, List<Object> analogue){
		if(predicted.size() != analogue.size()) return null;
		Distance = new double[predicted.size()-1];
		for(int i=0;i<predicted.size()-1;i++){
			if((predicted.get(i) instanceof Double) && (analogue.get(i) instanceof Double)){
				Distance[i] = DoubleEuclidian((Double)predicted.get(i), (Double)analogue.get(i));
			}else if((predicted.get(i) instanceof String) && (analogue.get(i) instanceof String)){
				Distance[i] = StringEuclidian((String)predicted.get(i), (String)analogue.get(i));
			}else{
				break;
			}
		}
		return Distance;
	}
	
	// Funcrion Points RE
	public double DoubleEuclidian(double predicted, double analogue){
		return Math.abs(predicted-analogue);
	}
	
	public double StringEuclidian(String predicted, String analogue){
		if(predicted.equalsIgnoreCase(analogue)){
			return 0;
		}
		return 1;
	}
	

}
