package es.uma.atenea.cepdm.subscriber.airline;

import com.espertech.esper.client.Configuration;
import com.yahoo.labs.samoa.instances.Instance;

import es.uma.atenea.cepdm.event.airline.FlightWithFourDelays;
import es.uma.atenea.cepdm.io.ARFFDataWriter;
import es.uma.atenea.cepdm.subscriber.StatementSubscriber;

/**
 * A subscriber to create enriched flight records with the number 
 * of previous delays in the four attributes of interest 
 * (day of week, airport to, airport from, airline). 
 * The subscriber stores them in ARFF format.
 * @author Aurora
 * @version 1
 * */

public class FlightFourDelaySubscriber implements StatementSubscriber {

	/** Windows size for delay counting */
	protected int windowSize;
	
	/**
	 * Empty constructor
	 * */
	public FlightFourDelaySubscriber() {
		this.windowSize = 10;
	}
	
	/**
	 * Parameterized constructor
	 * @param windowSize Window size for statement
	 * */
	public FlightFourDelaySubscriber(int windowSize) {
		this.windowSize = windowSize;
	}

	@Override
	public String getStatement() {
		Configuration configuration = new Configuration();
		configuration.addImport("es.uma.atenea.cepdm.event.airline.*");
		String expression = 
				"select time, length, "
				+ "(select sum(delay) from Flight#length(" + this.windowSize + ") windowWeek where windowWeek.dayOfWeek=windowEvent.dayOfWeek) as delayByWeekDay, "
				+ "(select sum(delay) from Flight#length(" + this.windowSize + ") windowAirline where windowAirline.airline=windowEvent.airline) as delayByAirline, "
				+ "(select sum(delay) from Flight#length(" + this.windowSize + ") windowAirportFrom where windowAirportFrom.airportFrom=windowEvent.airportFrom) as delayByAirportFrom, "
				+ "(select sum(delay) from Flight#length(" + this.windowSize + ") windowAirportTo where windowAirportTo.airportTo=windowEvent.airportTo) as delayByAirportTo, "
				+ "delay "
				+ "from Flight#length(" + this.windowSize + ") windowEvent";
		return expression;
	}

	/**
	 * The update method (listener)
	 * @param dayOfWeek The day of week of the original instance
	 * @param time The flight duration of the original instance
	 * @param length The flight distance of the original instance
	 * @param weekDelays The number of delays of the day of week of the original instance
	 * @param airlineDelays The number of delays of the airline of the original instance
	 * @param airportFromDelays The number of delays of the departure airport of the original instance
	 * @param airportToDelays The number of delays of the arrival airport of the original instance
	 * @param delay The value of class attribute of the original instance
	 * */
	public void update(int time, int length, long weekDelays, long airlineDelays, long airportFromDelays, long airportToDelays, byte delay) {
		// Save the values in an object
		FlightWithFourDelays event = new FlightWithFourDelays();
		event.setTime(time);
		event.setLength(length);
		event.setNumDelaysDayOfWeek((int)weekDelays);
		event.setNumDelaysAirline((int)airlineDelays);
		event.setNumDelaysAirportFrom((int)airportFromDelays);
		event.setNumDelaysAirportTo((int)airportToDelays);
		event.setDelay(delay);

		Instance instance = event.toMOAInstance();
		ARFFDataWriter writer = ARFFDataWriter.getARFFDataWriter();
		if(!writer.isMetadataWriten()) {
			writer.writeMetadata(event.getConverter().getMetadataAsString());
		}
		writer.writeInstance(event.getConverter().getMetadata(), instance);
	}
}
