package es.uma.atenea.cepdm.io.converter.airline;

import java.util.Arrays;
import java.util.List;

import com.yahoo.labs.samoa.instances.Attribute;

import es.uma.atenea.cepdm.event.airline.FlightWithFourDelays;
import es.uma.atenea.cepdm.io.converter.EventToInstanceConverter;

/**
 * A converter for flight events with attributes for delays related to all attributes
 * (airport from, airport to, airline, day of week)
 * @author Aurora
 * @version 1
 * */
public class FlightWithFourDelaysToInstanceConverter extends EventToInstanceConverter {

	/**
	 * Singleton instance
	 * */
	private static FlightWithFourDelaysToInstanceConverter singleton = null;
	
	/**
	 * Private constructor
	 * */
	private FlightWithFourDelaysToInstanceConverter() {
		//do nothing
	}
	
	/**
	 * Public method to get an unique instance of this converter
	 * */
	public static FlightWithFourDelaysToInstanceConverter getConverter() {
		if(singleton == null)
			singleton = new FlightWithFourDelaysToInstanceConverter();
		return singleton;
	}

	@Override
	protected Attribute[] createAttributes() {
		Attribute [] attributes = new Attribute[7];
		attributes[0] = super.createNumericalAttribute("time");
		attributes[1] = super.createNumericalAttribute("length");
		attributes[2] = super.createNumericalAttribute("numDelaysAirline");
		attributes[3] = super.createNumericalAttribute("numDelaysWeekDay");
		attributes[4] = super.createNumericalAttribute("numDelaysAiportFrom");
		attributes[5] = super.createNumericalAttribute("numDelaysAirportTo");
		List<String> prediction = Arrays.asList("0","1");
		attributes[6] = super.createCategoricalAttribute("class", prediction);
		return attributes;
	}

	@Override
	public void fillInstanceWithValues() {
		FlightWithFourDelays flightEvent = (FlightWithFourDelays)this.event;
		setNumericalValue(0, flightEvent.getTime());
		setNumericalValue(1, flightEvent.getLength());
		setNumericalValue(2, flightEvent.getNumDelaysAirline());
		setNumericalValue(3, flightEvent.getNumDelaysDayOfWeek());
		setNumericalValue(4, flightEvent.getNumDelaysAirportFrom());
		setNumericalValue(5, flightEvent.getNumDelaysAirportTo());
		setCategoricalValue(6, Byte.toString(flightEvent.getDelay()));
	}

	@Override
	public int getClassAttributeIndex() {
		return 6;
	}
}