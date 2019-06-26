package es.uma.atenea.cepdm.event;

import com.yahoo.labs.samoa.instances.Instance;

import es.uma.atenea.cepdm.io.converter.EventToInstanceConverter;

/**
 * An abstract class to represent the events that could be sent to MOA for data mining.
 * @author Aurora
 * @version 1
 * */

public abstract class AbstractEvent implements Event {

	/** The Event-Instance converter */
	protected EventToInstanceConverter converter;
	
	/**
	 * Empty constructor
	 * */
	public AbstractEvent() {
		// do nothing
	}

	@Override
	public Instance toMOAInstance() {
		if(this.converter == null) {
			this.converter = createConverter();
		}
		Instance instance = this.converter.createInstanceFromEvent(this);
		return instance;
	}
	
	/**
	 * Get a proper converter for the event
	 * */
	public EventToInstanceConverter getConverter() {
		return this.converter;
	}
	
	/**
	 * Provide a specific converter for the event type
	 * */
	protected abstract EventToInstanceConverter createConverter();
}
