package es.uma.atenea.cepdm.learning.regression;

import java.io.File;

import com.github.javacliparser.MultiChoiceOption;

import es.uma.atenea.cepdm.learning.MOARegressionExperiment;
import moa.classifiers.functions.SGD;

/**
 * A MOA experiment subclass that uses a stochastic gradient descend as regression algorithm.
 * Three different loss functions can be configured: hinge loss (SVM), log loss (logistic regression)
 * or squared loss (linear regression).
 * @author Aurora
 * @version 1
 * @see MOARegressionExperiment
 * */

public class ExperimentSGD extends MOARegressionExperiment {

	/** Type of regression (0 by default) */
	protected int regressionType = 0;
		
	/**
	 * Parameterized constructor
	 * @param dataset The file path of the dataset
	 * */
	public ExperimentSGD(String dataset) {
		super(dataset);
	}
	
	/**
	 * Parameterized constructor
	 * @param dataset The file path of the dataset
	 * @param regressionType The type of regression
	 * */
	public ExperimentSGD(String dataset, int regressionType) {
		super(dataset);
		this.regressionType = regressionType;
	}

	/**
	 * Set the regression type:
	 * <ol>
	 * 	<li>0: Hinge loss (SVM)</li>
	 * 	<li>1: Log loss (logistic regression)</li>
	 * 	<li>2: Squared loss (regression)</li>
	 * </ol>
	 * */
	public void setRegressionType(int regressionType) {
		this.regressionType = regressionType;
		if(this.learner != null) {
			((SGD)this.learner).setLossFunction(regressionType);
			// This is also needed to prevent the learner get the default value in the reset method
			((SGD)this.learner).lossFunctionOption = new MultiChoiceOption(
		            "lossFunction", 'o', "The loss function to use.", new String[]{
		                    "HINGE", "LOGLOSS", "SQUAREDLOSS"}, new String[]{
		                    "Hinge loss (SVM)",
		                    "Log loss (logistic regression)",
		                    "Squared loss (regression)"}, this.regressionType);
		}
	}
	
	@Override
	protected void createAlgorithm() {
		this.learner = new SGD();
		setRegressionType(this.regressionType);
		((SGD)this.learner).setModelContext(this.stream.getHeader());
		((SGD)this.learner).prepareForUse();
	}
	
	/**
	 * {@inheritDoc}
	 * <p>Add the regression type to the experiment name
	 * */
	protected String getExperimentName() {
		String expName = this.learner.getClass().getSimpleName() + "-reg" + this.regressionType + "-" + 
				this.dataset.substring(dataset.lastIndexOf("/")+1, dataset.indexOf(".arff"));
		return expName;
	}
	
	/**
	 * {@inheritDoc}
	 * <p>Add the regression type to the experiment name 
	 * */
	protected void createExperimentFolder() {
		this.dir = "results/" + this.dataset.substring(dataset.lastIndexOf("/")+1, dataset.indexOf(".arff")) + "/" + 
				this.learner.getClass().getSimpleName() + "-reg" + this.regressionType;
		(new File(this.dir)).mkdirs();
	}
}
