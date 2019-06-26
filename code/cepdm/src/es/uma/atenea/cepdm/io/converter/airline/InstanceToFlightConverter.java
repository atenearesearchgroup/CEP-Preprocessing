package es.uma.atenea.cepdm.io.converter.airline;

import java.util.List;

import com.yahoo.labs.samoa.instances.InstanceImpl;

import es.uma.atenea.cepdm.event.AbstractEvent;
import es.uma.atenea.cepdm.event.airline.Flight;
import es.uma.atenea.cepdm.io.converter.InstanceToEventConverter;

/**
 * The converter to create flight events from the ARFF dataset
 * @author Aurora
 * @version 1
 * */

public class InstanceToFlightConverter extends InstanceToEventConverter {

	/**
	 * Empty constructor
	 * */
	public InstanceToFlightConverter() {
		super();
	}

	@Override
	public AbstractEvent createEventFromInstance(InstanceImpl instance) {
		AbstractEvent event = new Flight();
		List<String> listOfValues = instance.attribute(0).getAttributeValues();
		((Flight)event).setAirline(listOfValues.get((int)instance.value(0)));
		((Flight)event).setFlightNumber((int)instance.value(1));
		listOfValues = instance.attribute(2).getAttributeValues();
		((Flight)event).setAirportFrom(listOfValues.get((int)instance.value(2)));
		listOfValues = instance.attribute(3).getAttributeValues();
		((Flight)event).setAirportTo(listOfValues.get((int)instance.value(3)));
		((Flight)event).setDayOfWeek((int)instance.value(4)+1); // The ARFF reader loads the index, not the value
		((Flight)event).setTime((int)instance.value(5));
		((Flight)event).setLength((int)instance.value(6));
		((Flight)event).setDelay((byte)instance.value(7));
		return event;
	}
}
