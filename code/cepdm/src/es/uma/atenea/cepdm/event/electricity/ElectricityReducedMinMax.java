package es.uma.atenea.cepdm.event.electricity;

import es.uma.atenea.cepdm.io.converter.EventToInstanceConverter;
import es.uma.atenea.cepdm.io.converter.electricity.ElectricityReducedMinMaxToInstanceConverter;

/**
 * A derived electricity event with additional information about
 * minimum and maximum price and demand. Original attributes are excluded.
 * @author Aurora
 * @version 1
 * */

public class ElectricityReducedMinMax extends ElectricityEnrichedMinMax {
	
	/**
	 * Empty constructor
	 * */
	public ElectricityReducedMinMax() {
		super();
	}
	
	/**
	 * Parameterized constructor
	 * @param elecEvent An instance of the superclass
	 * @param minNswPrice Minimum NSW price
	 * @param maxNswPrice Maximum NSW price
	 * @param minNswDemand Minimum NSW demand
	 * @param maxNswDemand Maximum NSW demand
	 * @param minVicPrice Minimum VIC price
	 * @param maxVicPrice Maximum VIC price
	 * @param minVicDemand Minimum VIC demand
	 * @param maxVicDemand Maximum VIC demand
	 * */
	public ElectricityReducedMinMax(Electricity elecEvent, 
			double minNswPrice, double maxNswPrice, double minNswDemand, double avgMaxDemand, 
			double minVicPrice, double maxVicPrice, double minVicDemand, double maxVicDemand) {
		super(elecEvent,minNswPrice,maxNswPrice,minNswDemand,minNswDemand,minVicPrice,maxVicPrice,minVicDemand,maxVicDemand);
	}
	
	@Override
	protected EventToInstanceConverter createConverter() {
		ElectricityReducedMinMaxToInstanceConverter converter = 
				ElectricityReducedMinMaxToInstanceConverter.getConverter();
		return converter;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Electricity {\n");
		sb.append("\tDate: " + timestamp + "\n");
		sb.append("\tDay: " + day + "\n");
		sb.append("\tPeriod: " + period + "\n");
		sb.append("\tMinNSWPrice: " + minNswPrice + "\n");
		sb.append("\tMaxNSWPrice: " + maxNswPrice + "\n");
		sb.append("\tMinNSWdemand: " + minNswDemand + "\n");
		sb.append("\tMaxNSWdemand: " + maxNswDemand + "\n");
		sb.append("\tMinVICPrice: " + minVicPrice + "\n");
		sb.append("\tMaxVICPrice: " + maxVicPrice + "\n");
		sb.append("\tMinVICDemand: " + minVicDemand + "\n");
		sb.append("\tMaxVICDemand: " + maxVicDemand + "\n");
		sb.append("\tTransfer: " + transfer + "\n");
		sb.append("\tClass: " + prediction + "\n");
		sb.append("}");
		return sb.toString();
	}
}
