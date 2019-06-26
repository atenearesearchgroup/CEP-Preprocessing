package es.uma.atenea.cepdm.io.converter;

import com.yahoo.labs.samoa.instances.InstanceImpl;

import es.uma.atenea.cepdm.event.AbstractEvent;

/**
 * An abstract general converter that creates events from ARFF instances
 * @author Aurora
 * @version 1
 * */
public abstract class InstanceToEventConverter {

	/**
	 * Empty constructor
	 * */
	public InstanceToEventConverter() {
		// do nothing
	}
	
	/**
	 * Create an event from an instance 
	 * */
	public abstract AbstractEvent createEventFromInstance(InstanceImpl instance);

}
