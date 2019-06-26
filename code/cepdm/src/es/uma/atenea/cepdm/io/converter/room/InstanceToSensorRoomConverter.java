package es.uma.atenea.cepdm.io.converter.room;

import java.util.List;

import com.yahoo.labs.samoa.instances.InstanceImpl;

import es.uma.atenea.cepdm.event.AbstractEvent;
import es.uma.atenea.cepdm.event.room.SensorRoom;
import es.uma.atenea.cepdm.io.converter.InstanceToEventConverter;

/**
 * The converter to create sensor room events from the ARFF dataset
 * @author Aurora
 * @version 1
 * */

public class InstanceToSensorRoomConverter extends InstanceToEventConverter {

	/** A flag to indicate how to manage missing values. True for null event, false for null properties */
	public boolean nullEventMode;

	/**
	 * Empty constructor
	 * */
	public InstanceToSensorRoomConverter() {
		super();
		this.nullEventMode = false;
	}

	/**
	 * Parameterized constructor
	 * @param nullEventMode True if the whole event can be null, false if null is applied at the attribute level
	 * */
	public InstanceToSensorRoomConverter(boolean nullEventMode) {
		super();
		this.nullEventMode = nullEventMode;
	}

	@Override
	public AbstractEvent createEventFromInstance(InstanceImpl instance) {
		AbstractEvent room;

		if(Double.isNaN(instance.value(1))){

			// Option 1: The whole event is null (use when subscribers rely on "exists")
			if(this.nullEventMode) {
				room = null;
			}
			// Option 2: Each property is null (use when subscribers rely on is/is not null)
			else {
				room = new SensorRoom();
				((SensorRoom)room).setTimestamp(instance.value(0));
				((SensorRoom)room).setTemperature(null);
				((SensorRoom)room).setHumidity(null);
				((SensorRoom)room).setLight(null);
				((SensorRoom)room).setCO2(null);
				((SensorRoom)room).setHumidityRatio(null);
				List<String> values = instance.classAttribute().getAttributeValues();
				String prediction = values.get((int)instance.classValue());
				((SensorRoom)room).setOccupancy(prediction);
			}
		}
		else {
			room  = new SensorRoom();
			((SensorRoom)room).setTimestamp(instance.value(0));
			((SensorRoom)room).setTemperature(instance.value(1));
			((SensorRoom)room).setHumidity(instance.value(2));
			((SensorRoom)room).setLight(instance.value(3));
			((SensorRoom)room).setCO2(instance.value(4));
			((SensorRoom)room).setHumidityRatio(instance.value(5));

			List<String> values = instance.classAttribute().getAttributeValues();
			String prediction = values.get((int)instance.classValue());
			((SensorRoom)room).setOccupancy(prediction);
		}
		
		return room;
	}
}
