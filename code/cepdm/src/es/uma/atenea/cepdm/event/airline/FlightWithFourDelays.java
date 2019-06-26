package es.uma.atenea.cepdm.event.airline;

import es.uma.atenea.cepdm.io.converter.EventToInstanceConverter;
import es.uma.atenea.cepdm.io.converter.airline.FlightWithFourDelaysToInstanceConverter;

/**
 * Derived event that represents a flight with additional
 * information about the number of delays (day of week, airline, airport from and airport to)
 * @author Aurora
 * @version 1
 * */

public class FlightWithFourDelays extends Flight {

	/** Number of previous delays in the same day of the week */
	protected int numDelaysDayOfWeek;
	
	/** Number of previous delays in the same airline */
	protected int numDelaysAirline;
	
	/** Number of previous delays in the same departure airport */
	protected int numDelaysAiportFrom;
	
	/** Number of previous delays in the same destination airport */
	protected int numDelaysAirportTo;
	
	/**
	 * Empty constructor
	 * */
	public FlightWithFourDelays() {
		super();
	}
	
	/**
	 * Parameterized constructor. For subclasses.
	 * */
	public FlightWithFourDelays(Flight flight) {
		super(flight);
	}
	
	/**
	 * Parameterized constructor
	 * @param flight The complete flight information
	 * @param numDelaysAirline Number of delays in the airline
	 * */
	public FlightWithFourDelays(Flight flight, int numDelaysDayOfWeek, int numDelaysAirline) {
		super(flight);
		setNumDelaysDayOfWeek(numDelaysDayOfWeek);
		setNumDelaysAirline(numDelaysAirline);
		
	}
	
	@Override
	protected EventToInstanceConverter createConverter() {
		return FlightWithFourDelaysToInstanceConverter.getConverter();
	}

	// Getters and setters
	
	public int getNumDelaysDayOfWeek() {
		return numDelaysDayOfWeek;
	}

	public void setNumDelaysDayOfWeek(int numDelaysDayOfWeek) {
		this.numDelaysDayOfWeek = numDelaysDayOfWeek;
	}
	
	public int getNumDelaysAirline() {
		return numDelaysAirline;
	}

	public void setNumDelaysAirline(int numDelaysAirline) {
		this.numDelaysAirline = numDelaysAirline;
	}
	
	public int getNumDelaysAirportFrom() {
		return numDelaysAiportFrom;
	}

	public void setNumDelaysAirportFrom(int numDelaysAiportFrom) {
		this.numDelaysAiportFrom = numDelaysAiportFrom;
	}
	
	public int getNumDelaysAirportTo() {
		return numDelaysAirportTo;
	}

	public void setNumDelaysAirportTo(int numDelaysAirportTo) {
		this.numDelaysAirportTo = numDelaysAirportTo;
	}
}
