package es.uma.atenea.cepdm.io.converter.electricity;

import java.util.Arrays;
import java.util.List;

import com.yahoo.labs.samoa.instances.Attribute;

import es.uma.atenea.cepdm.event.electricity.ElectricityEnrichedMinMax;
import es.uma.atenea.cepdm.io.converter.EventToInstanceConverter;

/**
 * A converter that creates instances from electricity events enriched with minimum
 * and maximum price and demand.
 * @author Aurora
 * @version 1
 * */

public class ElectricityEnrichedMinMaxToInstanceConverter extends EventToInstanceConverter {

	/**
	 * Singleton instance
	 * */
	private static ElectricityEnrichedMinMaxToInstanceConverter singleton = null;
	
	/**
	 * Private constructor
	 * */
	private ElectricityEnrichedMinMaxToInstanceConverter() {
		// do nothing
	}

	/**
	 * Public method to get an unique instance of this converter
	 * */
	public static ElectricityEnrichedMinMaxToInstanceConverter getConverter() {
		if(singleton == null)
			singleton = new ElectricityEnrichedMinMaxToInstanceConverter();
		return singleton;
	}
	
	@Override
	protected Attribute[] createAttributes() {
		Attribute [] attributes = new Attribute[17];
		attributes[0] = super.createNumericalAttribute("date");
		List<String> days = Arrays.asList("1","2","3","4","5","6","7");
		attributes[1] = super.createCategoricalAttribute("day", days);
		attributes[2] = super.createNumericalAttribute("period");
		attributes[3] = super.createNumericalAttribute("nswprice");
		attributes[4] = super.createNumericalAttribute("minnswprice");
		attributes[5] = super.createNumericalAttribute("maxnswprice");
		attributes[6] = super.createNumericalAttribute("nswdemand");
		attributes[7] = super.createNumericalAttribute("minnswdemand");
		attributes[8] = super.createNumericalAttribute("maxnswdemand");
		attributes[9] = super.createNumericalAttribute("vicprice");
		attributes[10] = super.createNumericalAttribute("minvicprice");
		attributes[11] = super.createNumericalAttribute("maxvicprice");
		attributes[12] = super.createNumericalAttribute("vicdemand");
		attributes[13] = super.createNumericalAttribute("minvicdemand");
		attributes[14] = super.createNumericalAttribute("maxvicdemand");
		attributes[15] = super.createNumericalAttribute("transfer");
		List<String> prediction = Arrays.asList("UP","DOWN");
		attributes[16] = super.createCategoricalAttribute("class", prediction);
		return attributes;
	}

	@Override
	public void fillInstanceWithValues() {
		ElectricityEnrichedMinMax electEvent = (ElectricityEnrichedMinMax)this.event;
		setNumericalValue(0, electEvent.getTimestamp());
		setCategoricalValue(1, electEvent.getDay());
		setNumericalValue(2, electEvent.getPeriod());
		setNumericalValue(3, electEvent.getNswprice());
		setNumericalValue(4, electEvent.getMinNswPrice());
		setNumericalValue(5, electEvent.getMaxNswPrice());
		setNumericalValue(6, electEvent.getNswdemand());
		setNumericalValue(7, electEvent.getMinNswDemand());
		setNumericalValue(8, electEvent.getMaxNswDemand());
		setNumericalValue(9, electEvent.getVicprice());
		setNumericalValue(10, electEvent.getMinVicPrice());
		setNumericalValue(11, electEvent.getMaxVicPrice());
		setNumericalValue(12, electEvent.getVicdemand());
		setNumericalValue(13, electEvent.getMinVicDemand());
		setNumericalValue(14, electEvent.getMaxVicDemand());
		setNumericalValue(15, electEvent.getTransfer());
		setCategoricalValue(16, electEvent.getPrediction());
	}

	@Override
	public int getClassAttributeIndex() {
		return 16;
	}
}
