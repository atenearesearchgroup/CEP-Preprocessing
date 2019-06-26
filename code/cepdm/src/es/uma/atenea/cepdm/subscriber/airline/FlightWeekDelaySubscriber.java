package es.uma.atenea.cepdm.subscriber.airline;

import com.espertech.esper.client.Configuration;
import com.yahoo.labs.samoa.instances.Instance;

import es.uma.atenea.cepdm.event.airline.FlightWithWeekDelay;
import es.uma.atenea.cepdm.io.ARFFDataWriter;
import es.uma.atenea.cepdm.subscriber.StatementSubscriber;

/**
 * A subscriber to create enriched flight records with the number 
 * of previous delays in the same day of week, and store them in ARFF format
 * @author Aurora
 * @version 1
 * */
public class FlightWeekDelaySubscriber implements StatementSubscriber {

	/** Windows size for delay counting */
	protected int windowSize;
	
	/**
	 * Empty constructor
	 * */
	public FlightWeekDelaySubscriber() {
		this.windowSize = 10;
	}
	
	/**
	 * Parameterized constructor
	 * @param windowSize Window size for statement
	 * */
	public FlightWeekDelaySubscriber(int windowSize) {
		this.windowSize = windowSize;
	}

	@Override
	public String getStatement() {
		Configuration configuration = new Configuration();
		configuration.addImport("es.uma.atenea.cepdm.event.airline.*");
		String expression = 
				"select time, length, sum(delay), delay "
				+ "from Flight#length(" + this.windowSize + ") " 
				+ "group by dayOfWeek";
		return expression;
	}

	/**
	 * The update method (listener)
	 * @param dayOfWeek The day of week of the original instance
	 * @param time The flight duration of the original instance
	 * @param length The flight distance of the original instance
	 * @param weekDelays The number of delays in the day of the week of the original instance
	 * @param delay The value of class attribute of the original instance
	 * */
	public void update(int time, int length, long weekDelays, byte delay) {
		// Save the values in an object
		FlightWithWeekDelay event = new FlightWithWeekDelay();
		event.setTime(time);
		event.setLength(length);
		event.setNumDelaysDayOfWeek((int)weekDelays);
		event.setDelay(delay);
		Instance instance = event.toMOAInstance();
		ARFFDataWriter writer = ARFFDataWriter.getARFFDataWriter();
		if(!writer.isMetadataWriten()) {
			writer.writeMetadata(event.getConverter().getMetadataAsString());
		}
		writer.writeInstance(event.getConverter().getMetadata(), instance);
	}
}
