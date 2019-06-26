package es.uma.atenea.cepdm.io.converter.electricity;

import java.util.Arrays;
import java.util.List;

import com.yahoo.labs.samoa.instances.Attribute;

import es.uma.atenea.cepdm.event.electricity.ElectricityEnrichedAvg;
import es.uma.atenea.cepdm.io.converter.EventToInstanceConverter;

/**
 * A converter that creates instances from electricity events enriched with average
 * price and demand.
 * @author Aurora
 * @version 1
 * */

public class ElectricityEnrichedAvgToInstanceConverter extends EventToInstanceConverter {

	/**
	 * Singleton instance
	 * */
	private static ElectricityEnrichedAvgToInstanceConverter singleton = null;
	
	/**
	 * Private constructor
	 * */
	private ElectricityEnrichedAvgToInstanceConverter() {
		// do nothing
	}

	/**
	 * Public method to get an unique instance of this converter
	 * */
	public static ElectricityEnrichedAvgToInstanceConverter getConverter() {
		if(singleton == null)
			singleton = new ElectricityEnrichedAvgToInstanceConverter();
		return singleton;
	}
	
	@Override
	protected Attribute[] createAttributes() {
		Attribute [] attributes = new Attribute[13];
		attributes[0] = super.createNumericalAttribute("date");
		List<String> days = Arrays.asList("1","2","3","4","5","6","7");
		attributes[1] = super.createCategoricalAttribute("day", days);
		attributes[2] = super.createNumericalAttribute("period");
		attributes[3] = super.createNumericalAttribute("nswprice");
		attributes[4] = super.createNumericalAttribute("avgnswprice");
		attributes[5] = super.createNumericalAttribute("nswdemand");
		attributes[6] = super.createNumericalAttribute("avgnswdemand");
		attributes[7] = super.createNumericalAttribute("vicprice");
		attributes[8] = super.createNumericalAttribute("avgvicprice");
		attributes[9] = super.createNumericalAttribute("vicdemand");
		attributes[10] = super.createNumericalAttribute("avgvicdemand");
		attributes[11] = super.createNumericalAttribute("transfer");
		List<String> prediction = Arrays.asList("UP","DOWN");
		attributes[12] = super.createCategoricalAttribute("class", prediction);
		return attributes;
	}

	@Override
	public void fillInstanceWithValues() {
		ElectricityEnrichedAvg electEvent = (ElectricityEnrichedAvg)this.event;
		setNumericalValue(0, electEvent.getTimestamp());
		setCategoricalValue(1, electEvent.getDay());
		setNumericalValue(2, electEvent.getPeriod());
		setNumericalValue(3, electEvent.getNswprice());
		setNumericalValue(4, electEvent.getAvgNswPrice());
		setNumericalValue(5, electEvent.getNswdemand());
		setNumericalValue(6, electEvent.getAvgNswDemand());
		setNumericalValue(7, electEvent.getVicprice());
		setNumericalValue(8, electEvent.getAvgVicPrice());
		setNumericalValue(9, electEvent.getVicdemand());
		setNumericalValue(10, electEvent.getAvgVicDemand());
		setNumericalValue(11, electEvent.getTransfer());
		setCategoricalValue(12, electEvent.getPrediction());
	}

	@Override
	public int getClassAttributeIndex() {
		return 12;
	}
}
