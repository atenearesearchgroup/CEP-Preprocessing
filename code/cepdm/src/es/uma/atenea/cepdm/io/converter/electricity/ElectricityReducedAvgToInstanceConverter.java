package es.uma.atenea.cepdm.io.converter.electricity;

import java.util.Arrays;
import java.util.List;

import com.yahoo.labs.samoa.instances.Attribute;

import es.uma.atenea.cepdm.event.electricity.ElectricityEnrichedAvg;
import es.uma.atenea.cepdm.io.converter.EventToInstanceConverter;

/**
 * A converter that creates instances from electricity events where absolute
 * price and demand are replaced by average values.
 * @author Aurora
 * @version 1
 * */

public class ElectricityReducedAvgToInstanceConverter extends EventToInstanceConverter {

	/**
	 * Singleton instance
	 * */
	private static ElectricityReducedAvgToInstanceConverter singleton = null;
	
	/**
	 * Private constructor
	 * */
	private ElectricityReducedAvgToInstanceConverter() {
		// do nothing
	}

	/**
	 * Public method to get an unique instance of this converter
	 * */
	public static ElectricityReducedAvgToInstanceConverter getConverter() {
		if(singleton == null)
			singleton = new ElectricityReducedAvgToInstanceConverter();
		return singleton;
	}
	
	@Override
	protected Attribute[] createAttributes() {
		Attribute [] attributes = new Attribute[9];
		attributes[0] = super.createNumericalAttribute("date");
		List<String> days = Arrays.asList("1","2","3","4","5","6","7");
		attributes[1] = super.createCategoricalAttribute("day", days);
		attributes[2] = super.createNumericalAttribute("period");
		attributes[3] = super.createNumericalAttribute("avgnswprice");
		attributes[4] = super.createNumericalAttribute("avgnswdemand");
		attributes[5] = super.createNumericalAttribute("avgvicprice");
		attributes[6] = super.createNumericalAttribute("avgvicdemand");
		attributes[7] = super.createNumericalAttribute("transfer");
		List<String> prediction = Arrays.asList("UP","DOWN");
		attributes[8] = super.createCategoricalAttribute("class", prediction);
		return attributes;
	}

	@Override
	public void fillInstanceWithValues() {
		ElectricityEnrichedAvg electEvent = (ElectricityEnrichedAvg)this.event;
		setNumericalValue(0, electEvent.getTimestamp());
		setCategoricalValue(1, electEvent.getDay());
		setNumericalValue(2, electEvent.getPeriod());
		setNumericalValue(3, electEvent.getAvgNswPrice());
		setNumericalValue(4, electEvent.getAvgNswDemand());
		setNumericalValue(5, electEvent.getAvgVicPrice());
		setNumericalValue(6, electEvent.getAvgVicDemand());
		setNumericalValue(7, electEvent.getTransfer());
		setCategoricalValue(8, electEvent.getPrediction());
	}

	@Override
	public int getClassAttributeIndex() {
		return 8;
	}
}
