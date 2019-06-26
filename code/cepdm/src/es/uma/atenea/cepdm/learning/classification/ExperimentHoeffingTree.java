package es.uma.atenea.cepdm.learning.classification;

import es.uma.atenea.cepdm.learning.MOAClassificationExperiment;
import moa.classifiers.trees.HoeffdingTree;

/** 
 * A MOA experiment subclass that uses a decision tree as classification algorithm.
 * @author Aurora
 * @version 1
 * @see MOAClassificationExperiment
 * */

public class ExperimentHoeffingTree extends MOAClassificationExperiment {

	/**
	 * Parameterized constructor
	 * @param dataset The file path of the dataset
	 * */
	public ExperimentHoeffingTree(String dataset) {
		super(dataset);
	}
	
	@Override
	protected void createAlgorithm() {
		this.learner = new HoeffdingTree();
		this.learner.setModelContext(this.stream.getHeader());
		this.learner.prepareForUse();		
	}
}
