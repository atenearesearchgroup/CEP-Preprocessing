package es.uma.atenea.cepdm.event.room;

import java.util.Calendar;
import java.util.GregorianCalendar;

import es.uma.atenea.cepdm.event.AbstractEvent;
import es.uma.atenea.cepdm.io.converter.EventToInstanceConverter;
import es.uma.atenea.cepdm.io.converter.room.SensorRoomToInstanceConverter;

/**
 * An event to represent sensor and occupancy data. The events are equivalent
 * to the instances of the UCI dataset available from https://archive.ics.uci.edu/ml/datasets/Occupancy+Detection+
 * @author Aurora
 * @version 1
 * */

public class SensorRoom extends AbstractEvent {

	/** Timestamp */
	protected Double timestamp;

	/** Temperature */
	protected Double temperature;

	/** Humidity */
	protected Double humidity;

	/** Light */
	protected Double light;

	/** CO2 */
	protected Double co2;

	/** Humity ratio */
	protected Double humidityRatio;

	/** Class attribute (yes/no) */
	protected String occupancy;

	/**
	 * Default constructor
	 * */
	public SensorRoom() {
		super();
	}

	/**
	 * Copy constructor
	 * @param other Other sensor object
	 * */
	public SensorRoom(SensorRoom other) {
		super();
		copyFrom(other);
	}

	@Override
	protected EventToInstanceConverter createConverter() {
		return SensorRoomToInstanceConverter.getConverter();
	}

	/**
	 * A method to copy the event information
	 * @param other Other event
	 * */
	protected void copyFrom(SensorRoom other) {
		this.timestamp = other.timestamp;
		this.temperature = other.temperature;
		this.humidity = other.humidity;
		this.light = other.light;
		this.co2 = other.co2;
		this.humidityRatio = other.humidityRatio;
		this.occupancy = other.occupancy;		
	}

	// Getters and setters
	
	public Double getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Double timestamp) {
		this.timestamp = timestamp;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public Double getHumidity() {
		return humidity;
	}

	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}

	public Double getLight() {
		return light;
	}

	public void setLight(Double light) {
		this.light = light;
	}

	public Double getCO2() {
		return co2;
	}

	public void setCO2(Double co2) {
		this.co2 = co2;
	}

	public Double getHumidityRatio() {
		return humidityRatio;
	}

	public void setHumidityRatio(Double humidityRatio) {
		this.humidityRatio = humidityRatio;
	}

	public String getOccupancy() {
		return occupancy;
	}

	public void setOccupancy(String occupancy) {
		this.occupancy = occupancy;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("SensorRoom {\n");
		if(timestamp != null) {
			Calendar date = new GregorianCalendar();
			date.setTimeInMillis((long)timestamp.doubleValue());
			sb.append("\tTimestamp: " + date.getTime().toString() + "\n");
		}
		sb.append("\tTemperature: " + temperature + "\n");
		sb.append("\tHumidity: " + humidity + "\n");
		sb.append("\tLight: " + light + "\n");
		sb.append("\tCO2: " + co2 + "\n");
		sb.append("\tHumidityRatio: " + humidityRatio + "\n");
		sb.append("\tOccupancy: " + occupancy + "\n");
		sb.append("}");
		return sb.toString();
	}
}
