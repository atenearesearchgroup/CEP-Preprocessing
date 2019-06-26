package es.uma.atenea.cepdm.io.converter.airline;

import java.util.Arrays;
import java.util.List;

import com.yahoo.labs.samoa.instances.Attribute;

import es.uma.atenea.cepdm.event.airline.FlightWithAirlineDelay;
import es.uma.atenea.cepdm.io.converter.EventToInstanceConverter;

/**
 * A converter for flight events with airline delay attribute
 * @author Aurora
 * @version 1
 * */
public class FlightWithAirlineDelayToInstanceConverter extends EventToInstanceConverter {

	/**
	 * Singleton instance
	 * */
	private static FlightWithAirlineDelayToInstanceConverter singleton = null;
	
	/**
	 * Private constructor
	 * */
	private FlightWithAirlineDelayToInstanceConverter() {
		//do nothing
	}
	
	/**
	 * Public method to get an unique instance of this converter
	 * */
	public static FlightWithAirlineDelayToInstanceConverter getConverter() {
		if(singleton == null)
			singleton = new FlightWithAirlineDelayToInstanceConverter();
		return singleton;
	}

	@Override
	protected Attribute[] createAttributes() {
		Attribute [] attributes = new Attribute[5];
		List<String> days = Arrays.asList("1","2","3","4","5","6","7");
		attributes[0] = super.createCategoricalAttribute("dayOfWeek", days);
		attributes[1] = super.createNumericalAttribute("time");
		attributes[2] = super.createNumericalAttribute("length");
		attributes[3] = super.createNumericalAttribute("numDelaysAirline");
		List<String> prediction = Arrays.asList("0","1");
		attributes[4] = super.createCategoricalAttribute("class", prediction);
		return attributes;
	}

	@Override
	public void fillInstanceWithValues() {
		FlightWithAirlineDelay flightEvent = (FlightWithAirlineDelay)this.event;
		setCategoricalValue(0, Integer.toString(flightEvent.getDayOfWeek()));
		setNumericalValue(1, flightEvent.getTime());
		setNumericalValue(2, flightEvent.getLength());
		setNumericalValue(3, flightEvent.getNumDelaysAirline());
		setCategoricalValue(4, Byte.toString(flightEvent.getDelay()));
	}

	@Override
	public int getClassAttributeIndex() {
		return 4;
	}
}
