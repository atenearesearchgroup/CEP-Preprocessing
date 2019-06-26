package es.uma.atenea.cepdm.learning;

import com.yahoo.labs.samoa.instances.Instance;
import com.yahoo.labs.samoa.instances.Prediction;

import moa.classifiers.Classifier;
import moa.core.InstanceExample;
import moa.core.TimingUtils;
import moa.evaluation.BasicRegressionPerformanceEvaluator;

/** 
 * A wrapper for regression algorithms.
 * @author Aurora
 * @version 1
 * */

public abstract class MOARegressionExperiment extends MOAExperiment {
	
	/**
	 * Parameterized constructor
	 * @param dataset The file path of the dataset
	 * */
	public MOARegressionExperiment(String dataset) {
		super(dataset);
	}
	
	/**
	 * Create and configure the regression algorithm.
	 * To be refined by subclasses.
	 * */
	protected abstract void createAlgorithm();
	
	
	/**
	 * Prepare the evaluator object to store 
	 * performance measures
	 * */
	protected void createEvaluator() {
		this.evaluator = new BasicRegressionPerformanceEvaluator();
	}
	
	@Override
	protected void testThenTrain() {
		int numberSamples = 0;
		TimingUtils.enablePreciseTiming();
		long evaluateStartTime = TimingUtils.getNanoCPUTimeOfCurrentThread();
		Instance trainInstance;
		InstanceExample trainExample;
		
		while(this.stream.hasMoreInstances() && numberSamples<=45) {
		
			trainExample = this.stream.nextInstance();
			trainInstance = trainExample.getData();
			numberSamples++;

			// Test
			Prediction prediction = this.learner.getPredictionForInstance(trainExample);
			
			// Evaluation
			this.evaluator.addResult(trainExample, prediction);
			
			// Train
			((Classifier)this.learner).trainOnInstance(trainInstance);
		}
		
		// Also update statistics at the end
		this.time = TimingUtils.nanoTimeToSeconds(TimingUtils.getNanoCPUTimeOfCurrentThread()-evaluateStartTime);
	}
}
