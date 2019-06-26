package es.uma.atenea.cepdm.learning;

import java.util.concurrent.BlockingQueue;

import com.yahoo.labs.samoa.instances.Instance;
import com.yahoo.labs.samoa.instances.InstancesHeader;

import moa.classifiers.Classifier;
import moa.core.InstanceExample;

/**
 * A class to run MOA classification experiments directly receiving the instances from CEP.
 * Only accuracy is computed.
 * @author Aurora
 * @version 1
 * */

public abstract class MOAThreadClassificationExperiment extends MOAThreadExperiment {

	/** Number of samples correctly classified */
	protected int numberSamplesCorrect = 0;
	
	/** Number of samples processed */
	protected int numberSamples = 0;
	
	/** Current accurcara */
	protected double accuracy;

	/**
	 * Parameterized constructor
	 * @param q Collection of instances
	 * @param h Stream header information
	 * */
	public MOAThreadClassificationExperiment(BlockingQueue<InstanceExample> q, InstancesHeader h) {
		super(q,h);
	}

	@Override
	public	abstract void createAlgorithm();

	@Override
	protected void testThenTrain(InstanceExample instance) {
		Instance trainInstance = instance.getData();

		this.numberSamples++;

		System.out.println("[MOAOnlineExperiment] New instance received... total processed = " + numberSamples);

		try {
			// Test
			if(((Classifier)this.learner).correctlyClassifies(trainInstance)) {
				this.numberSamplesCorrect++;
			}
			updateAccuracy();
			
			// Train
			((Classifier)this.learner).trainOnInstance(trainInstance);
			
			System.out.println("[MOAOnlineExperiment] Updated accuracy: " + getAccuracy());
			
		}catch(Exception e) {
			System.err.println("[MOAOnlineExperiment] Error during learning...");
			e.printStackTrace();
		}
	}

	/**
	 * Update accuracy measure
	 * */
	public synchronized void updateAccuracy() {
		this.accuracy = 100.0*(double)numberSamplesCorrect/(double)numberSamples;
	}
	
	/**
	 * Get current accuracy value
	 * @param Accuracy of the current classifier
	 * */
	public synchronized double getAccuracy() {
		return this.accuracy;
	}
}
