package es.uma.atenea.cepdm.subscriber.electricity;

import java.util.concurrent.BlockingQueue;

import com.yahoo.labs.samoa.instances.Instance;

import es.uma.atenea.cepdm.event.electricity.Electricity;
import es.uma.atenea.cepdm.event.electricity.ElectricityEnrichedAvg;
import es.uma.atenea.cepdm.subscriber.OnlineSubscriber;
import moa.core.InstanceExample;

/**
 * A subscriber that enriches electricity events with average values of price and demand.
 * The resulting instance is directly added to the queue for learning.
 * @author Aurora
 * @version 1
 * @see ElectricityEnrichedAvgSubscriber
 * @see OnlineSubscriber
 * */

public class ElectricityEnrichedAvgOnlineSubscriber extends ElectricityEnrichedAvgSubscriber
		implements OnlineSubscriber {

	/** The queue where instances are stored */
	private final BlockingQueue<InstanceExample> queue;
	
	/**
	 * Parameterized constructor
	 * @param queue Queue where instances has to be added
	 * */
	public ElectricityEnrichedAvgOnlineSubscriber(BlockingQueue<InstanceExample> queue) {
		super();
		this.queue = queue;
	}

	/**
	 * Parameterized constructor
	 * @param queue Queue where instances has to be added
	 * @param windowsSize Window size for average function
	 * */
	public ElectricityEnrichedAvgOnlineSubscriber(BlockingQueue<InstanceExample> queue, int windowsSize) {
		super(windowsSize);
		this.queue = queue;
	}

	/**
	 * Update method (listener)
	 * @param originalEvent The complete original instance
	 * @param avgNswPrice Average price in NSW
	 * @param avgNswDemand Average demand in NSW
	 * @param avgVicPrice Average price in Victoria
	 * @param avgVicDemand Average demand in Victoria
	 * */
	public void update(Electricity originalEvent, double avgNswPrice, double avgNswDemand, double avgVicPrice, double avgVicDemand) {
		ElectricityEnrichedAvg event = new ElectricityEnrichedAvg(originalEvent, avgNswPrice, avgNswDemand, avgVicPrice, avgVicDemand);
		Instance instance = event.toMOAInstance();
		putInstanceInQueue(instance);
	}
	
	@Override
	public void putInstanceInQueue(Instance instance) {
		InstanceExample example = new InstanceExample(instance);
		try {
			System.out.println("[CEP Subscriber] Putting new instance in the learning queue... ");
			queue.put(example);
			
		} catch (InterruptedException e) {
			System.err.println("[CEP Subscriber] Unable to put new instance in queue for learning...");
			e.printStackTrace();
		}

	}
}
