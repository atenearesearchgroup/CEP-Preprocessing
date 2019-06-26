package es.uma.atenea.cepdm.learning.classification;

import es.uma.atenea.cepdm.learning.MOAClassificationExperiment;
import moa.classifiers.bayes.NaiveBayes;

/** 
 * A MOA experiment subclass that uses Naive Bayes algorithm as classifier.
 * @author Aurora
 * @version 1
 * @see MOAClassificationExperiment
 * */

public class ExperimentNaiveBayes extends MOAClassificationExperiment {

	/**
	 * Parameterized constructor
	 * @param dataset The file path of the dataset
	 * */
	public ExperimentNaiveBayes(String dataset) {
		super(dataset);
	}
	
	@Override
	protected void createAlgorithm() {
		this.learner = new NaiveBayes();
		this.learner.setModelContext(this.stream.getHeader());
		this.learner.prepareForUse();	
	}
}
