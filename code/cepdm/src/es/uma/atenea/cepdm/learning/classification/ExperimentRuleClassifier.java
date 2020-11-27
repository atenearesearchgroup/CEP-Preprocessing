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
	
	/**
	 * Parameterized constructor
	 * @param dataset The file path of the dataset
	 * @param numExecution The identifier of the current execution
	 * */
	public ExperimentRuleClassifier(String dataset, int numExecution) {
		super(dataset, numExecution);
	}
	
	/**
	 * Parameterized constructor
	 * @param dataset The file path of the dataset
	 * @param numExecution The identifier of the current execution
	 * @param frequency The reporting frequency
	 * */
	public ExperimentRuleClassifier(String dataset, int numExecution, int frequency) {
		super(dataset, numExecution, frequency);
	}

	@Override
	protected void createAlgorithm() {
		this.learner = new RuleClassifier();
		this.learner.setModelContext(this.stream.getHeader());
		this.learner.prepareForUse();
	}
}
