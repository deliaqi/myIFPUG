package AnalogyAdjustmentGA;

import java.util.List;

public abstract class Distance {
	protected List<Object> X;
	protected List<Object> Y;
	protected double[] Distance;
	
	public Distance(List<Object> x, List<Object> y) {
		super();
		X = x;
		Y = y;
	}

	public List<Object> getX() {
		return X;
	}

	public List<Object> getY() {
		return Y;
	}

	public void setX(List<Object> x) {
		X = x;
	}

	public void setY(List<Object> y) {
		Y = y;
	}
	
	public double[] getDistance() {
		return Distance;
	}

}
