package es.uma.atenea.cepdm.event.airline;

import es.uma.atenea.cepdm.event.AbstractEvent;
import es.uma.atenea.cepdm.io.converter.EventToInstanceConverter;

/**
 * Event that represents a flight of the "airlines" MOA dataset.
 * A description of the dataset can be found at https://www.openml.org/d/1169
 * @author Aurora
 * @version 1
 * */

public class Flight extends AbstractEvent {

	/** The airline identifier */
	protected String airline;
	
	/** The flight number */
	protected int flightNumber;
	
	/** The departure airport identifier */
	protected String airportFrom;
	
	/** The arrival airport identifier */
	protected String airportTo;
	
	/** The day of the week (1-7) */
	protected int dayOfWeek;
	
	/** Flight time (duration) */
	protected int time;
	
	/** Flight length (distance) */
	protected int length;
	
	/** 0=not delayed, 1=delayed */
	protected byte delay;
	
	/**
	 * Empty constructor
	 * */
	public Flight() {
		super();
	}
	
	/**
	 * Parameterized constructor. For subclasses.
	 * @param flight Other flight object
	 * */
	public Flight(Flight flight) {
		super();
		setAirline(flight.getAirline());
		setFlightNumber(flight.getFlightNumber());
		setAirportFrom(flight.getAirportFrom());
		setAirportTo(flight.getAirportTo());
		setDayOfWeek(flight.getDayOfWeek());
		setTime(flight.getTime());
		setLength(flight.getLength());
		setDelay(flight.getDelay());
	}

	@Override
	protected EventToInstanceConverter createConverter() {
		return null;
	}

	// Getters and setters
	
	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public int getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(int flight) {
		this.flightNumber = flight;
	}

	public String getAirportFrom() {
		return airportFrom;
	}

	public void setAirportFrom(String airportFrom) {
		this.airportFrom = airportFrom;
	}

	public String getAirportTo() {
		return airportTo;
	}

	public void setAirportTo(String airportTo) {
		this.airportTo = airportTo;
	}

	public int getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public byte getDelay() {
		return delay;
	}

	public void setDelay(byte delay) {
		this.delay = delay;
	}	
}
