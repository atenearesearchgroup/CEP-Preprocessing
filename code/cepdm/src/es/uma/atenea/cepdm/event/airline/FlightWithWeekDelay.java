package es.uma.atenea.cepdm.event.airline;

import es.uma.atenea.cepdm.io.converter.EventToInstanceConverter;
import es.uma.atenea.cepdm.io.converter.airline.FlightWithWeekDelayToInstanceConverter;

/**
 * Derived event that represents a flight with additional
 * information about the number of delays in the same day of the week.
 * @author Aurora
 * @version 1
 * */

public class FlightWithWeekDelay extends Flight {

	/** Number of previous delays in the same day of the week */
	protected int numDelaysDayOfWeek;
	
	/**
	 * Empty constructor
	 * */
	public FlightWithWeekDelay() {
		super();
	}
	
	/**
	 * Parameterized constructor. For subclasses.
	 * */
	public FlightWithWeekDelay(Flight flight) {
		super(flight);
	}
	
	/**
	 * Parameterized constructor
	 * @param flight The complete flight information
	 * @param numDelaysAirline Number of delays in the airline
	 * */
	public FlightWithWeekDelay(Flight flight, int numDelaysDayOfWeek) {
		super(flight);
		setNumDelaysDayOfWeek(numDelaysDayOfWeek);
		
	}
	
	@Override
	protected EventToInstanceConverter createConverter() {
		return FlightWithWeekDelayToInstanceConverter.getConverter();
	}

	// Getters and setters
	
	public int getNumDelaysDayOfWeek() {
		return numDelaysDayOfWeek;
	}

	public void setNumDelaysDayOfWeek(int numDelaysDayOfWeek) {
		this.numDelaysDayOfWeek = numDelaysDayOfWeek;
	}
}
