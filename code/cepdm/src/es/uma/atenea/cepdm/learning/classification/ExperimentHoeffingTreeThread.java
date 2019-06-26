package es.uma.atenea.cepdm.learning.classification;

import java.util.concurrent.BlockingQueue;

import com.yahoo.labs.samoa.instances.InstancesHeader;

import es.uma.atenea.cepdm.learning.MOAThreadClassificationExperiment;
import moa.classifiers.trees.HoeffdingTree;
import moa.core.InstanceExample;

/** 
 * A MOA experiment subclass that uses a decision tree as classification algorithm in an
 * independent thread.
 * @author Aurora
 * @version 1
 * @see MOAThreadClassificationExperiment
 * */
public class ExperimentHoeffingTreeThread extends MOAThreadClassificationExperiment {

	/**
	 * Parameterized constructor
	 * @param q Collection of instances
	 * @param h Stream header information
	 * */
	public ExperimentHoeffingTreeThread(BlockingQueue<InstanceExample> q, InstancesHeader h) {
		super(q,h);
	}

	@Override
	public void createAlgorithm() {
		this.learner = new HoeffdingTree();
		this.learner.setModelContext(getDataHeader());
		this.learner.prepareForUse();
	}
}
