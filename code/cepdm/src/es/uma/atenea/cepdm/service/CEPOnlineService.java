package es.uma.atenea.cepdm.service;

import java.util.concurrent.BlockingQueue;

import com.yahoo.labs.samoa.instances.InstancesHeader;

import es.uma.atenea.cepdm.learning.MOAThreadExperiment;
import moa.core.InstanceExample;

/**
 * A special CEP service to directly send instances to MOA algorithms
 * @author Aurora
 * @version 1
 * */

public abstract class CEPOnlineService extends CEPService {

	/** A queue to store incoming instances */
	private final BlockingQueue<InstanceExample> queue;
	
	/** The metadata information */
	private final InstancesHeader header;
	
	/** The learning process */
	protected MOAThreadExperiment experiment;
	
	/**
	 * Parameterized constructor
	 * @param q Queue of incoming instances
	 * @param h Instance metadata
	 * */
	public CEPOnlineService(BlockingQueue<InstanceExample> q, InstancesHeader h) {
		super();
		this.queue = q;
		this.header = h;
	}
	
	/**
	 * Configure the learning process depending on the type of algorithm
	 * */
	public abstract void prepareLearningProcess();
	
	/**
	 * Initialize the experiment handler in an independent threat
	 * */
	public void initializeLearningProcess() {
		if(this.experiment != null)
			this.experiment.start();
	}
	
	/**
	 * Get the collection of instances
	 * @return Queue with current instances
	 * */
	protected BlockingQueue<InstanceExample> getQueue() {
		return this.queue;
	}
	
	/**
	 * Get the metadata
	 * @return Instances header object
	 * */
	protected InstancesHeader getDataHeader() {
		return this.header;
	}
}
