package es.uma.atenea.cepdm.io.converter.electricity;

import java.util.List;

import com.yahoo.labs.samoa.instances.InstanceImpl;

import es.uma.atenea.cepdm.event.AbstractEvent;
import es.uma.atenea.cepdm.event.electricity.Electricity;
import es.uma.atenea.cepdm.io.converter.InstanceToEventConverter;

/**
 * The converter to create electricity events from the ARFF dataset
 * @author Aurora
 * @version 1
 * */

public class InstanceToElectricityConverter extends InstanceToEventConverter {
	
	/**
	 * Empty constructor
	 * */
	public InstanceToElectricityConverter() {
		super();
	}

	@Override
	public AbstractEvent createEventFromInstance(InstanceImpl instance) {
		AbstractEvent event = new Electricity();
		((Electricity)event).setTimestamp(instance.value(0));
		List<String> days = instance.attribute(1).getAttributeValues();
		((Electricity)event).setDay(days.get((int)instance.value(1)));
		((Electricity)event).setPeriod(instance.value(2));
		((Electricity)event).setNswprice(instance.value(3));
		((Electricity)event).setNswdemand(instance.value(4));
		((Electricity)event).setVicprice(instance.value(5));
		((Electricity)event).setVicdemand(instance.value(6));
		((Electricity)event).setTransfer(instance.value(7));
		List<String> values = instance.classAttribute().getAttributeValues();
		String prediction = values.get((int)instance.classValue());
		((Electricity)event).setPrediction(prediction);
		return event;
	}

}
