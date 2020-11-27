package es.uma.atenea.cepdm.learning;

import com.yahoo.labs.samoa.instances.Instance;

import es.uco.kdis.datapro.dataset.column.IntegerColumn;
import es.uco.kdis.datapro.dataset.column.NumericalColumn;
import es.uco.kdis.datapro.dataset.source.CsvDataset;
import moa.classifiers.Classifier;
import moa.core.InstanceExample;
import moa.core.TimingUtils;
import moa.evaluation.BasicClassificationPerformanceEvaluator;
import moa.evaluation.WindowClassificationPerformanceEvaluator;

/** 
 * A wrapper for MOA classification algorithms.
 * @author Aurora
 * @version 2
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
	 * Parameterized constructor
	 * @param dataset The file path of the dataset
	 * @param numExecution The identifier of the current execution
	 * */
	public MOAClassificationExperiment(String dataset, int numExecution) {
		super(dataset, numExecution);
	}
	
	/**
	 * Parameterized constructor
	 * @param dataset The file path of the dataset
	 * @param numExecution The identifier of the current execution
	 * @param frequency The reporting frequency
	 * */
	public MOAClassificationExperiment(String dataset, int numExecution, int frequency) {
		super(dataset, numExecution, frequency);
	}
	
	/**
	 * Parameterized constructor
	 * @param dataset The file path of the dataset
	 * @param numExecution The identifier of the current execution
	 * @param frequency The reporting frequency
	 * @param numberrOfClasses Number of classes for prediction
	 * */
	public MOAClassificationExperiment(String dataset, int numExecution, int frequency, int numberOfClasses) {
		super(dataset, numExecution, frequency);
		this.numClasses = numberOfClasses;
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
		// Standard evaluator with optional statistics set to true
		this.evaluator = new BasicClassificationPerformanceEvaluator();
		((BasicClassificationPerformanceEvaluator)this.evaluator).precisionRecallOutputOption.setValue(true);
		((BasicClassificationPerformanceEvaluator)this.evaluator).f1PerClassOption.setValue(true);
		((BasicClassificationPerformanceEvaluator)this.evaluator).recallPerClassOption.setValue(true);
		((BasicClassificationPerformanceEvaluator)this.evaluator).precisionPerClassOption.setValue(true);
		((BasicClassificationPerformanceEvaluator)this.evaluator).prepareForUse();
		
		// Window-based evaluator with optional statistics set to true
		this.wEvaluator = new WindowClassificationPerformanceEvaluator();
		((WindowClassificationPerformanceEvaluator)this.wEvaluator).precisionRecallOutputOption.setValue(true);
		((WindowClassificationPerformanceEvaluator)this.wEvaluator).f1PerClassOption.setValue(true);
		((WindowClassificationPerformanceEvaluator)this.wEvaluator).recallPerClassOption.setValue(true);
		((WindowClassificationPerformanceEvaluator)this.wEvaluator).precisionPerClassOption.setValue(true);
		((WindowClassificationPerformanceEvaluator)this.wEvaluator).prepareForUse();
	}
		
	/**
	 * Create the datasets to add measurements during evaluation
	 * */
	protected void createResDataset() {
		
		this.resDataset = new CsvDataset();
		this.wResDataset = new CsvDataset();
		
		// First column is the number of samples
		this.resDataset.addColumn(new IntegerColumn("samples"));
		this.wResDataset.addColumn(new IntegerColumn("samples"));
		
		// Each column is a performance measure
		this.resDataset.addColumn(new NumericalColumn("accuracy"));
		this.resDataset.addColumn(new NumericalColumn("kappa"));
		this.resDataset.addColumn(new NumericalColumn("temporalKappa"));
		this.resDataset.addColumn(new NumericalColumn("mKappa"));
		this.resDataset.addColumn(new NumericalColumn("f1"));
		
		this.wResDataset.addColumn(new NumericalColumn("accuracy"));
		this.wResDataset.addColumn(new NumericalColumn("kappa"));
		this.wResDataset.addColumn(new NumericalColumn("temporalKappa"));
		this.wResDataset.addColumn(new NumericalColumn("mKappa"));
		this.wResDataset.addColumn(new NumericalColumn("f1"));
		
		// Columns for class values
		for(int i=0; i<this.numClasses; i++) {
			this.resDataset.addColumn(new NumericalColumn("f1-class"+i));
			this.wResDataset.addColumn(new NumericalColumn("f1-class"+i));
		}
		
		this.resDataset.addColumn(new NumericalColumn("precision"));
		this.wResDataset.addColumn(new NumericalColumn("precision"));
		for(int i=0; i<this.numClasses; i++) {
			this.resDataset.addColumn(new NumericalColumn("precision-class"+i));
			this.wResDataset.addColumn(new NumericalColumn("precision-class"+i));
		}
		
		this.resDataset.addColumn(new NumericalColumn("recall"));
		this.wResDataset.addColumn(new NumericalColumn("recall"));
		for(int i=0; i<this.numClasses; i++) {
			this.resDataset.addColumn(new NumericalColumn("recall-class"+i));
			this.wResDataset.addColumn(new NumericalColumn("recall-class"+i));
		}
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
			this.wEvaluator.addResult(trainExample, ((Classifier)this.learner).getVotesForInstance(trainInstance));
			if(numberSamples % this.frequency == 0) {
				updateMeasureDataset(numberSamples);
			}
			
			// Train
			((Classifier)this.learner).trainOnInstance(trainInstance);
		}
		
		// Also update statistics at the end
		updateMeasureDataset(numberSamples);
		this.accuracy = 100.0*(double)numberSamplesCorrect/(double)numberSamples;
		this.time = TimingUtils.nanoTimeToSeconds(TimingUtils.getNanoCPUTimeOfCurrentThread()-evaluateStartTime);
	}
}