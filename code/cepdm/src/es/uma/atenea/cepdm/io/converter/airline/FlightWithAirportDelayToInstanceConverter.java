package es.uma.atenea.cepdm.io.converter.airline;

import java.util.Arrays;
import java.util.List;

import com.yahoo.labs.samoa.instances.Attribute;

import es.uma.atenea.cepdm.event.airline.FlightWithAirportDelay;
import es.uma.atenea.cepdm.io.converter.EventToInstanceConverter;

/**
 * A converter for flight events with airport delay attribute
 * @author Aurora
 * @version 1
 * */
public class FlightWithAirportDelayToInstanceConverter extends EventToInstanceConverter {

	/**
	 * Singleton instance
	 * */
	private static FlightWithAirportDelayToInstanceConverter singleton = null;
	
	/**
	 * Private constructor
	 * */
	private FlightWithAirportDelayToInstanceConverter() {
		//do nothing
	}
	
	/**
	 * Public method to get an unique instance of this converter
	 * */
	public static FlightWithAirportDelayToInstanceConverter getConverter() {
		if(singleton == null)
			singleton = new FlightWithAirportDelayToInstanceConverter();
		return singleton;
	}

	@Override
	protected Attribute[] createAttributes() {
		Attribute [] attributes = new Attribute[5];
		List<String> days = Arrays.asList("1","2","3","4","5","6","7");
		attributes[0] = super.createCategoricalAttribute("dayOfWeek", days);
		attributes[1] = super.createNumericalAttribute("time");
		attributes[2] = super.createNumericalAttribute("length");
		attributes[3] = super.createNumericalAttribute("numDelaysAirport");
		List<String> prediction = Arrays.asList("0","1");
		attributes[4] = super.createCategoricalAttribute("class", prediction);
		return attributes;
	}

	@Override
	public void fillInstanceWithValues() {
		FlightWithAirportDelay flightEvent = (FlightWithAirportDelay)this.event;
		setCategoricalValue(0, Integer.toString(flightEvent.getDayOfWeek()));
		setNumericalValue(1, flightEvent.getTime());
		setNumericalValue(2, flightEvent.getLength());
		setNumericalValue(3, flightEvent.getNumDelaysAirport());
		setCategoricalValue(4, Byte.toString(flightEvent.getDelay()));
	}

	@Override
	public int getClassAttributeIndex() {
		return 4;
	}
}
