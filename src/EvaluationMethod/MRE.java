package EvaluationMethod;

public class MRE {
	
	private double actualEffort;
	private double estimationEffort;
	private double RelativeError;
	
	public MRE(double actualEffort, double estimationEffort) {
		super();
		this.actualEffort = actualEffort;
		this.estimationEffort = estimationEffort;
		this.RelativeError = Math.abs(actualEffort-estimationEffort)/(actualEffort);
	}
	
	public double getActualEffort() {
		return actualEffort;
	}
	public double getEstimationEffort() {
		return estimationEffort;
	}
	public double getRelativeError() {
		return RelativeError;
	}
	public void setActualEffort(double actualEffort) {
		this.actualEffort = actualEffort;
	}
	public void setEstimationEffort(double estimationEffort) {
		this.estimationEffort = estimationEffort;
	}
	public void setRelativeError(double relativeError) {
		RelativeError = relativeError;
	}
	
	
	
	

}
