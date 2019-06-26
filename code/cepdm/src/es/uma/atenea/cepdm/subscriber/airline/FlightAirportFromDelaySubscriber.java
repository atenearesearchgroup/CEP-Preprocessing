package es.uma.atenea.cepdm.subscriber.airline;

import com.espertech.esper.client.Configuration;
import com.yahoo.labs.samoa.instances.Instance;

import es.uma.atenea.cepdm.event.airline.FlightWithAirportDelay;
import es.uma.atenea.cepdm.io.ARFFDataWriter;
import es.uma.atenea.cepdm.subscriber.StatementSubscriber;

/**
 * A subscriber to create enriched flight records with the number 
 * of previous delays in the departure airport, and store them in ARFF format
 * @author Aurora
 * @version 1
 * */
public class FlightAirportFromDelaySubscriber implements StatementSubscriber {

	/** Windows size for delay counting */
	protected int windowSize;
	
	/**
	 * Empty constructor
	 * */
	public FlightAirportFromDelaySubscriber() {
		this.windowSize = 10;
	}
	
	/**
	 * Parameterized constructor
	 * @param windowSize Window size for statement
	 * */
	public FlightAirportFromDelaySubscriber(int windowSize) {
		this.windowSize = windowSize;
	}

	@Override
	public String getStatement() {
		Configuration configuration = new Configuration();
		configuration.addImport("es.uma.atenea.cepdm.event.airline.*");
		String expression = 
				"select dayOfWeek, time, length, sum(delay), delay "
				+ "from Flight#length(" + this.windowSize + ") " 
				+ "group by airportFrom";
		return expression;
	}

	/**
	 * The update method (listener)
	 * @param dayOfWeek The day of week of the original instance
	 * @param time The flight duration of the original instance
	 * @param length The flight distance of the original instance
	 * @param numDelaysAirport The number of delays of the departure airport of the original instance
	 * @param delay The value of class attribute of the original instance
	 * */
	public void update(int dayOfWeek, int time, int length, long numDelaysAirport, byte delay) {
		
		// Save the values in an object
		FlightWithAirportDelay event = new FlightWithAirportDelay();
		event.setDayOfWeek(dayOfWeek);
		event.setTime(time);
		event.setLength(length);
		event.setNumDelaysAirport((int)numDelaysAirport);
		event.setDelay(delay);
		Instance instance = event.toMOAInstance();
		ARFFDataWriter writer = ARFFDataWriter.getARFFDataWriter();
		if(!writer.isMetadataWriten()) {
			writer.writeMetadata(event.getConverter().getMetadataAsString());
		}
		writer.writeInstance(event.getConverter().getMetadata(), instance);
	}
}
