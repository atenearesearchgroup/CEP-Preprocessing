package es.uma.atenea.cepdm.io.converter.airline;

import java.util.Arrays;
import java.util.List;

import com.yahoo.labs.samoa.instances.Attribute;

import es.uma.atenea.cepdm.event.airline.FlightWithWeekDelay;
import es.uma.atenea.cepdm.io.converter.EventToInstanceConverter;

/**
 * A converter for flight events with attribute for day of week delays
 * day of the week
 * @author Aurora
 * @version 1
 * */
public class FlightWithWeekDelayToInstanceConverter extends EventToInstanceConverter {

	/**
	 * Singleton instance
	 * */
	private static FlightWithWeekDelayToInstanceConverter singleton = null;
	
	/**
	 * Private constructor
	 * */
	private FlightWithWeekDelayToInstanceConverter() {
		//do nothing
	}
	
	/**
	 * Public method to get an unique instance of this converter
	 * */
	public static FlightWithWeekDelayToInstanceConverter getConverter() {
		if(singleton == null)
			singleton = new FlightWithWeekDelayToInstanceConverter();
		return singleton;
	}

	@Override
	protected Attribute[] createAttributes() {
		Attribute [] attributes = new Attribute[4];
		attributes[0] = super.createNumericalAttribute("time");
		attributes[1] = super.createNumericalAttribute("length");
		attributes[2] = super.createNumericalAttribute("numDelaysWeekDay");
		List<String> prediction = Arrays.asList("0","1");
		attributes[3] = super.createCategoricalAttribute("class", prediction);
		return attributes;
	}

	@Override
	public void fillInstanceWithValues() {
		FlightWithWeekDelay flightEvent = (FlightWithWeekDelay)this.event;
		setNumericalValue(0, flightEvent.getTime());
		setNumericalValue(1, flightEvent.getLength());
		setNumericalValue(2, flightEvent.getNumDelaysDayOfWeek());
		setCategoricalValue(3, Byte.toString(flightEvent.getDelay()));
	}

	@Override
	public int getClassAttributeIndex() {
		return 3;
	}
}