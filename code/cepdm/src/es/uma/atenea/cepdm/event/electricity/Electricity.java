package es.uma.atenea.cepdm.event.electricity;

import es.uma.atenea.cepdm.event.AbstractEvent;
import es.uma.atenea.cepdm.io.converter.EventToInstanceConverter;
import es.uma.atenea.cepdm.io.converter.electricity.ElectricityToInstanceConverter;

/**
 * Event that represents the electricity records of the "electNormNew" MOA dataset.
 * A description of the dataset can be found at https://www.openml.org/d/151
 * @author Aurora
 * @version 1
 * */

public class Electricity extends AbstractEvent{

	/** Date (normalized from 0 to 1 -- May 1996 to December 1998) */
	protected double timestamp;
	
	/** Day in the week (1-7) */
	protected String day;
	
	/** Time of measurement (1-48) in intervals of 30 min */
	protected double period;
	
	/** Electricity price in New South Wales */
	protected double nswprice;
	
	/** Electricity demand in New South Wales */
	protected double nswdemand;
	
	/** Electricity price in Victoria */
	protected double vicprice;
	
	/** Electricity demand in Victoria */
	protected double vicdemand;
	
	/** Scheduled electricity transfer between states */
	protected double transfer;
	
	/** Class label (up/down) */
	protected String prediction;
	
	/**
	 * Empty constructor
	 * */
	public Electricity() {
		super();
	}

	/**
	 * Constructor from other object.
	 * @param elecEvent Other electricity event
	 * */
	public Electricity(Electricity elecEvent) {
		super();
		setTimestamp(elecEvent.getTimestamp());
		setDay(elecEvent.getDay());
		setPeriod(elecEvent.getPeriod());
		setNswprice(elecEvent.getNswprice());
		setNswdemand(elecEvent.getNswdemand());
		setVicprice(elecEvent.getVicprice());
		setVicdemand(elecEvent.getVicdemand());
		setTransfer(elecEvent.getTransfer());
		setPrediction(elecEvent.getPrediction());
	}
	
	// Getters and setters
	
	public double getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(double timestamp) {
		this.timestamp = timestamp;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public double getPeriod() {
		return period;
	}

	public void setPeriod(double period) {
		this.period = period;
	}

	public double getNswprice() {
		return nswprice;
	}

	public void setNswprice(double nswprice) {
		this.nswprice = nswprice;
	}

	public double getNswdemand() {
		return nswdemand;
	}

	public void setNswdemand(double nswdemand) {
		this.nswdemand = nswdemand;
	}

	public double getVicprice() {
		return vicprice;
	}

	public void setVicprice(double vicprice) {
		this.vicprice = vicprice;
	}

	public double getVicdemand() {
		return vicdemand;
	}

	public void setVicdemand(double vicdemand) {
		this.vicdemand = vicdemand;
	}

	public double getTransfer() {
		return transfer;
	}

	public void setTransfer(double transfer) {
		this.transfer = transfer;
	}

	public String getPrediction() {
		return prediction;
	}

	public void setPrediction(String prediction) {
		this.prediction = prediction;
	}

	@Override
	protected EventToInstanceConverter createConverter() {
		ElectricityToInstanceConverter converter = ElectricityToInstanceConverter.getConverter();
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
		sb.append("\tNSWdemand: " + nswdemand + "\n");
		sb.append("\tVICprice: " + vicprice + "\n");
		sb.append("\tVICdemand: " + vicdemand + "\n");
		sb.append("\tTransfer: " + transfer + "\n");
		sb.append("\tClass: " + prediction + "\n");
		sb.append("}");
		return sb.toString();
	}
}
