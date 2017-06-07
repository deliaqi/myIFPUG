package EvaluationMethod;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.descriptive.rank.Median;

public class EvaluateMRE {
	private List<MRE> ListMRE = new ArrayList<MRE>();
	private double[] MREs;
	private double MMRE;
	private double MdMRE;
	private double Pred;
	
	private static double PredAccuracy = 0.25;
	
	public EvaluateMRE(List<MRE> listMRE) {
		super();
		ListMRE = listMRE;
		this.initMREs(listMRE);
		this.caculateMMRE(MREs);
		this.caculateMdMRE(MREs);
		this.caculatePred(MREs, PredAccuracy);
	}

	public EvaluateMRE() {
		super();
	}

	public void initMREs(List<MRE> listMRE){
		int size = listMRE.size();
		MREs = new double[size];
		for(int i=0;i<size;i++){
			MREs[i] = listMRE.get(i).getRelativeError();
		}
	}
	
	public double caculateMMRE(double[] MREs){
		int size = MREs.length;
		if(size == 0) return 0;
		double sum = 0;
		for(int i=0;i<size;i++){
			sum = sum + MREs[i];
		}
		MMRE = sum/size;
		return MMRE;
	}
	
	public double caculateMdMRE(double[] MREs){
		Median median = new Median();
		MdMRE = median.evaluate(MREs);
		return MdMRE;
	}
	
	public double caculatePred(double[] MREs, double accuracy){
		int size = MREs.length;
		int num = 0;
		for(int i=0; i<size; i++){
			if(MREs[i] <= accuracy){
				num++;
			}
		}
		
		Pred = (double)num/size;
		return Pred;
	}

	public double[] getMREs() {
		return MREs;
	}

	public void setMREs(double[] mREs) {
		MREs = mREs;
	}
	
	public List<MRE> getListMRE() {
		return ListMRE;
	}

	public double getMMRE() {
		return MMRE;
	}

	public double getMdMRE() {
		return MdMRE;
	}

	public void setListMRE(List<MRE> listMRE) {
		ListMRE = listMRE;
	}

	public void setMMRE(double mMRE) {
		MMRE = mMRE;
	}

	public void setMdMRE(double mdMRE) {
		MdMRE = mdMRE;
	}

	public double getPred() {
		return Pred;
	}

	public static double getPredAccuracy() {
		return PredAccuracy;
	}

	public void setPred(double pred) {
		Pred = pred;
	}

	public static void setPredAccuracy(double predAccuracy) {
		PredAccuracy = predAccuracy;
	}

}
