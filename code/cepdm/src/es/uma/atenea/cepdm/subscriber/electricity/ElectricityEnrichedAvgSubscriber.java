package es.uma.atenea.cepdm.subscriber.electricity;

import com.espertech.esper.client.Configuration;
import com.yahoo.labs.samoa.instances.Instance;

import es.uma.atenea.cepdm.event.electricity.Electricity;
import es.uma.atenea.cepdm.event.electricity.ElectricityEnrichedAvg;
import es.uma.atenea.cepdm.io.ARFFDataWriter;
import es.uma.atenea.cepdm.subscriber.StatementSubscriber;

/**
 * A subscriber that enriches electricity events with average values of price and demand.
 * The resulting instance is stored in ARFF format.
 * @author Aurora
 * @version 1
 * */

public class ElectricityEnrichedAvgSubscriber implements StatementSubscriber {

	/** Window size */
	protected int windowSize;
	
	/**
	 * Default constructor
	 * */
	public ElectricityEnrichedAvgSubscriber() {
		this.windowSize = 10;
	}
	
	/**
	 * Parameterized constructor
	 * @param windowsSize Window size for aggregation function
	 * */
	public ElectricityEnrichedAvgSubscriber(int windowsSize) {
		this.windowSize = windowsSize;
	}

	@Override
	public String getStatement() {
		Configuration configuration = new Configuration();
		configuration.addImport("es.uma.atenea.cepdm.event.electricity.*");
		String expression = "select *, avg(nswprice), avg(nswdemand), avg(vicprice), avg(vicdemand) from Electricity#length("+this.windowSize+")";
		return expression;
	}
	
	/**
	 * Update method (listener)
	 * @param originalEvent The complete original instance
	 * @param avgNswPrice Average price in NSW
	 * @param avgNswDemand Average demand in NSW
	 * @param avgVicPrice Average price in Victoria
	 * @param avgVicDemand Average demand in Victoria
	 * */
	public void update(Electricity originalEvent, double avgNswPrice, double avgNswDemand, double avgVicPrice, double avgVicDemand) {
		ElectricityEnrichedAvg event = new ElectricityEnrichedAvg(originalEvent, avgNswPrice, avgNswDemand, avgVicPrice, avgVicDemand);
		Instance instance = event.toMOAInstance();
		ARFFDataWriter writer = ARFFDataWriter.getARFFDataWriter();
		if(!writer.isMetadataWriten()) {
			writer.writeMetadata(event.getConverter().getMetadataAsString());
		}
		writer.writeInstance(event.getConverter().getMetadata(), instance);
	}
}
