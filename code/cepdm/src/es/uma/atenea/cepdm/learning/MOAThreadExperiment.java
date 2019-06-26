package es.uma.atenea.cepdm.learning;

import java.util.concurrent.BlockingQueue;

import com.yahoo.labs.samoa.instances.Instance;
import com.yahoo.labs.samoa.instances.InstancesHeader;

import moa.core.Example;
import moa.core.InstanceExample;
import moa.learners.Learner;

/**
 * A class to run MOA experiments in an idependent thread from CEP preprocessing.
 * @author Aurora
 * @version 1
 * */

public abstract class MOAThreadExperiment extends Thread {
	
	/** A queue to store incoming instances */
	private final BlockingQueue<InstanceExample> queue;
	
	/** The algorithm */
	protected Learner<Example<Instance>> learner;
	
	/** The metadata information */
	private final InstancesHeader header;
	
	/**
	 * Parameterized constructor
	 * @param q The queue from which new instances will be consumed
	 * @param h Instances header information
	 * */
	public MOAThreadExperiment(BlockingQueue<InstanceExample> q, InstancesHeader h){
		this.queue = q;
		this.header = h;
		createAlgorithm();
	}
	
	/**
	 * Run method to wait until new instances arrive for learning
	 * */
	public void run() {
		System.out.println("[MOAExperiment] Ready to receive instances...");
		try {
			while(true) {
				testThenTrain(this.queue.take());
			}
		}catch(InterruptedException e) {
			System.err.println("Problems retrieving new instances from queue...");
		}
	}
	
	/**
	 * An abstract method to create the specific algorithm
	 * */
	public abstract void createAlgorithm();
	
	/**
	 * An abstract method to perform test-then-train learning
	 * */
	protected abstract void testThenTrain(InstanceExample instance);
	
	/** 
	 * Get the configured learner
	 * @return Learning algorithm
	 * */
	public Learner<Example<Instance>> getLearner(){
		return this.learner;
	}
	
	/**
	 * Get the configured instance header
	 * @return Stream metadata
	 * */
	public InstancesHeader getDataHeader() {
		return this.header;
	}
}
