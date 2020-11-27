package es.uma.atenea.cepdm.learning.classification;

import es.uma.atenea.cepdm.learning.MOAClassificationExperiment;
import moa.classifiers.meta.AdaptiveRandomForest;

/** 
 * A MOA experiment subclass that uses an adaptive random forest (ensemble) as classification algorithm.
 * Since the algorithm is randomized, the number of execution is used as random seed.
 * @author Aurora
 * @version 1
 * @see MOAClassificationExperiment
 * */

public class ExperimentAdaptiveRF extends MOAClassificationExperiment {

	/**
	 * Parameterized constructor
	 * @param dataset The file path of the dataset
	 * */
	public ExperimentAdaptiveRF(String dataset) {
		super(dataset);
	}

	/**
	 * Parameterized constructor
	 * @param dataset The file path of the dataset
	 * @param numExecution The identifier of the current execution
	 * */
	public ExperimentAdaptiveRF(String dataset, int numExecution) {
		super(dataset, numExecution);
	}

	/**
	 * Parameterized constructor
	 * @param dataset The file path of the dataset
	 * @param numExecution The identifier of the current execution
	 * @param frequency The reporting frequency
	 * */
	public ExperimentAdaptiveRF(String dataset, int numExecution, int frequency) {
		super(dataset, numExecution, frequency);
	}

	@Override
	protected void createAlgorithm() {
		this.learner = new AdaptiveRandomForest();
		((AdaptiveRandomForest)this.learner).setRandomSeed(this.numExecution);
		this.learner.setModelContext(this.stream.getHeader());
		this.learner.prepareForUse();	
	}

}
