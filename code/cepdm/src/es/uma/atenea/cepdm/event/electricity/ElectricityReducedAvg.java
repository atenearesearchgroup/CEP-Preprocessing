package es.uma.atenea.cepdm.event.electricity;

import es.uma.atenea.cepdm.io.converter.EventToInstanceConverter;
import es.uma.atenea.cepdm.io.converter.electricity.ElectricityReducedAvgToInstanceConverter;

/**
 * A derived electricity event with additional information about
 * average price and demand. Original attributes are excluded.
 * @author Aurora
 * @version 1
 * */

public class ElectricityReducedAvg extends ElectricityEnrichedAvg {

	/**
	 * Empty constructor
	 * */
	public ElectricityReducedAvg() {
		super();
	}
	
	/**
	 * Parameterized constructor
	 * @param elecEvent An instance of the superclass
	 * @param avgNswPrice Average NSW price
	 * @param avgNswDemand Average NSW demand
	 * @param avgVicPrice Average VIC price
	 * @param avgVicDemand Average VIC demand
	 * */
	public ElectricityReducedAvg(Electricity elecEvent, double avgNswPrice, double avgNswDemand, double avgVicPrice, double avgVicDemand) {
		super(elecEvent,avgNswPrice,avgNswDemand,avgVicPrice,avgVicDemand);
	}
	
	@Override
	protected EventToInstanceConverter createConverter() {
		ElectricityReducedAvgToInstanceConverter converter = 
				ElectricityReducedAvgToInstanceConverter.getConverter();
		return converter;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Electricity {\n");
		sb.append("\tDate: " + timestamp + "\n");
		sb.append("\tDay: " + day + "\n");
		sb.append("\tPeriod: " + period + "\n");
		sb.append("\tAvgNSWPrice: " + avgNswPrice + "\n");
		sb.append("\tAvgNSWDemand: " + avgNswDemand + "\n");
		sb.append("\tAvgVICPrice: " + avgVicPrice + "\n");
		sb.append("\tAvgVICDemand: " + avgVicDemand + "\n");
		sb.append("\tTransfer: " + transfer + "\n");
		sb.append("\tClass: " + prediction + "\n");
		sb.append("}");
		return sb.toString();
	}
}
