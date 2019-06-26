package es.uma.atenea.cepdm.event.airline;

import es.uma.atenea.cepdm.io.converter.EventToInstanceConverter;
import es.uma.atenea.cepdm.io.converter.airline.FlightWithAirlineDelayToInstanceConverter;

/**
 * Derived event that represents a flight with additional
 * information about the number of delays of the same airline.
 * @author Aurora
 * @version 1
 * */

public class FlightWithAirlineDelay extends Flight {

	/** Number of previous delays in the same airline */
	protected int numDelaysAirline;
	
	/**
	 * Empty constructor
	 * */
	public FlightWithAirlineDelay() {
		super();
	}

	/**
	 * Parameterized constructor
	 * @param flight The complete flight information
	 * */
	public FlightWithAirlineDelay(Flight flight) {
		super(flight);
	}

	/**
	 * Parameterized constructor
	 * @param flight The complete flight information
	 * @param numDelaysAirline Number of delays in the airline
	 * */
	public FlightWithAirlineDelay(Flight flight, int numDelaysAirline) {
		super(flight);
		setNumDelaysAirline(numDelaysAirline);
	}
	
	@Override
	protected EventToInstanceConverter createConverter() {
		return FlightWithAirlineDelayToInstanceConverter.getConverter();
	}
	
	// Getters and setters
	
	public int getNumDelaysAirline() {
		return numDelaysAirline;
	}

	public void setNumDelaysAirline(int numDelaysAirline) {
		this.numDelaysAirline = numDelaysAirline;
	}
}
