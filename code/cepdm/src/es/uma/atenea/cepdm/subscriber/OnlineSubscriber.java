package es.uma.atenea.cepdm.subscriber;

import com.yahoo.labs.samoa.instances.Instance;

/**
 * An interface for subscribers registered in online services. They have to send the resulting instances to the algorithm using a queue
 * @author Aurora
 * @version 1
 * */

public interface OnlineSubscriber {

	/**
	 * Add an instance to the queue so that it can be used by the algorithm for training
	 * @param instance New instance produced by the rule
	 * */
	public void putInstanceInQueue(Instance instance);
}
