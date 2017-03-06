package EvaluationMethod;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.descriptive.rank.Median;

public class EvaluateMRE {
	private List<MRE> ListMRE = new ArrayList<MRE>();
	private double[] MREs;
	private double MMRE;
	private double MdMRE;
	
	public EvaluateMRE(List<MRE> listMRE) {
		super();
		ListMRE = listMRE;
		this.initMREs(listMRE);
		this.caculateMMRE(MREs);
		this.caculateMdMRE(MREs);
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

}
