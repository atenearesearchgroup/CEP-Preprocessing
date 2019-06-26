package es.uma.atenea.cepdm.io.converter.electricity;

import java.util.Arrays;
import java.util.List;

import com.yahoo.labs.samoa.instances.Attribute;

import es.uma.atenea.cepdm.event.electricity.ElectricityEnrichedMinMax;
import es.uma.atenea.cepdm.io.converter.EventToInstanceConverter;

/**
 * A converter that creates instances from electricity events where absolute
 * price and demand are replaced by minimum and maximum values.
 * @author Aurora
 * @version 1
 * */

public class ElectricityReducedMinMaxToInstanceConverter extends EventToInstanceConverter {

	/**
	 * Singleton instance
	 * */
	private static ElectricityReducedMinMaxToInstanceConverter singleton = null;
	
	/**
	 * Private constructor
	 * */
	public ElectricityReducedMinMaxToInstanceConverter() {
		// do nothing
	}

	/**
	 * Public method to get an unique instance of this converter
	 * */
	public static ElectricityReducedMinMaxToInstanceConverter getConverter() {
		if(singleton == null)
			singleton = new ElectricityReducedMinMaxToInstanceConverter();
		return singleton;
	}
	
	@Override
	protected Attribute[] createAttributes() {
		Attribute [] attributes = new Attribute[13];
		attributes[0] = super.createNumericalAttribute("date");
		List<String> days = Arrays.asList("1","2","3","4","5","6","7");
		attributes[1] = super.createCategoricalAttribute("day", days);
		attributes[2] = super.createNumericalAttribute("period");
		attributes[3] = super.createNumericalAttribute("minnswprice");
		attributes[4] = super.createNumericalAttribute("maxnswprice");
		attributes[5] = super.createNumericalAttribute("minnswdemand");
		attributes[6] = super.createNumericalAttribute("maxnswdemand");
		attributes[7] = super.createNumericalAttribute("minvicprice");
		attributes[8] = super.createNumericalAttribute("maxvicprice");
		attributes[9] = super.createNumericalAttribute("minvicdemand");
		attributes[10] = super.createNumericalAttribute("maxvicdemand");
		attributes[11] = super.createNumericalAttribute("transfer");
		List<String> prediction = Arrays.asList("UP","DOWN");
		attributes[12] = super.createCategoricalAttribute("class", prediction);
		return attributes;
	}

	@Override
	public void fillInstanceWithValues() {
		ElectricityEnrichedMinMax electEvent = (ElectricityEnrichedMinMax)this.event;
		setNumericalValue(0, electEvent.getTimestamp());
		setCategoricalValue(1, electEvent.getDay());
		setNumericalValue(2, electEvent.getPeriod());		
		setNumericalValue(3, electEvent.getMinNswPrice());
		setNumericalValue(4, electEvent.getMaxNswPrice());		
		setNumericalValue(5, electEvent.getMinNswDemand());
		setNumericalValue(6, electEvent.getMaxNswDemand());		
		setNumericalValue(7, electEvent.getMinVicPrice());
		setNumericalValue(8, electEvent.getMaxVicPrice());		
		setNumericalValue(9, electEvent.getMinVicDemand());
		setNumericalValue(10, electEvent.getMaxVicDemand());
		setNumericalValue(11, electEvent.getTransfer());
		setCategoricalValue(12, electEvent.getPrediction());
	}

	@Override
	public int getClassAttributeIndex() {
		return 12;
	}
}
