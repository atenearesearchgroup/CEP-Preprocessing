package es.uma.atenea.cepdm.io.converter.room;

import java.util.Arrays;
import java.util.List;

import com.yahoo.labs.samoa.instances.Attribute;

import es.uma.atenea.cepdm.event.room.SensorRoom;
import es.uma.atenea.cepdm.io.converter.EventToInstanceConverter;

/**
 * A converter that copies all attributes from an sensor room event.
 * @author Aurora
 * @version 1
 * */

public class SensorRoomToInstanceConverter extends EventToInstanceConverter {

	/** Singleton instance */
	private static SensorRoomToInstanceConverter singleton = null;
	
	/**
	 * Private constructor
	 * */
	private SensorRoomToInstanceConverter() {
		// do nothing
	}

	/**
	 * Public method to get an unique instance of this converter
	 * */
	public static SensorRoomToInstanceConverter getConverter() {
		if(singleton == null)
			singleton = new SensorRoomToInstanceConverter();
		return singleton;
	}
	
	@Override
	protected Attribute[] createAttributes() {
		Attribute [] attributes = new Attribute[6];
		attributes[0] = super.createNumericalAttribute("temperature");
		attributes[1] = super.createNumericalAttribute("humidity");
		attributes[2] = super.createNumericalAttribute("light");
		attributes[3] = super.createNumericalAttribute("CO2");
		attributes[4] = super.createNumericalAttribute("humidityRatio");
		List<String> prediction = Arrays.asList("no","yes");
		attributes[5] = super.createCategoricalAttribute("class",prediction);
		return attributes;
	}

	@Override
	public void fillInstanceWithValues() {
		SensorRoom room = (SensorRoom)this.event;
		setNumericalValue(0, room.getTemperature());
		setNumericalValue(1, room.getHumidity());
		setNumericalValue(2, room.getLight());
		setNumericalValue(3, room.getCO2());
		setNumericalValue(4, room.getHumidityRatio());
		setCategoricalValue(5, room.getOccupancy());

	}

	@Override
	public int getClassAttributeIndex() {
		return 5;
	}
}
