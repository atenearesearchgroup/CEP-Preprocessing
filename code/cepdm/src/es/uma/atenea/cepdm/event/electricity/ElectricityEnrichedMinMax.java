package es.uma.atenea.cepdm.event.electricity;

import es.uma.atenea.cepdm.io.converter.EventToInstanceConverter;
import es.uma.atenea.cepdm.io.converter.electricity.ElectricityEnrichedMinMaxToInstanceConverter;

/**
 * A derived electricity event with additional information about
 * minimum and maximum price and demand.
 * @author Aurora
 * @version 1
 * */

public class ElectricityEnrichedMinMax extends Electricity {

	/** Minimum price in New South Wales (last n events) */
	protected double minNswPrice;
	
	/** Maximum price in New South Wales (last n events) */
	protected double maxNswPrice;
	
	/** Minimum demand in New South Wales (last n events) */
	protected double minNswDemand;
	
	/** Maximum demand in New South Wales (last n events) */
	protected double maxNswDemand;
	
	/** Minimum price in Victoria (last n events) */
	protected double minVicPrice;
	
	/** Maximum price in Victoria (last n events) */
	protected double maxVicPrice;
	
	/** Minimum demand in Victoria (last n events) */
	protected double minVicDemand;
	
	/** Maximum demand in Victoria (last n events) */
	protected double maxVicDemand;
	
	/**
	 * Empty constructor
	 * */
	public ElectricityEnrichedMinMax() {
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
	public ElectricityEnrichedMinMax(Electricity elecEvent, 
			double minNswPrice, double maxNswPrice, double minNswDemand, double avgMaxDemand, 
			double minVicPrice, double maxVicPrice, double minVicDemand, double maxVicDemand) {
		super(elecEvent);
		setMinNswPrice(minNswPrice);
		setMaxNswPrice(maxNswPrice);
		setMinNswDemand(minNswDemand);
		setMaxNswDemand(minNswDemand);
		setMinVicPrice(minVicPrice);
		setMaxVicPrice(maxVicPrice);
		setMinVicDemand(minVicDemand);
		setMaxVicDemand(maxVicDemand);
	}
	
	// Getters and setters
	
	public double getMinNswPrice() {
		return minNswPrice;
	}

	public void setMinNswPrice(double minNswPrice) {
		this.minNswPrice = minNswPrice;
	}

	public double getMaxNswPrice() {
		return maxNswPrice;
	}

	public void setMaxNswPrice(double maxNswPrice) {
		this.maxNswPrice = maxNswPrice;
	}

	public double getMinNswDemand() {
		return minNswDemand;
	}

	public void setMinNswDemand(double minNswDemand) {
		this.minNswDemand = minNswDemand;
	}

	public double getMaxNswDemand() {
		return maxNswDemand;
	}

	public void setMaxNswDemand(double maxNswDemand) {
		this.maxNswDemand = maxNswDemand;
	}

	public double getMinVicPrice() {
		return minVicPrice;
	}

	public void setMinVicPrice(double minVicPrice) {
		this.minVicPrice = minVicPrice;
	}

	public double getMaxVicPrice() {
		return maxVicPrice;
	}

	public void setMaxVicPrice(double maxVicPrice) {
		this.maxVicPrice = maxVicPrice;
	}

	public double getMinVicDemand() {
		return minVicDemand;
	}

	public void setMinVicDemand(double minVicDemand) {
		this.minVicDemand = minVicDemand;
	}

	public double getMaxVicDemand() {
		return maxVicDemand;
	}

	public void setMaxVicDemand(double maxVicDemand) {
		this.maxVicDemand = maxVicDemand;
	}
		
	@Override
	protected EventToInstanceConverter createConverter() {
		ElectricityEnrichedMinMaxToInstanceConverter converter = 
				ElectricityEnrichedMinMaxToInstanceConverter.getConverter();
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
		sb.append("\tMinNSWPrice: " + minNswPrice + "\n");
		sb.append("\tMaxNSWPrice: " + maxNswPrice + "\n");
		sb.append("\tMinNSWdemand: " + minNswDemand + "\n");
		sb.append("\tMaxNSWdemand: " + maxNswDemand + "\n");
		sb.append("\tVICprice: " + vicprice + "\n");
		sb.append("\tMinVICPrice: " + minVicPrice + "\n");
		sb.append("\tMaxVICPrice: " + maxVicPrice + "\n");
		sb.append("\tVICdemand: " + vicdemand + "\n");
		sb.append("\tMinVICDemand: " + minVicDemand + "\n");
		sb.append("\tMaxVICDemand: " + maxVicDemand + "\n");
		sb.append("\tTransfer: " + transfer + "\n");
		sb.append("\tClass: " + prediction + "\n");
		sb.append("}");
		return sb.toString();
	}
}
