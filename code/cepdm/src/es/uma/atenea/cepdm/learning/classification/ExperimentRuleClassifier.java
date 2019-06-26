package es.uma.atenea.cepdm.learning.classification;

import es.uma.atenea.cepdm.learning.MOAClassificationExperiment;
import moa.classifiers.rules.RuleClassifier;

/** 
 * A MOA experiment subclass that uses a rule-based classifier as algorithm.
 * @author Aurora
 * @version 1
 * @see MOAClassificationExperiment
 * */

public class ExperimentRuleClassifier extends MOAClassificationExperiment {

	/**
	 * Parameterized constructor
	 * @param dataset The file path of the dataset
	 * */
	public ExperimentRuleClassifier(String dataset) {
		super(dataset);
	}

	@Override
	protected void createAlgorithm() {
		this.learner = new RuleClassifier();
		this.learner.setModelContext(this.stream.getHeader());
		this.learner.prepareForUse();
	}
}
