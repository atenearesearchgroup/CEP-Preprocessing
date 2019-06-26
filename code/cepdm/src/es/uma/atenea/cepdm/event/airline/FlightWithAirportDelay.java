package es.uma.atenea.cepdm.event.airline;

import es.uma.atenea.cepdm.io.converter.EventToInstanceConverter;
import es.uma.atenea.cepdm.io.converter.airline.FlightWithAirportDelayToInstanceConverter;

/**
 * Derived event that represents a flight with additional
 * information about the number of delays of the same departure airport.
 * @author Aurora
 * @version 1
 * */

public class FlightWithAirportDelay extends Flight {

	/** Number of previous delays in the same airport */
	protected int numDelaysAirport;
	
	/**
	 * Empty constructor
	 * */
	public FlightWithAirportDelay() {
		super();
	}

	/**
	 * Parameterized constructor
	 * @param flight The complete flight information
	 * */
	public FlightWithAirportDelay(Flight flight) {
		super(flight);
	}

	/**
	 * Parameterized constructor
	 * @param flight The complete flight information
	 * @param numDelaysAirport Number of delays in the airline
	 * */
	public FlightWithAirportDelay(Flight flight, int numDelaysAirport) {
		super(flight);
		setNumDelaysAirport(numDelaysAirport);
	}
	
	@Override
	protected EventToInstanceConverter createConverter() {
		return FlightWithAirportDelayToInstanceConverter.getConverter();
	}
	
	// Getters and setters
	
	public int getNumDelaysAirport() {
		return numDelaysAirport;
	}

	public void setNumDelaysAirport(int numDelaysAirport) {
		this.numDelaysAirport = numDelaysAirport;
	}
}
