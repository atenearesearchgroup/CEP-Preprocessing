package es.uma.atenea.cepdm.event.electricity;

import es.uma.atenea.cepdm.io.converter.EventToInstanceConverter;
import es.uma.atenea.cepdm.io.converter.electricity.ElectricityEnrichedAvgToInstanceConverter;

/**
 * A derived electricity event with additional information about
 * average price and demand.
 * @author Aurora
 * @version 1
 * */

public class ElectricityEnrichedAvg extends Electricity {

	/** Average price in New South Wales (last n events) */
	protected double avgNswPrice;
	
	/** Average demand in New South Wales (last n events) */
	protected double avgNswDemand;
	
	/** Average price in Victoria (last n events) */
	protected double avgVicPrice;
	
	/** Average demand in Victoria (last n events) */
	protected double avgVicDemand;
	
	/**
	 * Empty constructor
	 * */
	public ElectricityEnrichedAvg() {
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
	public ElectricityEnrichedAvg(Electricity elecEvent, double avgNswPrice, double avgNswDemand, double avgVicPrice, double avgVicDemand) {
		super(elecEvent);
		setAvgNswPrice(avgNswPrice);
		setAvgNswDemand(avgNswDemand);
		setAvgVicPrice(avgVicPrice);
		setAvgVicDemand(avgVicDemand);
	}
	
	// Getters and setters
	
	public double getAvgNswPrice() {
		return avgNswPrice;
	}

	public void setAvgNswPrice(double avgNswPrice) {
		this.avgNswPrice = avgNswPrice;
	}

	public double getAvgNswDemand() {
		return avgNswDemand;
	}

	public void setAvgNswDemand(double avgNswDemand) {
		this.avgNswDemand = avgNswDemand;
	}

	public double getAvgVicPrice() {
		return avgVicPrice;
	}

	public void setAvgVicPrice(double avgVicPrice) {
		this.avgVicPrice = avgVicPrice;
	}

	public double getAvgVicDemand() {
		return avgVicDemand;
	}

	public void setAvgVicDemand(double avgVicDemand) {
		this.avgVicDemand = avgVicDemand;
	}
	
	@Override
	protected EventToInstanceConverter createConverter() {
		ElectricityEnrichedAvgToInstanceConverter converter = 
				ElectricityEnrichedAvgToInstanceConverter.getConverter();
		return converter;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Electricity {\n");
		sb.append("\tDate: " + timestamp + "\n");
		sb.append("\tDay: " + day + "\n");
		sb.append("\tPeriod: " + period + "\n");
		sb.append("\tNSWprice: " + nswprice + "\n");
		sb.append("\tAvgNSWPrice: " + avgNswPrice + "\n");
		sb.append("\tNSWdemand: " + nswdemand + "\n");
		sb.append("\tAvgNSWDemand: " + avgNswDemand + "\n");
		sb.append("\tVICprice: " + vicprice + "\n");
		sb.append("\tAvgVICPrice: " + avgVicPrice + "\n");
		sb.append("\tVICdemand: " + vicdemand + "\n");
		sb.append("\tAvgVICDemand: " + avgVicDemand + "\n");
		sb.append("\tTransfer: " + transfer + "\n");
		sb.append("\tClass: " + prediction + "\n");
		sb.append("}");
		return sb.toString();
	}
}
