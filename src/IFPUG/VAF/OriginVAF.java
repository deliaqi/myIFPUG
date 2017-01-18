package IFPUG.VAF;

public class OriginVAF {
	// 14 Value adjustment factors 
	private int DataCommunications;
	private int DistributedDataProcessing;
	private int Performance;
	private int HeavilyUsedConfiguration;
	private int TransactionRate;
	private int OnlineDataEntry;
	private int EnduserEfficiency;
	private int OnlineUpdate;
	private int ComplexProcessing;
	private int Reusability;
	private int InstallationEase;
	private int OperationalEase;
	private int MultipleSites;
	private int FacilitateChange;
	
	// Total
	private double AdjustmentPoint;
	
	public OriginVAF(){
	}
	
	public OriginVAF(int adjustmentpoint){
		AdjustmentPoint = adjustmentpoint;
	}
	
	public double computeAdujstment(){
		return 0.65 + ((DataCommunications+DistributedDataProcessing+Performance+HeavilyUsedConfiguration
				+TransactionRate+OnlineDataEntry+EnduserEfficiency+OnlineUpdate+ComplexProcessing
				+Reusability+InstallationEase+OperationalEase+MultipleSites+FacilitateChange)/100);
	}

	public double getAdjustmentPoint() {
		if(AdjustmentPoint > 0) return AdjustmentPoint;
		return computeAdujstment();
	}

	public void setDataCommunications(int dataCommunications) {
		DataCommunications = dataCommunications;
	}
	
	public void setDistributedDataProcessing(int distributedDataProcessing) {
		DistributedDataProcessing = distributedDataProcessing;
	}

	public void setPerformance(int performance) {
		Performance = performance;
	}

	public void setHeavilyUsedConfiguration(int heavilyUsedConfiguration) {
		HeavilyUsedConfiguration = heavilyUsedConfiguration;
	}

	public void setTransactionRate(int transactionRate) {
		TransactionRate = transactionRate;
	}

	public void setOnlineDataEntry(int onlineDataEntry) {
		OnlineDataEntry = onlineDataEntry;
	}

	public void setEnduserEfficiency(int enduserEfficiency) {
		EnduserEfficiency = enduserEfficiency;
	}

	public void setOnlineUpdate(int onlineUpdate) {
		OnlineUpdate = onlineUpdate;
	}

	public void setComplexProcessing(int complexProcessing) {
		ComplexProcessing = complexProcessing;
	}

	public void setReusability(int reusability) {
		Reusability = reusability;
	}

	public void setInstallationEase(int installationEase) {
		InstallationEase = installationEase;
	}

	public void setOperationalEase(int operationalEase) {
		OperationalEase = operationalEase;
	}

	public void setMultipleSites(int multipleSites) {
		MultipleSites = multipleSites;
	}

	public void setFacilitateChange(int facilitateChange) {
		FacilitateChange = facilitateChange;
	}

	public void setAdjustmentPoint(int adjustmentPoint) {
		AdjustmentPoint = adjustmentPoint;
	}
	
	

}
