package es.uma.atenea.cepdm.subscriber.airline;

import com.espertech.esper.client.Configuration;

/**
 * A subscriber to create enriched flight records with the number 
 * of previous airline delays, and store them in ARFF format
 * @author Aurora
 * @version 1
 * */
public class FlightAirportToDelaySubscriber extends FlightAirportFromDelaySubscriber {

	/**
	 * Empty constructor
	 * */
	public FlightAirportToDelaySubscriber() {
		super();
	}
	
	/**
	 * Parameterized constructor
	 * @param windowSize Window size for statement
	 * */
	public FlightAirportToDelaySubscriber(int windowSize) {
		super(windowSize);
	}

	@Override
	public String getStatement() {
		Configuration configuration = new Configuration();
		configuration.addImport("es.uma.atenea.cepdm.event.airline.*");
		String expression = 
				"select dayOfWeek, time, length, sum(delay), delay "
				+ "from Flight#length(" + this.windowSize + ") " 
				+ "group by airportTo";
		return expression;
	}
}
