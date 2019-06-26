package es.uma.atenea.cepdm.learning;

import com.yahoo.labs.samoa.instances.Instance;

import moa.classifiers.Classifier;
import moa.core.InstanceExample;
import moa.core.TimingUtils;
import moa.evaluation.BasicClassificationPerformanceEvaluator;

/** 
 * A wrapper for MOA classification algorithms.
 * @author Aurora
 * @version 1
 * */

public abstract class MOAClassificationExperiment extends MOAExperiment{
		
	/** Classification accuracy */
	protected double accuracy;
	
	/** The number of classes in the dataset (2 by default) */
	protected int numClasses = 2;
	
	/**
	 * Parameterized constructor
	 * @param dataset The file path of the dataset
	 * */
	public MOAClassificationExperiment(String dataset) {
		super(dataset);
	}
	
	/**
	 * Get the accuracy
	 * @return Classification accuracy
	 * */
	public double getAccuracy() {
		return this.accuracy;
	}
	
	/**
	 * Create and configure the classification algorithm.
	 * To be refined by subclasses.
	 * */
	protected abstract void createAlgorithm();
	
	/**
	 * Prepare the evaluator object to store 
	 * performance measures
	 * */
	protected void createEvaluator() {
		this.evaluator = new BasicClassificationPerformanceEvaluator();
		// Set optional statistics to true
		((BasicClassificationPerformanceEvaluator)this.evaluator).precisionRecallOutputOption.setValue(true);
		((BasicClassificationPerformanceEvaluator)this.evaluator).f1PerClassOption.setValue(true);
		((BasicClassificationPerformanceEvaluator)this.evaluator).recallPerClassOption.setValue(true);
		((BasicClassificationPerformanceEvaluator)this.evaluator).precisionPerClassOption.setValue(true);
		((BasicClassificationPerformanceEvaluator)this.evaluator).prepareForUse();
	}
		
	/**
	 * The learning phase following a test-then-train strategy
	 * */
	protected void testThenTrain() {
		int numberSamplesCorrect = 0;
		int numberSamples = 0;
		TimingUtils.enablePreciseTiming();
		long evaluateStartTime = TimingUtils.getNanoCPUTimeOfCurrentThread();
		Instance trainInstance;
		InstanceExample trainExample;
		
		while(this.stream.hasMoreInstances()) {
		
			trainExample = this.stream.nextInstance();
			trainInstance = trainExample.getData();
			numberSamples++;
			
			// Test
			if(((Classifier)this.learner).correctlyClassifies(trainInstance)) {
				numberSamplesCorrect++;
			}
			
			// Evaluation
			this.evaluator.addResult(trainExample, ((Classifier)this.learner).getVotesForInstance(trainInstance));
			
			// Train
			((Classifier)this.learner).trainOnInstance(trainInstance);
		}
		
		// Also update statistics at the end
		this.accuracy = 100.0*(double)numberSamplesCorrect/(double)numberSamples;
		this.time = TimingUtils.nanoTimeToSeconds(TimingUtils.getNanoCPUTimeOfCurrentThread()-evaluateStartTime);
	}
}