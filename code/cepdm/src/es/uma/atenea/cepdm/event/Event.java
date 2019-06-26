package es.uma.atenea.cepdm.event;

import com.yahoo.labs.samoa.instances.Instance;

/**
 * A simple interface to force a conversion method in events.
 * @author Aurora
 * @version 1
 * */

public interface Event {

	/** 
	 * Convert the CEP event into a MOA instance 
	 * @return A MOA instance with the event information
	 * */
	public Instance toMOAInstance();
	
}
