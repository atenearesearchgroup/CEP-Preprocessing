package es.uma.atenea.cepdm.learning.classification;

import es.uma.atenea.cepdm.learning.MOAClassificationExperiment;
import moa.classifiers.lazy.kNN;

/** 
 * A MOA experiment subclass that uses a lazy classifier (k nearest neighbors) as algorithm.
 * @author Aurora
 * @version 1
 * @see MOAClassificationExperiment
 * */

public class ExperimentKNN extends MOAClassificationExperiment {

	/**
	 * Parameterized constructor
	 * @param dataset The file path of the dataset
	 * */
	public ExperimentKNN(String dataset) {
		super(dataset);
	}

	@Override
	protected void createAlgorithm() {
		this.learner = new kNN();
		this.learner.setModelContext(this.stream.getHeader());
		this.learner.prepareForUse();
	}
}
