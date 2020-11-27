package es.uma.atenea.cepdm.learning;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.yahoo.labs.samoa.instances.Instance;

import es.uco.kdis.datapro.dataset.source.CsvDataset;
import moa.MOAObject;
import moa.core.Example;
import moa.core.Measurement;
import moa.evaluation.LearningPerformanceEvaluator;
import moa.learners.Learner;
import moa.streams.ArffFileStream;

/** 
 * A wrapper for MOA experiments. It defines common methods to prepare the execution and save reports. 
 * Subclasses should define those methods that depend on the particular task (regression or classification).
 * 
 * @author Aurora
 * @version 2
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
	
	/** The window-based evaluator */
	protected LearningPerformanceEvaluator<Example<Instance>> wEvaluator;
	
	/** The dataset to store the measurements */
	protected CsvDataset resDataset;
	
	/** The dataset to store the window-based measurements */
	protected CsvDataset wResDataset;
	
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
		createResDataset();
		
		// Learning
		testThenTrain();
		
		// Post-processing
		createExperimentFolder();
		saveModelResults();
		savePerformanceMeasures();
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
	 * Create the columns of the dataset according
	 * to the selected evaluator. To be refined
	 * by subclasses.
	 * */
	protected abstract void createResDataset();
	
	/**
	 * The learning method. To be refined by
	 * subclasses according to the learning task.
	 * */
	protected abstract void testThenTrain();
	
	/**
	 * Save the current performance metrics in the result datasets
	 * @param currentNumberSamples Current number of samples
	 * */
	protected void updateMeasureDataset(int currentNumberSamples) {
		// Standard evaluator
		Measurement [] measurements = this.evaluator.getPerformanceMeasurements();
		int numMeasures = measurements.length;
		this.resDataset.getColumn(0).addValue(currentNumberSamples);
		for(int i=1; i<numMeasures; i++) { // Skip first value (number of report)
			this.resDataset.getColumn(i).addValue(measurements[i].getValue());
		}
		
		// Window-based evaluator
		Measurement [] wMeasurements = this.wEvaluator.getPerformanceMeasurements();
		numMeasures = wMeasurements.length;
		this.wResDataset.getColumn(0).addValue(currentNumberSamples);
		for(int i=1; i<numMeasures; i++) { // Skip first value (number of report)
			this.wResDataset.getColumn(i).addValue(wMeasurements[i].getValue());
		}
	}
	
	/**
	 * Save the information of the model in a TXT file
	 * */
	protected void saveModelResults() {
		
		// Get the learned model
		MOAObject model = this.learner.getModel();
		StringBuilder sb = new StringBuilder();
		model.getDescription(sb, 5);
		
		// Get the evaluation measurement
		Measurement [] measurements = this.learner.getModelMeasurements();
		String learnerName = this.learner.getClass().getSimpleName();
		
		// Append the results
		StringBuffer buffer = new StringBuffer();
		buffer.append("Dataset: " + this.dataset + "\n");
		buffer.append("Classifier: " + learnerName + "\n");
		buffer.append("Time: " + this.time + "\n");
		for(int i=0; i<measurements.length; i++) {
			buffer.append("Name: " + measurements[i].getName() + " Value: " + measurements[i].getValue() + "\n");
		}
		buffer.append("Model: \n" + sb.toString());
		
		// Save in file
		String outfilename = this.dir + "/" + getExperimentName() + "-model-" + numExecution + ".txt";
		
		try {
			FileWriter writer = new FileWriter(outfilename);
			writer.write(buffer.toString());
			writer.close();
		}catch(IOException e) {
			System.err.println("Unable to write in file. Output to be shown in console...");
			System.out.println(buffer.toString());
		}
	}
	
	/**
	 * Save the performance measures in a CSV file
	 * */
	protected void savePerformanceMeasures() {
		try {
			
			// Standard evaluation metrics
			String filename = this.dir + "/" + getExperimentName() + "-measures-" + this.numExecution + ".csv";
			this.resDataset.setNumberOfDecimals(6);
			this.resDataset.writeDataset(filename);
			
			// Window-based evaluation metrics
			filename = this.dir + "/" + getExperimentName() + "-window-measures-" + this.numExecution + ".csv";
			this.wResDataset.setNumberOfDecimals(6);
			this.wResDataset.writeDataset(filename);
		}catch(IOException e) {
			System.err.println("Unable to write measures in CSV file.");
		}	
	}
	
	/**
	 * Return a name for the current experiment
	 * containing the algorithm name and the dataset
	 * @return A string with the experiment name
	 * */
	protected String getExperimentName() {
		String expName = this.learner.getClass().getSimpleName() + "-" + this.dataset.substring(dataset.lastIndexOf("/")+1, dataset.indexOf(".arff"));
		return expName;
	}
	
	/**
	 * Create the folders to store the results of this experiment. By default the structure
	 * is a root folder ("results"), then a folder with the name of the algorithm and
	 * then a folder with the name of the dataset. 
	 * */
	protected void createExperimentFolder() {
		this.dir = "results/" + this.dataset.substring(dataset.lastIndexOf("/")+1, dataset.indexOf(".arff")) + "/" + this.learner.getClass().getSimpleName();
		(new File(this.dir)).mkdirs();
	}
}
