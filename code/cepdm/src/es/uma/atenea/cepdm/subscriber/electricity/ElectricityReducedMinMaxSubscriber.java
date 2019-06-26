package es.uma.atenea.cepdm.subscriber.electricity;

import com.espertech.esper.client.Configuration;
import com.yahoo.labs.samoa.instances.Instance;

import es.uma.atenea.cepdm.event.electricity.Electricity;
import es.uma.atenea.cepdm.event.electricity.ElectricityReducedMinMax;
import es.uma.atenea.cepdm.io.ARFFDataWriter;
import es.uma.atenea.cepdm.subscriber.StatementSubscriber;

/**
 * A subscriber that enriches electricity events with average values of price and demand.
 * The resulting instance is stored in ARFF format.
 * @author Aurora
 * @version 1
 * */

public class ElectricityReducedMinMaxSubscriber implements StatementSubscriber {
	
	/** Window size */
	protected int windowSize;
	
	/**
	 * Default constructor
	 * */
	public ElectricityReducedMinMaxSubscriber() {
		this.windowSize = 10;
	}
	
	/**
	 * Parameterized constructor
	 * @param windowsSize Window size for aggregation function
	 * */
	public ElectricityReducedMinMaxSubscriber(int windowsSize) {
		this.windowSize = windowsSize;
	}

	@Override
	public String getStatement() {
		Configuration configuration = new Configuration();
		configuration.addImport("es.uma.atenea.cepdm.event.electricity.*");
		String expression = "select *, min(nswprice), max(nswprice), min(nswdemand), max(nswdemand),"
				+ " min(vicprice), max(vicprice), min(vicdemand), max(vicdemand) from Electricity#length("+this.windowSize+")";
		return expression;
	}
	
	/**
	 * Update method (listener)
	 * @param originalEvent The complete original instance
	 * @param minNswPrice Minimum price in NSW
	 * @param maxNswPrice Maximum price in NSW
	 * @param minNswDemand Minimum demand in NSW
	 * @param maxNswDemand Maximum demand in NSW
	 * @param minVicPrice Minimum price in Victoria
	 * @param maxVicPrice Maximum price in Victoria
	 * @param minVicDemand Minimum demand in Victoria
	 * @param maxVicDemand Maximum demand in Victoria
	 * */
	public void update(Electricity originalEvent, 
			double minNswPrice, double maxNswPrice, double minNswDemand, double maxNswDemand, 
			double minVicPrice, double maxVicPrice, double minVicDemand, double maxVicDemand) {
		ElectricityReducedMinMax event = new ElectricityReducedMinMax(originalEvent, minNswPrice, maxNswPrice,
				minNswDemand, maxNswDemand, minVicPrice, maxVicPrice, minVicDemand, maxVicDemand);
		Instance instance = event.toMOAInstance();
		ARFFDataWriter writer = ARFFDataWriter.getARFFDataWriter();
		if(!writer.isMetadataWriten()) {
			writer.writeMetadata(event.getConverter().getMetadataAsString());
		}
		writer.writeInstance(event.getConverter().getMetadata(), instance);
	}
}