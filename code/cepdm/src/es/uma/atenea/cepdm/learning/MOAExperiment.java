package es.uma.atenea.cepdm.learning;

import com.yahoo.labs.samoa.instances.Instance;

import moa.core.Example;
import moa.evaluation.LearningPerformanceEvaluator;
import moa.learners.Learner;
import moa.streams.ArffFileStream;

/** 
 * A wrapper for MOA experiments. It defines common methods to prepare the execution and save reports. 
 * Subclasses should define those methods that depend on the particular task (regression or classification).
 * 
 * @author Aurora
 * @version 1
 * */

public abstract class MOAExperiment {

	/** The output directory */
	protected String dir;
	
	/** The file path to the ARFF dataset */
	protected String dataset;
	
	/** The data stream created from ARFF file */
	protected ArffFileStream stream;
	
	/** The algorithm */
	protected Learner<Example<Instance>> learner;
	
	/** The evaluator */
	protected LearningPerformanceEvaluator<Example<Instance>> evaluator;

	/** Total execution time */
	protected double time;
	
	/** Report frequency for classification performance measures (1 by default) */
	protected int frequency = 1;
	
	/** Number of execution */
	protected int numExecution = 1;
	
	/**
	 * Parameterized dataset
	 * @param dataset The name of the ARFF dataset
	 * */
	public MOAExperiment(String dataset) {
		this.dataset = dataset;
	}
	
	/**
	 * Parameterized dataset
	 * @param dataset The name of the ARFF dataset
	 * @param frequency Reporting frequency
	 * */
	public MOAExperiment(String dataset, int numExecution) {
		this.dataset = dataset;
		this.numExecution = numExecution;
	}
	
	/**
	 * Parameterized dataset
	 * @param dataset The name of the ARFF dataset
	 * @param numExecution Identifier of current execution
	 * @param frequency Reporting frequency
	 * */
	public MOAExperiment(String dataset, int numExecution, int frequency) {
		this.dataset = dataset;
		this.numExecution = numExecution;
		this.frequency = frequency;
	}
	
	/**
	 * Set the report frequency for classification performance measures
	 * @param frequency Reporting frequency
	 * */
	public void setReportFrequency(int frequency) {
		this.frequency = frequency;
	}
	
	/**
	 * Run the experiment
	 * */
	public void run() {
		
		// Configuration
		createStreamFromDataset();
		createAlgorithm();
		createEvaluator();
		
		// Learning
		testThenTrain();
	}
	
	/**
	 * Open the ARFF dataset and prepare the 
	 * data stream for classification
	 * */
	protected void createStreamFromDataset() {
		this.stream = new ArffFileStream();
		this.stream.arffFileOption.setValue(this.dataset);
		this.stream.prepareForUse();
	}
	
	/**
	 * Create and configure the algorithm.
	 * To be refined by subclasses.
	 * */
	protected abstract void createAlgorithm();
	
	/**
	 * Create and configure the evaluator.
	 * To be refined by subclasses.
	 * */
	protected abstract void createEvaluator();
	
	/**
	 * The learning method. To be refined by
	 * subclasses according to the learning task.
	 * */
	protected abstract void testThenTrain();
}
