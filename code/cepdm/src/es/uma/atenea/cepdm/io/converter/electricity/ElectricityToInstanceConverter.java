package es.uma.atenea.cepdm.io.converter.electricity;

import java.util.Arrays;
import java.util.List;

import com.yahoo.labs.samoa.instances.Attribute;

import es.uma.atenea.cepdm.event.electricity.Electricity;
import es.uma.atenea.cepdm.io.converter.EventToInstanceConverter;

/**
 * A specific converter that copies all attributes from an electricity event.
 * @author Aurora
 * @version 1
 * */

public class ElectricityToInstanceConverter extends EventToInstanceConverter{
	
	/** Singleton instance */
	private static ElectricityToInstanceConverter singleton = null;

	/**
	 * Private constructor
	 * */
	private ElectricityToInstanceConverter() {
		// do nothing
	}

	/**
	 * Public method to get an unique instance of this converter
	 * */
	public static ElectricityToInstanceConverter getConverter() {
		if(singleton == null)
			singleton = new ElectricityToInstanceConverter();
		return singleton;
	}

	@Override
	protected Attribute [] createAttributes() {
		Attribute [] attributes = new Attribute[9];
		attributes[0] = super.createNumericalAttribute("date");
		List<String> days = Arrays.asList("1","2","3","4","5","6","7");
		attributes[1] = super.createCategoricalAttribute("day", days);
		attributes[2] = super.createNumericalAttribute("period");
		attributes[3] = super.createNumericalAttribute("nswprice");
		attributes[4] = super.createNumericalAttribute("nswdemand");
		attributes[5] = super.createNumericalAttribute("vicprice");
		attributes[6] = super.createNumericalAttribute("vicdemand");
		attributes[7] = super.createNumericalAttribute("transfer");
		List<String> prediction = Arrays.asList("UP","DOWN");
		attributes[8] = super.createCategoricalAttribute("class", prediction);
		return attributes;
	}

	@Override
	public void fillInstanceWithValues() {
		Electricity electEvent = (Electricity)this.event;
		setNumericalValue(0, electEvent.getTimestamp());
		setCategoricalValue(1, electEvent.getDay());
		setNumericalValue(2, electEvent.getPeriod());
		setNumericalValue(3, electEvent.getNswprice());
		setNumericalValue(4, electEvent.getNswdemand());
		setNumericalValue(5, electEvent.getVicprice());
		setNumericalValue(6, electEvent.getVicdemand());
		setNumericalValue(7, electEvent.getTransfer());
		setCategoricalValue(8, electEvent.getPrediction());
	}

	@Override
	public int getClassAttributeIndex() {
		return 8;
	}
}
