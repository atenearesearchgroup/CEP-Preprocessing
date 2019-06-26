package es.uma.atenea.cepdm.learning;

import es.uma.atenea.cepdm.learning.classification.ExperimentHoeffingTree;
import es.uma.atenea.cepdm.learning.classification.ExperimentKNN;
import es.uma.atenea.cepdm.learning.classification.ExperimentNaiveBayes;
import es.uma.atenea.cepdm.learning.classification.ExperimentRuleClassifier;

/**
 * A main program to run classification algorithms from a folder containing datasets.
 * For testing purposes.
 * @author Aurora
 * @version 1
 * */
public class MainMOAClassificationExperiment {

	public static void main(String[] args) {

		String dir = "./datasets";
		String [] datasets = {"airlines.arff", "elecNormNew.arff"};

		for(int i=0; i<datasets.length; i++) {

			System.out.println("Dataset: " + datasets[i]);
			
			// Decision tree
			System.out.println("\tRunning HoeffingTree...");
			ExperimentHoeffingTree exp1 = new ExperimentHoeffingTree(dir+"/"+datasets[i]);
			exp1.run();

			// Naive Bayes
			System.out.println("\tRunning NaiveBayes...");
			ExperimentNaiveBayes exp2 = new ExperimentNaiveBayes(dir+"/"+datasets[i]);
			exp2.run();

			// kNN
			System.out.println("\tRunning kNN...");
			ExperimentKNN exp3 = new ExperimentKNN(dir+"/"+datasets[i]);
			exp3.run();

			// Rule classifier
			System.out.println("\tRunning Rule-based Classifier...");
			ExperimentRuleClassifier exp4 = new ExperimentRuleClassifier(dir+"/"+datasets[i]);
			exp4.run();
		}
	}
}
